-- Script para agregar la columna user_id a monitoring_sessions
-- Ejecutar este script antes de reiniciar la aplicación

-- Agregar la columna user_id (permitiendo NULL temporalmente)
ALTER TABLE monitoring_sessions 
ADD COLUMN user_id BIGINT;

-- Si tienes datos existentes, puedes asignarles un usuario por defecto
-- Descomenta la siguiente línea y reemplaza 1 con un ID de usuario válido
-- UPDATE monitoring_sessions SET user_id = 1 WHERE user_id IS NULL;

-- Ahora hacer la columna NOT NULL
ALTER TABLE monitoring_sessions 
ALTER COLUMN user_id SET NOT NULL;

-- Verificar la estructura actualizada
SELECT column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'monitoring_sessions';
