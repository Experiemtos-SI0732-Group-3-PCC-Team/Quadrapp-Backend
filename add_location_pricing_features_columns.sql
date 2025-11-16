-- Script para agregar las columnas de Location, Pricing y Features a la tabla parking_profiles
-- Ejecutar este script en la base de datos antes de reiniciar la aplicación

-- Columnas de LocationData (con prefijo location_)
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS location_address_line VARCHAR(500);
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS location_city VARCHAR(100);
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS location_postal_code VARCHAR(20);
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS location_state VARCHAR(100);
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS location_country VARCHAR(100);
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS location_latitude DOUBLE PRECISION;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS location_longitude DOUBLE PRECISION;

-- Columnas de PricingData (con prefijo pricing_)
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_hourly_rate DOUBLE PRECISION;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_daily_rate DOUBLE PRECISION;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_monthly_rate DOUBLE PRECISION;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_currency VARCHAR(10);
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_minimum_stay VARCHAR(50);
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_open_24h BOOLEAN;

-- Operating Hours (con prefijo pricing_operating_)
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_operating_open_time VARCHAR(10);
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_operating_close_time VARCHAR(10);

-- Operating Days (con prefijo pricing_operating_)
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_operating_monday BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_operating_tuesday BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_operating_wednesday BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_operating_thursday BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_operating_friday BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_operating_saturday BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_operating_sunday BOOLEAN;

-- Promotions (con prefijo pricing_promo_)
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_promo_early_bird BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_promo_weekend BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS pricing_promo_long_stay BOOLEAN;

-- Security Features (con prefijo features_security_)
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_security_24h BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_security_cameras BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_security_lighting BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_security_access_control BOOLEAN;

-- Amenities (con prefijo features_amenity_)
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_amenity_covered BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_amenity_elevator BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_amenity_bathrooms BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_amenity_car_wash BOOLEAN;

-- Services (con prefijo features_service_)
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_service_electric_charging BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_service_free_wifi BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_service_valet BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_service_maintenance BOOLEAN;

-- Payments (con prefijo features_payment_)
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_payment_card BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_payment_mobile BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_payment_monthly_passes BOOLEAN;
ALTER TABLE parking_profiles ADD COLUMN IF NOT EXISTS features_payment_corporate_rates BOOLEAN;

-- Comentario final
COMMENT ON TABLE parking_profiles IS 'Tabla actualizada con campos de Location, Pricing y Features con prefijos para evitar conflictos';
