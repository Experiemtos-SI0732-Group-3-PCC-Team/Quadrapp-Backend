@echo off
REM Script de prueba para endpoints de Parking Management API
REM Reemplaza YOUR_TOKEN con tu token JWT válido

SET BASE_URL=http://localhost:8080
SET TOKEN=YOUR_TOKEN_HERE

echo ========================================
echo PARKING MANAGEMENT API - TEST SCRIPT
echo ========================================
echo.

REM Test 1: Crear parking
echo [Test 1] Creando parking básico...
curl -X POST %BASE_URL%/api/v1/parkings ^
  -H "Authorization: Bearer %TOKEN%" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Test Parking\",\"type\":\"PUBLIC\",\"status\":\"ACTIVE\",\"totalSpaces\":100,\"accessibleSpaces\":10,\"phone\":\"+51999999999\",\"email\":\"test@example.com\"}"
echo.
echo.

REM NOTA: Reemplaza {id} con el ID del parking creado en el paso anterior
SET PARKING_ID=1

echo [Test 2] Agregando location al parking %PARKING_ID%...
curl -X POST %BASE_URL%/api/v1/parkings/%PARKING_ID%/location ^
  -H "Authorization: Bearer %TOKEN%" ^
  -H "Content-Type: application/json" ^
  -d "{\"addressLine\":\"Av. Test 123\",\"city\":\"Lima\",\"postalCode\":\"15001\",\"state\":\"Lima\",\"country\":\"Peru\",\"latitude\":-12.0464,\"longitude\":-77.0428}"
echo.
echo.

echo [Test 3] Verificando location...
curl -H "Authorization: Bearer %TOKEN%" ^
  "%BASE_URL%/api/v1/parkings/%PARKING_ID%/location"
echo.
echo.

echo [Test 4] Agregando pricing al parking %PARKING_ID%...
curl -X POST %BASE_URL%/api/v1/parkings/%PARKING_ID%/pricing ^
  -H "Authorization: Bearer %TOKEN%" ^
  -H "Content-Type: application/json" ^
  -d "{\"hourlyRate\":5.0,\"dailyRate\":30.0,\"monthlyRate\":500.0,\"currency\":\"PEN\"}"
echo.
echo.

echo [Test 5] Verificando pricing...
curl -H "Authorization: Bearer %TOKEN%" ^
  "%BASE_URL%/api/v1/parkings/%PARKING_ID%/pricing"
echo.
echo.

echo [Test 6] Agregando features al parking %PARKING_ID%...
curl -X POST %BASE_URL%/api/v1/parkings/%PARKING_ID%/features ^
  -H "Authorization: Bearer %TOKEN%" ^
  -H "Content-Type: application/json" ^
  -d "{\"security\":{\"security24h\":true,\"cameras\":true,\"lighting\":true,\"accessControl\":true},\"amenities\":{\"covered\":true,\"elevator\":false,\"bathrooms\":true,\"carWash\":false},\"services\":{\"electricCharging\":true,\"freeWifi\":true,\"valetService\":false,\"maintenance\":false},\"payments\":{\"cardPayment\":true,\"mobilePayment\":true,\"monthlyPasses\":true,\"corporateRates\":false}}"
echo.
echo.

echo [Test 7] Verificando features...
curl -H "Authorization: Bearer %TOKEN%" ^
  "%BASE_URL%/api/v1/parkings/%PARKING_ID%/features"
echo.
echo.

echo [Test 8] Obteniendo parking completo...
curl -H "Authorization: Bearer %TOKEN%" ^
  "%BASE_URL%/api/v1/parkings/%PARKING_ID%"
echo.
echo.

echo [Test 9] Listando todos los parkings...
curl -H "Authorization: Bearer %TOKEN%" ^
  "%BASE_URL%/api/v1/parkings"
echo.
echo.

echo ========================================
echo TESTS COMPLETADOS
echo ========================================
pause

