-- Ejecuta este query en MySQL Workbench para ver las columnas actuales
USE quadrapp;

-- Ver TODAS las columnas de parking_profiles
SHOW COLUMNS FROM parking_profiles;

-- Ver solo las columnas de location
SELECT COLUMN_NAME, COLUMN_TYPE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'quadrapp'
AND TABLE_NAME = 'parking_profiles'
AND COLUMN_NAME LIKE 'location_%'
ORDER BY COLUMN_NAME;

-- Ver solo las columnas de pricing
SELECT COLUMN_NAME, COLUMN_TYPE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'quadrapp'
AND TABLE_NAME = 'parking_profiles'
AND COLUMN_NAME LIKE 'pricing_%'
ORDER BY COLUMN_NAME;

-- Ver solo las columnas de features
SELECT COLUMN_NAME, COLUMN_TYPE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'quadrapp'
AND TABLE_NAME = 'parking_profiles'
AND COLUMN_NAME LIKE 'features_%'
ORDER BY COLUMN_NAME;

-- Ver datos del parking 1
SELECT
    id,
    name,
    owner_id,
    location_address_line,
    location_city,
    pricing_hourly_rate,
    pricing_daily_rate,
    features_security_24h,
    features_security_cameras
FROM parking_profiles
WHERE id = 1;

