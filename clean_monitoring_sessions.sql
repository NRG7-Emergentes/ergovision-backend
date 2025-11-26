-- Script para limpiar la tabla monitoring_sessions y permitir que Hibernate la recree
-- ADVERTENCIA: Esto eliminará todos los datos existentes de sesiones de monitoreo

-- Eliminar todos los registros
TRUNCATE TABLE monitoring_sessions CASCADE;

-- O si prefieres eliminar y recrear la tabla completa:
-- DROP TABLE IF EXISTS monitoring_sessions CASCADE;
