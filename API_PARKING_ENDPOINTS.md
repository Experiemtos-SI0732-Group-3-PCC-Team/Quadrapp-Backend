# Parking Management API - Endpoints Documentation

## Autenticación
Todos los endpoints requieren un token JWT en el header:
```
Authorization: Bearer <tu-token-jwt>
```

## Endpoints Principales

### 1. Gestión de Parkings (Parking Profiles)

#### Listar parkings del usuario autenticado
```bash
curl -H "Authorization: Bearer <token>" http://localhost:8080/api/v1/parkings
```

#### Obtener parking por ID
```bash
curl -H "Authorization: Bearer <token>" http://localhost:8080/api/v1/parkings/1
```

#### Crear parking
```bash
curl -X POST http://localhost:8080/api/v1/parkings \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Parking Central",
    "type": "PUBLIC",
    "status": "ACTIVE",
    "description": "Estacionamiento en el centro",
    "totalSpaces": 100,
    "accessibleSpaces": 10,
    "phone": "+51999999999",
    "email": "parking@example.com"
  }'
```

#### Actualizar parking completo
```bash
curl -X PUT http://localhost:8080/api/v1/parkings/1 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Parking Central Actualizado",
    "type": "PUBLIC",
    "status": "ACTIVE",
    "description": "Nueva descripción",
    "totalSpaces": 120,
    "accessibleSpaces": 15,
    "phone": "+51999999999",
    "email": "parking@example.com"
  }'
```

---

## 2. Location Endpoints (Ubicación)

### Endpoints Anidados (Recomendados)

#### GET - Obtener ubicación de un parking
```bash
curl -H "Authorization: Bearer <token>" \
  "http://localhost:8080/api/v1/parkings/1/location"
```

**Respuesta:**
```json
{
  "data": {
    "addressLine": "Av. Principal 123",
    "city": "Lima",
    "postalCode": "15001",
    "state": "Lima",
    "country": "Peru",
    "latitude": -12.0464,
    "longitude": -77.0428
  }
}
```

#### POST - Crear/actualizar ubicación
```bash
curl -X POST http://localhost:8080/api/v1/parkings/1/location \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "addressLine": "Av. Principal 123",
    "city": "Lima",
    "postalCode": "15001",
    "state": "Lima",
    "country": "Peru",
    "latitude": -12.0464,
    "longitude": -77.0428
  }'
```

#### PUT - Actualizar ubicación
```bash
curl -X PUT http://localhost:8080/api/v1/parkings/1/location \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "addressLine": "Av. Nueva Dirección 456",
    "city": "Lima",
    "postalCode": "15002",
    "state": "Lima",
    "country": "Peru",
    "latitude": -12.0500,
    "longitude": -77.0500
  }'
```

### Endpoints Alternativos

#### GET por query parameter
```bash
curl -H "Authorization: Bearer <token>" \
  "http://localhost:8080/api/v1/locations?profileId=1"
```

#### GET por ID directo
```bash
curl -H "Authorization: Bearer <token>" \
  "http://localhost:8080/api/v1/locations/1"
```

---

## 3. Pricing Endpoints (Precios)

### Endpoints Anidados (Recomendados)

#### GET - Obtener precios de un parking
```bash
curl -H "Authorization: Bearer <token>" \
  "http://localhost:8080/api/v1/parkings/1/pricing"
```

**Respuesta:**
```json
{
  "data": {
    "hourlyRate": 5.0,
    "dailyRate": 30.0,
    "monthlyRate": 500.0,
    "currency": "PEN",
    "minimumStay": "PT30M",
    "open24h": true,
    "operatingHours": {
      "openTime": "08:00",
      "closeTime": "22:00"
    },
    "operatingDays": {
      "monday": true,
      "tuesday": true,
      "wednesday": true,
      "thursday": true,
      "friday": true,
      "saturday": true,
      "sunday": false
    },
    "promotions": {
      "earlyBird": true,
      "weekend": false,
      "longStay": true
    }
  }
}
```

#### POST - Crear/actualizar precios
```bash
curl -X POST http://localhost:8080/api/v1/parkings/1/pricing \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "hourlyRate": 5.0,
    "dailyRate": 30.0,
    "monthlyRate": 500.0,
    "currency": "PEN",
    "minimumStay": "PT30M",
    "open24h": false,
    "operatingHours": {
      "openTime": "08:00",
      "closeTime": "22:00"
    },
    "operatingDays": {
      "monday": true,
      "tuesday": true,
      "wednesday": true,
      "thursday": true,
      "friday": true,
      "saturday": true,
      "sunday": false
    },
    "promotions": {
      "earlyBird": true,
      "weekend": false,
      "longStay": true
    }
  }'
```

#### PUT - Actualizar precios
```bash
curl -X PUT http://localhost:8080/api/v1/parkings/1/pricing \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "hourlyRate": 6.0,
    "dailyRate": 35.0,
    "monthlyRate": 550.0,
    "currency": "PEN"
  }'
```

### Endpoints Alternativos

```bash
# GET por query parameter
curl -H "Authorization: Bearer <token>" \
  "http://localhost:8080/api/v1/pricing?profileId=1"

# GET por ID directo
curl -H "Authorization: Bearer <token>" \
  "http://localhost:8080/api/v1/pricing/1"
```

---

## 4. Features Endpoints (Características)

### Endpoints Anidados (Recomendados)

#### GET - Obtener características de un parking
```bash
curl -H "Authorization: Bearer <token>" \
  "http://localhost:8080/api/v1/parkings/1/features"
```

**Respuesta:**
```json
{
  "data": {
    "security": {
      "security24h": true,
      "cameras": true,
      "lighting": true,
      "accessControl": true
    },
    "amenities": {
      "covered": true,
      "elevator": true,
      "bathrooms": true,
      "carWash": false
    },
    "services": {
      "electricCharging": true,
      "freeWifi": true,
      "valetService": false,
      "maintenance": true
    },
    "payments": {
      "cardPayment": true,
      "mobilePayment": true,
      "monthlyPasses": true,
      "corporateRates": false
    }
  }
}
```

#### POST - Crear/actualizar características
```bash
curl -X POST http://localhost:8080/api/v1/parkings/1/features \
  -H "Authorization: Bearer <token>" \
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
      "elevator": true,
      "bathrooms": true,
      "carWash": false
    },
    "services": {
      "electricCharging": true,
      "freeWifi": true,
      "valetService": false,
      "maintenance": true
    },
    "payments": {
      "cardPayment": true,
      "mobilePayment": true,
      "monthlyPasses": true,
      "corporateRates": false
    }
  }'
```

#### PUT - Actualizar características
```bash
curl -X PUT http://localhost:8080/api/v1/parkings/1/features \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "security": {
      "security24h": true,
      "cameras": true,
      "lighting": true,
      "accessControl": false
    }
  }'
```

### Endpoints Alternativos

```bash
# GET por query parameter
curl -H "Authorization: Bearer <token>" \
  "http://localhost:8080/api/v1/features?profileId=1"

# GET por ID directo
curl -H "Authorization: Bearer <token>" \
  "http://localhost:8080/api/v1/features/1"
```

---

## 5. Analytics Endpoint

```bash
curl -H "Authorization: Bearer <token>" \
  "http://localhost:8080/api/v1/parkings/1/analytics"
```

**Respuesta:**
```json
{
  "parkingId": 1,
  "totalSpaces": 100,
  "occupiedSpaces": 45,
  "availableSpaces": 55,
  "occupancyRate": 45.0,
  "accessibleSpaces": 10,
  "rating": 4.5,
  "reviewCount": 23,
  "status": "ACTIVE"
}
```

---

## Códigos de Respuesta HTTP

- **200 OK**: Operación exitosa (GET, PUT)
- **201 Created**: Recurso creado exitosamente (POST)
- **204 No Content**: Recurso eliminado exitosamente (DELETE)
- **400 Bad Request**: Datos de entrada inválidos
- **401 Unauthorized**: Token JWT inválido o ausente
- **403 Forbidden**: Sin permisos para acceder al recurso
- **404 Not Found**: Recurso no encontrado
- **500 Internal Server Error**: Error del servidor

---

## Permisos y Seguridad

### Usuarios normales (ROLE_USER)
- Solo pueden ver/editar sus propios parkings
- `ownerId` se asigna automáticamente desde el JWT

### Administradores (ROLE_ADMIN)
- Pueden ver/editar todos los parkings
- Pueden usar el parámetro `?ownerId=xxx` para filtrar

---

## Validaciones

### LocationData
- `latitude`: entre -90 y 90
- `longitude`: entre -180 y 180

### PricingData
- `hourlyRate`, `dailyRate`, `monthlyRate`: >= 0

### ParkingProfile
- `name`: requerido
- `type`: enum (PUBLIC, PRIVATE, RESIDENTIAL, COMMERCIAL)
- `status`: enum (ACTIVE, INACTIVE, MAINTENANCE, FULL)
- `totalSpaces`: requerido, > 0
- `accessibleSpaces`: requerido, >= 0
- `phone`: requerido
- `email`: requerido, formato válido

---

## Troubleshooting

### Problema: POST devuelve 201 pero GET no muestra datos

**Solución:** 
1. Verificar logs del servidor para confirmar que los datos se guardan
2. Ejecutar el servidor con `--debug` para ver SQL generado
3. Verificar la base de datos directamente:

```sql
SELECT * FROM parking_profiles WHERE id = 1;
SHOW COLUMNS FROM parking_profiles LIKE '%location%';
SHOW COLUMNS FROM parking_profiles LIKE '%pricing%';
SHOW COLUMNS FROM parking_profiles LIKE '%security%';
```

### Problema: Columnas embebidas no se persisten

Las columnas con `@Embedded` y `@AttributeOverrides` se crearán automáticamente con `hibernate.ddl-auto=update`. 

Para forzar recreación (⚠️ **PERDERÁS DATOS**):
```properties
spring.jpa.hibernate.ddl-auto=create
```

Luego cambiar de vuelta a:
```properties
spring.jpa.hibernate.ddl-auto=update
```

### Verificar que las columnas existen en la BD

```sql
USE quadrapp;
DESCRIBE parking_profiles;
```

Deberías ver columnas como:
- `address_line`, `city`, `postal_code`, `latitude`, `longitude`
- `hourly_rate`, `daily_rate`, `monthly_rate`, `currency`
- `security_24h`, `security_cameras`, `amenity_covered`, etc.

---

## Integración con Frontend (Angular)

El frontend debe enviar las peticiones en el formato correcto:

```typescript
// Ejemplo de servicio Angular
updateLocation(parkingId: number, location: LocationData): Observable<any> {
  return this.http.post(`${this.baseUrl}/parkings/${parkingId}/location`, location);
}

getLocation(parkingId: number): Observable<LocationData> {
  return this.http.get<{data: LocationData}>(`${this.baseUrl}/parkings/${parkingId}/location`)
    .pipe(map(response => response.data));
}
```

Los endpoints devuelven formato `{ data: ... }` para compatibilidad con el frontend.

