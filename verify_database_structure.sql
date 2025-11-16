-- Script SQL para verificar la estructura de la base de datos
-- y diagnosticar problemas de persistencia

USE quadrapp;

-- 1. Ver estructura completa de la tabla parking_profiles
DESCRIBE parking_profiles;

-- 2. Ver todas las columnas relacionadas con location
SHOW COLUMNS FROM parking_profiles LIKE '%address%';
SHOW COLUMNS FROM parking_profiles LIKE '%city%';
SHOW COLUMNS FROM parking_profiles LIKE '%latitude%';
SHOW COLUMNS FROM parking_profiles LIKE '%longitude%';

-- 3. Ver todas las columnas relacionadas con pricing
SHOW COLUMNS FROM parking_profiles LIKE '%rate%';
SHOW COLUMNS FROM parking_profiles LIKE '%currency%';
SHOW COLUMNS FROM parking_profiles LIKE '%operating%';
SHOW COLUMNS FROM parking_profiles LIKE '%promo%';

-- 4. Ver todas las columnas relacionadas con features
SHOW COLUMNS FROM parking_profiles LIKE '%security%';
SHOW COLUMNS FROM parking_profiles LIKE '%amenity%';
SHOW COLUMNS FROM parking_profiles LIKE '%service%';
SHOW COLUMNS FROM parking_profiles LIKE '%payment%';

-- 5. Consultar datos existentes
SELECT id, name, owner_id,
       address_line, city, latitude, longitude,
       hourly_rate, daily_rate, monthly_rate, currency,
       security_24h, security_cameras, amenity_covered
FROM parking_profiles;

-- 6. Verificar si los datos se están guardando (ejecutar después de POST/PUT)
SELECT id, name,
       CASE
         WHEN address_line IS NULL THEN 'NULL'
         ELSE 'OK'
       END as location_status,
       CASE
         WHEN hourly_rate IS NULL THEN 'NULL'
         ELSE 'OK'
       END as pricing_status,
       CASE
         WHEN security_24h IS NULL THEN 'NULL'
         ELSE 'OK'
       END as features_status
FROM parking_profiles
ORDER BY id DESC
LIMIT 10;

-- 7. Si las columnas no existen, puedes recrear la tabla (⚠️ PERDERÁS TODOS LOS DATOS)
-- Descomenta las siguientes líneas SOLO si es necesario:

-- DROP TABLE IF EXISTS parking_profiles;
-- Luego reinicia la aplicación con spring.jpa.hibernate.ddl-auto=create

-- 8. Verificar índices
SHOW INDEXES FROM parking_profiles;

-- 9. Ver ejemplo completo de un registro
SELECT * FROM parking_profiles WHERE id = 1\G

