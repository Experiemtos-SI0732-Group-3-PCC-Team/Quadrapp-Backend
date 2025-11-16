# 🔧 Troubleshooting Guide - Parking Management API

## Problema Principal: POST devuelve 201 pero GET no muestra location, pricing o features

### Causa Raíz
El problema más común es que las columnas embebidas (`@Embedded`) no se están persistiendo correctamente en la base de datos debido a:

1. **Columnas no existen en la BD**: Hibernate no creó las columnas automáticamente
2. **Anotaciones JPA faltantes**: Los `@Embedded` anidados necesitan `@AttributeOverrides`
3. **Transacción no se commitea**: Los datos se quedan en memoria y no se persisten

---

## ✅ Soluciones Implementadas

### 1. Anotaciones JPA Mejoradas
Se agregaron `@Column` explícitos y `@AttributeOverrides` en:
- `LocationData.java` - Columnas: address_line, city, postal_code, latitude, longitude
- `PricingData.java` - Columnas: hourly_rate, daily_rate, operating_*, promo_*
- `FeaturesData.java` - Columnas: security_*, amenity_*, service_*, payment_*

### 2. Logs de Depuración
El servicio `ParkingProfileService` ahora incluye logs detallados:
- ANTES de save()
- DESPUÉS de save()
- VERIFICACIÓN desde BD (re-read)
- Incluye `repository.flush()` para forzar persistencia inmediata

### 3. Endpoints Anidados
Se agregaron endpoints RESTful:
```
GET  /api/v1/parkings/{id}/location
POST /api/v1/parkings/{id}/location
PUT  /api/v1/parkings/{id}/location

GET  /api/v1/parkings/{id}/pricing
POST /api/v1/parkings/{id}/pricing
PUT  /api/v1/parkings/{id}/pricing

GET  /api/v1/parkings/{id}/features
POST /api/v1/parkings/{id}/features
PUT  /api/v1/parkings/{id}/features
```

Todos devuelven formato `{ data: {...} }` compatible con el frontend.

---

## 🔍 Pasos de Diagnóstico

### Paso 1: Verificar que el servidor inició correctamente
```bash
# Buscar en los logs:
# "Started QuadrappApplication in X seconds"
# Sin errores de Hibernate/JPA
```

### Paso 2: Verificar estructura de la BD
Ejecuta el script SQL: `verify_database_structure.sql`

```sql
USE quadrapp;
DESCRIBE parking_profiles;
```

**Deberías ver columnas como:**
- `address_line`, `city`, `postal_code`, `state`, `country`, `latitude`, `longitude`
- `hourly_rate`, `daily_rate`, `monthly_rate`, `currency`, `minimum_stay`, `open_24h`
- `operating_open_time`, `operating_close_time`
- `operating_monday`, `operating_tuesday`, etc.
- `promo_early_bird`, `promo_weekend`, `promo_long_stay`
- `security_24h`, `security_cameras`, `security_lighting`, `security_access_control`
- `amenity_covered`, `amenity_elevator`, `amenity_bathrooms`, `amenity_car_wash`
- `service_electric_charging`, `service_free_wifi`, `service_valet`, `service_maintenance`
- `payment_card`, `payment_mobile`, `payment_monthly_passes`, `payment_corporate_rates`

**Si NO ves esas columnas**, sigue al Paso 3.

### Paso 3: Recrear la estructura de BD

⚠️ **ADVERTENCIA: Esto eliminará todos los datos de parking_profiles**

**Opción A: Forzar recreación (desarrollo)**
1. Edita `application.properties`:
```properties
spring.jpa.hibernate.ddl-auto=create
```
2. Reinicia la aplicación
3. Verifica que se crearon las columnas
4. Cambia de vuelta a:
```properties
spring.jpa.hibernate.ddl-auto=update
```

**Opción B: Crear columnas manualmente (producción)**
```sql
-- Ejemplo para location
ALTER TABLE parking_profiles 
  ADD COLUMN address_line VARCHAR(500),
  ADD COLUMN city VARCHAR(100),
  ADD COLUMN postal_code VARCHAR(20),
  ADD COLUMN state VARCHAR(100),
  ADD COLUMN country VARCHAR(100),
  ADD COLUMN latitude DOUBLE,
  ADD COLUMN longitude DOUBLE;

-- Ejemplo para pricing
ALTER TABLE parking_profiles 
  ADD COLUMN hourly_rate DOUBLE,
  ADD COLUMN daily_rate DOUBLE,
  ADD COLUMN monthly_rate DOUBLE,
  ADD COLUMN currency VARCHAR(10);

-- Y así sucesivamente para todas las columnas...
```

### Paso 4: Verificar logs de persistencia

Inicia la aplicación y busca en los logs cuando hagas POST/PUT:

```
[INFO] Updating location for parking: id=1, ownerId=user@example.com, userId=user@example.com
[DEBUG] Location data BEFORE update: null
[DEBUG] New location data to set: LocationData(addressLine=Av. Principal 123, city=Lima, ...)
[INFO] Location updated and flushed for parking ID: 1
[DEBUG] Location data AFTER save and flush: LocationData(addressLine=Av. Principal 123, city=Lima, ...)
[DEBUG] Location data VERIFIED from DB: LocationData(addressLine=Av. Principal 123, city=Lima, ...)
```

**Si ves "VERIFIED from DB: null"**, el problema está en la persistencia de JPA.

### Paso 5: Verificar datos en BD directamente

```sql
SELECT id, name, owner_id,
       address_line, city, latitude, longitude,
       hourly_rate, daily_rate,
       security_24h, amenity_covered
FROM parking_profiles
WHERE id = 1;
```

**Si las columnas tienen valores NULL después de POST/PUT**, hay un problema de transacción o mapeo.

---

## 🧪 Pruebas Paso a Paso

### Test 1: Crear parking básico
```bash
curl -X POST http://localhost:8080/api/v1/parkings \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Parking",
    "type": "PUBLIC",
    "status": "ACTIVE",
    "totalSpaces": 100,
    "accessibleSpaces": 10,
    "phone": "+51999999999",
    "email": "test@example.com"
  }'
```

Anota el `id` del parking creado.

### Test 2: Agregar location
```bash
curl -X POST http://localhost:8080/api/v1/parkings/1/location \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "addressLine": "Av. Test 123",
    "city": "Lima",
    "postalCode": "15001",
    "state": "Lima",
    "country": "Peru",
    "latitude": -12.0464,
    "longitude": -77.0428
  }'
```

### Test 3: Verificar location
```bash
curl -H "Authorization: Bearer YOUR_TOKEN" \
  "http://localhost:8080/api/v1/parkings/1/location"
```

**Respuesta esperada:**
```json
{
  "data": {
    "addressLine": "Av. Test 123",
    "city": "Lima",
    "postalCode": "15001",
    "state": "Lima",
    "country": "Peru",
    "latitude": -12.0464,
    "longitude": -77.0428
  }
}
```

### Test 4: Agregar pricing
```bash
curl -X POST http://localhost:8080/api/v1/parkings/1/pricing \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "hourlyRate": 5.0,
    "dailyRate": 30.0,
    "monthlyRate": 500.0,
    "currency": "PEN"
  }'
```

### Test 5: Verificar pricing
```bash
curl -H "Authorization: Bearer YOUR_TOKEN" \
  "http://localhost:8080/api/v1/parkings/1/pricing"
```

### Test 6: Agregar features
```bash
curl -X POST http://localhost:8080/api/v1/parkings/1/features \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "security": {
      "security24h": true,
      "cameras": true,
      "lighting": true,
      "accessControl": true
    },
    "amenities": {
      "covered": true,
      "elevator": false,
      "bathrooms": true,
      "carWash": false
    },
    "services": {
      "electricCharging": true,
      "freeWifi": true,
      "valetService": false,
      "maintenance": false
    },
    "payments": {
      "cardPayment": true,
      "mobilePayment": true,
      "monthlyPasses": true,
      "corporateRates": false
    }
  }'
```

### Test 7: Verificar features
```bash
curl -H "Authorization: Bearer YOUR_TOKEN" \
  "http://localhost:8080/api/v1/parkings/1/features"
```

### Test 8: Verificar parking completo
```bash
curl -H "Authorization: Bearer YOUR_TOKEN" \
  "http://localhost:8080/api/v1/parkings/1"
```

El objeto `ParkingProfileDto` debe incluir `location`, `pricing` y `features` completos.

---

## 🐛 Problemas Comunes y Soluciones

### Problema: "Cannot resolve column 'xxx'" en el IDE
**Solución**: Son warnings del IDE. Las columnas se crearán automáticamente cuando inicies la app. Puedes ignorarlos.

### Problema: 401 Unauthorized
**Solución**: 
- Verifica que el token JWT sea válido
- Verifica que el header sea: `Authorization: Bearer <token>`
- Verifica que `JWT_SECRET` en `application.properties` coincida con el usado al generar el token

### Problema: 403 Forbidden
**Solución**:
- Solo puedes acceder a parkings donde `ownerId` coincide con tu `userId` del JWT
- O necesitas tener rol `ROLE_ADMIN`

### Problema: Location/Pricing/Features vienen como `null` en GET
**Posibles causas:**

1. **Columnas no existen en BD**: Ver Paso 2-3 arriba
2. **No se envió en POST**: Verifica el body del POST
3. **Problema de Jackson deserialización**: Verifica los logs de errores

**Solución:**
```bash
# 1. Verifica logs cuando haces POST
# 2. Verifica BD directamente
# 3. Asegúrate de enviar objetos completos, no null
```

### Problema: Frontend no muestra precio ni location
**Causa**: El frontend espera formato específico o nombres de campos diferentes.

**Solución**: Verifica que el frontend use:
```typescript
// Correcto
location: LocationData = response.location;
pricing: PricingData = response.pricing;

// Para endpoints anidados
location: LocationData = response.data;
```

---

## 📊 Checklist Final

- [ ] Compilación exitosa (`mvnw clean compile`)
- [ ] Base de datos tiene todas las columnas embebidas
- [ ] Logs muestran "VERIFIED from DB" con datos correctos
- [ ] POST a `/api/v1/parkings/{id}/location` devuelve 200 con profile actualizado
- [ ] GET a `/api/v1/parkings/{id}/location` devuelve `{ data: {...} }` con datos
- [ ] POST a `/api/v1/parkings/{id}/pricing` devuelve 200 con profile actualizado
- [ ] GET a `/api/v1/parkings/{id}/pricing` devuelve `{ data: {...} }` con datos
- [ ] POST a `/api/v1/parkings/{id}/features` devuelve 200 con profile actualizado
- [ ] GET a `/api/v1/parkings/{id}/features` devuelve `{ data: {...} }` con datos
- [ ] Frontend puede consumir los endpoints correctamente

---

## 📞 Soporte Adicional

Si después de seguir estos pasos el problema persiste:

1. Exporta los logs completos del servidor
2. Ejecuta el script `verify_database_structure.sql` y guarda el resultado
3. Muestra un ejemplo de curl con el token redactado
4. Comparte la respuesta completa (headers + body)

---

## 🚀 Siguientes Pasos

Una vez que todo funcione:

1. Escribir tests unitarios e integración
2. Agregar validaciones de negocio adicionales
3. Implementar caché para mejorar performance
4. Agregar índices geoespaciales para búsqueda por ubicación
5. Documentar en Swagger/OpenAPI

