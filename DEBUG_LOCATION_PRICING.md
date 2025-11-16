# DEBUG: Location y Pricing no se guardan

## Estado Actual
- ✅ **Features**: Se guarda correctamente
- ❌ **Location**: NO se guarda (Map vacío)
- ❌ **Pricing**: NO se guarda (Map vacío)

## Hipótesis

### 1. El frontend NO está enviando los datos de location y pricing
El problema más probable es que el frontend está enviando:
```json
{
  "location": null,  // ❌
  "pricing": null,   // ❌
  "features": {...}  // ✅
}
```

### 2. Los endpoints PUT no se están llamando
El frontend debería llamar a:
- `PUT /api/v1/parkings/{id}/location`
- `PUT /api/v1/parkings/{id}/pricing`
- `PUT /api/v1/parkings/{id}/features`

Pero parece que solo se llama el de features.

## Pasos para Diagnosticar

### 1. Revisar los logs del backend al crear parking

En la consola del Spring Boot, deberías ver:

```
=== RECEIVED DTO FROM FRONTEND ===
DTO Location: null  (o con datos)
DTO Pricing: null   (o con datos)
DTO Features: {...} (con datos)
```

### 2. Revisar el Network tab del navegador

Cuando creas un parking, busca:
- **POST** `/api/v1/parkings` - ¿Qué datos envía en el body?
- **PUT** `/api/v1/parkings/4/location` - ¿Se hace esta llamada?
- **PUT** `/api/v1/parkings/4/pricing` - ¿Se hace esta llamada?

### 3. Revisar la respuesta de GET

Cuando haces **GET** `/api/v1/parkings/4/location`, ¿qué devuelve?
- Si devuelve `{"data": null}` → No se guardó
- Si devuelve `{"data": {...}}` → Sí se guardó pero el frontend no lo lee bien

## Solución Temporal - Endpoint de Prueba

He creado un endpoint POST `/api/v1/parkings/initialize-all` que puedes llamar para inicializar todos los parkings del usuario.

Esto creará objetos vacíos de location/pricing/features si son null, y luego el frontend puede actualizarlos con PUT.

**Llamar desde Postman o curl:**
```bash
POST http://localhost:8080/api/v1/parkings/initialize-all
Headers: Authorization: Bearer {tu_token}
```

## Próximo Paso

Necesito ver:
1. Los logs del backend al crear un parking (consola de Spring Boot)
2. El payload del POST en el Network tab del navegador
3. Confirmar si se hacen las llamadas PUT a `/location` y `/pricing`

