-- ============================================
-- Script de Verificación PostgreSQL
-- Orchestrator Bounded Context
-- ============================================

-- 1. Verificar que las tablas existen
SELECT 
    table_name,
    table_type
FROM 
    information_schema.tables
WHERE 
    table_schema = 'public'
    AND table_name IN ('alert_settings', 'posture_settings')
ORDER BY 
    table_name;

-- 2. Ver estructura de alert_settings
SELECT 
    column_name,
    data_type,
    is_nullable,
    column_default
FROM 
    information_schema.columns
WHERE 
    table_schema = 'public'
    AND table_name = 'alert_settings'
ORDER BY 
    ordinal_position;

-- 3. Ver estructura de posture_settings
SELECT 
    column_name,
    data_type,
    is_nullable,
    column_default
FROM 
    information_schema.columns
WHERE 
    table_schema = 'public'
    AND table_name = 'posture_settings'
ORDER BY 
    ordinal_position;

-- 4. Contar registros
SELECT 'alert_settings' as table_name, COUNT(*) as count FROM alert_settings
UNION ALL
SELECT 'posture_settings', COUNT(*) FROM posture_settings;

-- 5. Ver todos los datos de alert_settings
SELECT * FROM alert_settings ORDER BY id;

-- 6. Ver todos los datos de posture_settings
SELECT * FROM posture_settings ORDER BY id;

-- 7. Insertar datos de prueba (opcional)
-- Descomentar si quieres insertar datos de prueba manualmente

/*
INSERT INTO alert_settings (
    user_id, 
    visual_alerts_enabled, 
    sound_alerts_enabled, 
    alert_volume, 
    alert_interval,
    created_at,
    updated_at
) VALUES (
    1,
    true,
    true,
    75,
    30,
    NOW(),
    NOW()
);

INSERT INTO posture_settings (
    user_id,
    posture_sensitivity,
    shoulder_angle_threshold,
    head_angle_threshold,
    sampling_frequency,
    show_skeleton,
    shoulder_angle,
    neck_angle,
    back_angle,
    head_tilt,
    created_at,
    updated_at
) VALUES (
    1,
    75,
    20,
    15,
    2,
    true,
    20,
    15,
    25,
    10,
    NOW(),
    NOW()
);
*/

-- 8. Consultas útiles

-- Ver configuración de alertas por usuario
SELECT 
    id,
    user_id,
    visual_alerts_enabled,
    sound_alerts_enabled,
    alert_volume,
    alert_interval,
    created_at,
    updated_at
FROM 
    alert_settings
WHERE 
    user_id = 1;

-- Ver configuración postural por usuario
SELECT 
    id,
    user_id,
    posture_sensitivity,
    shoulder_angle_threshold,
    head_angle_threshold,
    sampling_frequency,
    show_skeleton,
    shoulder_angle,
    neck_angle,
    back_angle,
    head_tilt,
    created_at,
    updated_at
FROM 
    posture_settings
WHERE 
    user_id = 1;

-- 9. Limpiar datos de prueba (opcional)
/*
DELETE FROM alert_settings WHERE user_id = 1;
DELETE FROM posture_settings WHERE user_id = 1;
*/

-- 10. Verificar índices
SELECT
    tablename,
    indexname,
    indexdef
FROM
    pg_indexes
WHERE
    schemaname = 'public'
    AND tablename IN ('alert_settings', 'posture_settings')
ORDER BY
    tablename, indexname;
