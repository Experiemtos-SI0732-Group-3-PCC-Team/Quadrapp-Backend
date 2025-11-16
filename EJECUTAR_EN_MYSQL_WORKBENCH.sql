-- ========================================
-- SCRIPT DE MIGRACIÓN PARA MYSQL
-- Agregar columnas de Location, Pricing y Features
-- ========================================

-- IMPORTANTE: Ejecuta este script en MySQL Workbench
-- Base de datos: quadrapp
-- Tabla: parking_profiles

USE quadrapp;

-- Verificar si la tabla existe
SELECT COUNT(*) as tabla_existe
FROM information_schema.tables
WHERE table_schema = 'quadrapp'
AND table_name = 'parking_profiles';

-- ========================================
-- COLUMNAS DE LOCATION (7 columnas)
-- ========================================

-- Verificar y agregar location_address_line
SELECT IF(
    EXISTS(
        SELECT * FROM information_schema.columns
        WHERE table_schema='quadrapp'
        AND table_name='parking_profiles'
        AND column_name='location_address_line'
    ),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN location_address_line VARCHAR(500)'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Agregar location_city
SELECT IF(
    EXISTS(
        SELECT * FROM information_schema.columns
        WHERE table_schema='quadrapp'
        AND table_name='parking_profiles'
        AND column_name='location_city'
    ),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN location_city VARCHAR(100)'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Agregar location_postal_code
SELECT IF(
    EXISTS(
        SELECT * FROM information_schema.columns
        WHERE table_schema='quadrapp'
        AND table_name='parking_profiles'
        AND column_name='location_postal_code'
    ),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN location_postal_code VARCHAR(20)'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Agregar location_state
SELECT IF(
    EXISTS(
        SELECT * FROM information_schema.columns
        WHERE table_schema='quadrapp'
        AND table_name='parking_profiles'
        AND column_name='location_state'
    ),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN location_state VARCHAR(100)'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Agregar location_country
SELECT IF(
    EXISTS(
        SELECT * FROM information_schema.columns
        WHERE table_schema='quadrapp'
        AND table_name='parking_profiles'
        AND column_name='location_country'
    ),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN location_country VARCHAR(100)'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Agregar location_latitude
SELECT IF(
    EXISTS(
        SELECT * FROM information_schema.columns
        WHERE table_schema='quadrapp'
        AND table_name='parking_profiles'
        AND column_name='location_latitude'
    ),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN location_latitude DOUBLE'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Agregar location_longitude
SELECT IF(
    EXISTS(
        SELECT * FROM information_schema.columns
        WHERE table_schema='quadrapp'
        AND table_name='parking_profiles'
        AND column_name='location_longitude'
    ),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN location_longitude DOUBLE'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- ========================================
-- COLUMNAS DE PRICING (17 columnas)
-- ========================================

-- pricing_hourly_rate
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_hourly_rate'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_hourly_rate DOUBLE'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_daily_rate
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_daily_rate'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_daily_rate DOUBLE'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_monthly_rate
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_monthly_rate'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_monthly_rate DOUBLE'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_currency
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_currency'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_currency VARCHAR(10)'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_minimum_stay
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_minimum_stay'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_minimum_stay VARCHAR(50)'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_open_24h
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_open_24h'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_open_24h BOOLEAN'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_operating_open_time
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_operating_open_time'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_operating_open_time VARCHAR(10)'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_operating_close_time
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_operating_close_time'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_operating_close_time VARCHAR(10)'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_operating_monday
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_operating_monday'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_operating_monday BOOLEAN'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_operating_tuesday
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_operating_tuesday'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_operating_tuesday BOOLEAN'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_operating_wednesday
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_operating_wednesday'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_operating_wednesday BOOLEAN'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_operating_thursday
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_operating_thursday'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_operating_thursday BOOLEAN'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_operating_friday
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_operating_friday'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_operating_friday BOOLEAN'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_operating_saturday
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_operating_saturday'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_operating_saturday BOOLEAN'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_operating_sunday
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_operating_sunday'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_operating_sunday BOOLEAN'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_promo_early_bird
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_promo_early_bird'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_promo_early_bird BOOLEAN'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_promo_weekend
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_promo_weekend'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_promo_weekend BOOLEAN'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- pricing_promo_long_stay
SELECT IF(
    EXISTS(SELECT * FROM information_schema.columns WHERE table_schema='quadrapp' AND table_name='parking_profiles' AND column_name='pricing_promo_long_stay'),
    'Column exists',
    'ALTER TABLE parking_profiles ADD COLUMN pricing_promo_long_stay BOOLEAN'
) INTO @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- ========================================
-- VERIFICAR RESULTADOS
-- ========================================

SELECT
    'Location columns' as category,
    COUNT(*) as total_columns
FROM information_schema.columns
WHERE table_schema = 'quadrapp'
AND table_name = 'parking_profiles'
AND column_name LIKE 'location_%'

UNION ALL

SELECT
    'Pricing columns' as category,
    COUNT(*) as total_columns
FROM information_schema.columns
WHERE table_schema = 'quadrapp'
AND table_name = 'parking_profiles'
AND column_name LIKE 'pricing_%'

UNION ALL

SELECT
    'Features columns' as category,
    COUNT(*) as total_columns
FROM information_schema.columns
WHERE table_schema = 'quadrapp'
AND table_name = 'parking_profiles'
AND column_name LIKE 'features_%';

-- Mostrar todas las columnas de la tabla
DESCRIBE parking_profiles;

SELECT '✅ SCRIPT EJECUTADO CORRECTAMENTE' as status;

