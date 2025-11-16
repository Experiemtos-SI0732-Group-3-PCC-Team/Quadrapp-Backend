-- Script para diagnosticar el problema de parkings vacíos
-- Ejecutar en MySQL Workbench o consola MySQL

USE quadrapp;

-- 1. Ver todos los parkings y sus datos embebidos
SELECT
    id,
    name,
    owner_id,
    total_spaces,
    accessible_spaces,
    -- Location fields
    address_line,
    city,
    postal_code,
    latitude,
    longitude,
    -- Pricing fields
    hourly_rate,
    daily_rate,
    monthly_rate,
    currency,
    -- Features fields
    security_24h,
    amenity_covered,
    service_electric_charging,
    payment_card
FROM parking_profiles
ORDER BY id DESC;

-- 2. Ver si las COLUMNAS existen
SHOW COLUMNS FROM parking_profiles LIKE '%address%';
SHOW COLUMNS FROM parking_profiles LIKE '%hourly%';
SHOW COLUMNS FROM parking_profiles LIKE '%security%';

-- 3. Contar parkings con datos vacíos
SELECT
    COUNT(*) as total_parkings,
    SUM(CASE WHEN address_line IS NULL THEN 1 ELSE 0 END) as sin_location,
    SUM(CASE WHEN hourly_rate IS NULL THEN 1 ELSE 0 END) as sin_pricing,
    SUM(CASE WHEN security_24h IS NULL THEN 1 ELSE 0 END) as sin_features
FROM parking_profiles;

-- 4. Ver específicamente el parking que se muestra en la imagen (parece ser "Patata")
SELECT * FROM parking_profiles WHERE name LIKE '%Patata%' OR name LIKE '%patata%';

