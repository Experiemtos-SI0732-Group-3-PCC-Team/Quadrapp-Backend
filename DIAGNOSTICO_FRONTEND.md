# 🔍 Diagnóstico: Datos no aparecen en el Frontend

## Problema Actual
El frontend hace las peticiones correctamente:
- ✅ GET /api/v1/parkings → Devuelve lista de parkings
- ✅ GET /api/v1/locations?profileId=2
- ✅ GET /api/v1/locations?profileId=3
- ✅ GET /api/v1/pricing?profileId=2
- ✅ GET /api/v1/pricing?profileId=3
- ✅ GET /api/v1/features?profileId=2
- ✅ GET /api/v1/features?profileId=3

**Pero:** Los datos no aparecen en la interfaz.

---

## 🔎 Posibles Causas

### 1. **Parkings existen pero location/pricing/features son NULL**
Los parkings con ID 2 y 3 existen en la base de datos, pero sus campos embebidos están vacíos.

**Solución:** Agregar datos usando POST

### 2. **Frontend no está procesando la respuesta correctamente**
El backend devuelve `{ data: {...} }` pero el frontend espera otro formato.

### 3. **Los parkings fueron creados ANTES de la actualización**
Si los parkings ID 2 y 3 se crearon antes de implementar la inicialización automática, no tienen los objetos LocationData/PricingData/FeaturesData inicializados.

---

## ✅ PASOS DE VERIFICACIÓN

### Paso 1: Verificar qué devuelve el servidor

Abre la consola de red del navegador (F12 → Network) y revisa las respuestas:

**GET /api/v1/locations?profileId=2**
```json
// Esperado:
{
  "data": {
    "addressLine": null,
    "city": null,
    ...
  }
}

// O puede estar vacío:
{
  "data": null
}
```

### Paso 2: Verificar en la base de datos

```sql
USE quadrapp;

-- Ver parkings existentes
SELECT id, name, owner_id, 
       address_line, city, latitude,
       hourly_rate, daily_rate,
       security_24h, amenity_covered
FROM parking_profiles
WHERE id IN (2, 3);
```

**Si address_line, hourly_rate, security_24h son NULL:**
→ Los datos NO se han guardado todavía.

### Paso 3: Verificar logs del servidor

Cuando se hace GET, el servidor debe mostrar:
```
[DEBUG] Location data VERIFIED from DB: LocationData(addressLine=null, ...)
```

Si no aparece nada, significa que la consulta no está llegando al servidor.

---

## 🔧 SOLUCIONES RÁPIDAS

### Solución 1: Reinicializar parkings existentes

Si los parkings 2 y 3 fueron creados ANTES de la actualización, necesitas inicializarlos manualmente:

**Opción A: Desde SQL (Rápido)**
```sql
USE quadrapp;

-- Inicializar location vacía para parkings existentes
UPDATE parking_profiles 
SET address_line = '',
    city = '',
    postal_code = '',
    state = '',
    country = '',
    latitude = NULL,
    longitude = NULL
WHERE id IN (2, 3) AND address_line IS NULL;

-- Inicializar pricing vacío
UPDATE parking_profiles 
SET hourly_rate = NULL,
    daily_rate = NULL,
    monthly_rate = NULL,
    currency = 'PEN',
    open_24h = false
WHERE id IN (2, 3) AND hourly_rate IS NULL;

-- Inicializar features vacías
UPDATE parking_profiles 
SET security_24h = false,
    security_cameras = false,
    security_lighting = false,
    security_access_control = false,
    amenity_covered = false,
    amenity_elevator = false,
    amenity_bathrooms = false,
    amenity_car_wash = false
WHERE id IN (2, 3) AND security_24h IS NULL;
```

**Opción B: Desde el Frontend (Recomendado)**
```typescript
// Para cada parking, hacer POST para inicializar
const parkingIds = [2, 3];

parkingIds.forEach(id => {
  // Inicializar location
  this.http.post(`/api/v1/parkings/${id}/location`, {
    addressLine: '',
    city: '',
    postalCode: '',
    state: '',
    country: '',
    latitude: null,
    longitude: null
  }).subscribe();

  // Inicializar pricing
  this.http.post(`/api/v1/parkings/${id}/pricing`, {
    hourlyRate: 0,
    dailyRate: 0,
    monthlyRate: 0,
    currency: 'PEN'
  }).subscribe();

  // Inicializar features
  this.http.post(`/api/v1/parkings/${id}/features`, {
    security: { security24h: false, cameras: false, lighting: false, accessControl: false },
    amenities: { covered: false, elevator: false, bathrooms: false, carWash: false },
    services: { electricCharging: false, freeWifi: false, valetService: false, maintenance: false },
    payments: { cardPayment: false, mobilePayment: false, monthlyPasses: false, corporateRates: false }
  }).subscribe();
});
```

### Solución 2: Eliminar y recrear parkings

Si los parkings 2 y 3 son de prueba, elimínalos y créalos de nuevo:

```sql
DELETE FROM parking_profiles WHERE id IN (2, 3);
```

Luego crea nuevos parkings desde el frontend. Ahora SÍ se inicializarán automáticamente con los objetos vacíos.

---

## 🧪 SCRIPT DE PRUEBA COMPLETO

Crea este archivo TypeScript en tu frontend para probar:

```typescript
// test-parking-data.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class ParkingDataTester {
  constructor(private http: HttpClient) {}

  async testParkingData(parkingId: number) {
    console.log(`\n========== TESTING PARKING ${parkingId} ==========`);

    // 1. Get parking completo
    const parking = await this.http.get(`/api/v1/parkings/${parkingId}`).toPromise();
    console.log('Parking completo:', parking);

    // 2. Get location
    const location = await this.http.get(`/api/v1/parkings/${parkingId}/location`).toPromise();
    console.log('Location:', location);

    // 3. Get pricing
    const pricing = await this.http.get(`/api/v1/parkings/${parkingId}/pricing`).toPromise();
    console.log('Pricing:', pricing);

    // 4. Get features
    const features = await this.http.get(`/api/v1/parkings/${parkingId}/features`).toPromise();
    console.log('Features:', features);

    // 5. Inicializar si están vacíos
    if (!location.data || !location.data.city) {
      console.log('⚠️ Location vacía, inicializando...');
      await this.http.post(`/api/v1/parkings/${parkingId}/location`, {
        addressLine: 'Av. Test',
        city: 'Lima',
        postalCode: '15001',
        state: 'Lima',
        country: 'Peru',
        latitude: -12.0464,
        longitude: -77.0428
      }).toPromise();
      console.log('✅ Location inicializada');
    }

    if (!pricing.data || !pricing.data.hourlyRate) {
      console.log('⚠️ Pricing vacío, inicializando...');
      await this.http.post(`/api/v1/parkings/${parkingId}/pricing`, {
        hourlyRate: 5.0,
        dailyRate: 30.0,
        monthlyRate: 500.0,
        currency: 'PEN'
      }).toPromise();
      console.log('✅ Pricing inicializado');
    }

    if (!features.data || !features.data.security) {
      console.log('⚠️ Features vacías, inicializando...');
      await this.http.post(`/api/v1/parkings/${parkingId}/features`, {
        security: { security24h: true, cameras: true, lighting: true, accessControl: true },
        amenities: { covered: true, elevator: false, bathrooms: true, carWash: false },
        services: { electricCharging: true, freeWifi: true, valetService: false, maintenance: false },
        payments: { cardPayment: true, mobilePayment: true, monthlyPasses: true, corporateRates: false }
      }).toPromise();
      console.log('✅ Features inicializadas');
    }

    console.log('========== TEST COMPLETADO ==========\n');
  }

  async testAllParkings() {
    // Probar parkings 2 y 3
    await this.testParkingData(2);
    await this.testParkingData(3);
  }
}
```

**Úsalo en tu componente:**
```typescript
import { ParkingDataTester } from './test-parking-data';

// En ngOnInit o en un botón
constructor(private tester: ParkingDataTester) {}

async ngOnInit() {
  await this.tester.testAllParkings();
}
```

---

## 📊 VERIFICACIÓN FINAL

Después de ejecutar las soluciones, verifica:

### 1. En la consola del navegador
```
Parking completo: { id: 2, name: "...", location: {...}, pricing: {...}, features: {...} }
Location: { data: { addressLine: "Av. Test", city: "Lima", ... } }
Pricing: { data: { hourlyRate: 5.0, dailyRate: 30.0, ... } }
Features: { data: { security: {...}, amenities: {...}, ... } }
```

### 2. En la base de datos
```sql
SELECT id, name, address_line, city, hourly_rate, security_24h
FROM parking_profiles
WHERE id IN (2, 3);
```

**Debe mostrar:**
```
id | name | address_line | city | hourly_rate | security_24h
2  | ...  | Av. Test     | Lima | 5.0         | 1
3  | ...  | Av. Test     | Lima | 5.0         | 1
```

### 3. En la interfaz del frontend
Los cards de parking ahora deben mostrar:
- 📍 Ubicación: Lima
- 💰 Precio: €5.0/mes
- ⭐ Features activos

---

## 🎯 RESUMEN

**Causa principal:** Los parkings ID 2 y 3 fueron creados ANTES de implementar la inicialización automática de location/pricing/features.

**Solución más rápida:**
1. Ejecuta el script SQL de inicialización (Solución 1 - Opción A)
2. Recarga el frontend
3. Los datos deberían aparecer ahora

**Solución recomendada:**
1. Usa el script TypeScript (Solución 1 - Opción B)
2. Ejecuta `testAllParkings()`
3. Verifica que los datos se guardaron
4. Recarga la página

---

## 🐛 Si AÚN no aparece

Comparte:
1. **Respuesta del servidor** en Network tab para GET /api/v1/parkings/2
2. **Respuesta** para GET /api/v1/locations?profileId=2
3. **Logs del servidor** (consola de Spring Boot)
4. **Query SQL:** `SELECT * FROM parking_profiles WHERE id = 2`

Con esa información puedo identificar el problema exacto.

