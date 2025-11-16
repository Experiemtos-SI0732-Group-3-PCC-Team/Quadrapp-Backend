@echo off
echo ========================================
echo SOLUCION: Location, Pricing y Features
echo ========================================
echo.

echo [1/3] Verificando conexion a base de datos...
echo.
echo IMPORTANTE: Este script asume que tienes PostgreSQL configurado.
echo Si usas otra base de datos, ajusta el comando.
echo.

REM Ajusta estas variables segun tu configuracion
set DB_HOST=localhost
set DB_PORT=5432
set DB_NAME=quadrapp
set DB_USER=postgres

echo Conectando a: %DB_HOST%:%DB_PORT%/%DB_NAME%
echo.

REM Ejecutar el script SQL
echo [2/3] Ejecutando script SQL para agregar columnas...
psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f add_location_pricing_features_columns.sql

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: No se pudo ejecutar el script SQL.
    echo.
    echo Alternativa: Ejecuta manualmente:
    echo   psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f add_location_pricing_features_columns.sql
    echo.
    echo O abre el archivo add_location_pricing_features_columns.sql en tu cliente SQL favorito.
    pause
    exit /b 1
)

echo.
echo [3/3] Compilando el proyecto...
call mvnw.cmd clean compile -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: La compilacion fallo. Revisa los errores arriba.
    pause
    exit /b 1
)

echo.
echo ========================================
echo COMPLETADO EXITOSAMENTE
echo ========================================
echo.
echo Ahora puedes iniciar el backend con:
echo   mvnw.cmd spring-boot:run
echo.
echo Y probar el endpoint de debug:
echo   GET http://localhost:8080/api/v1/parkings/4/debug
echo.
echo Este endpoint te mostrara exactamente que datos tiene el parking.
echo.
pause

