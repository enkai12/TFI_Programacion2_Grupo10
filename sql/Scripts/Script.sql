-- -----------------------------------------------------
-- Script.sql - Consultas de prueba manuales
-- TFI Programación 2 - UTN
-- NO modifica estructura ni datos base críticos
-- -----------------------------------------------------

USE tpi_prog2_empleados;

-- =====================================================
-- 1) CONSULTAS BÁSICAS DE VERIFICACIÓN
-- =====================================================

-- 1.1) Ver todos los empleados activos
SELECT *
FROM empleado
WHERE eliminado = FALSE
ORDER BY id;

-- 1.2) Ver todos los legajos activos
SELECT *
FROM legajo
WHERE eliminado = FALSE
ORDER BY id;

-- 1.3) Ver empleados junto con su legajo (relación 1→1)
SELECT e.id              AS emp_id,
       e.nombre,
       e.apellido,
       e.dni,
       e.email,
       e.fecha_ingreso,
       e.area,
       e.eliminado       AS emp_eliminado,
       l.id              AS leg_id,
       l.nro_legajo,
       l.categoria,
       l.estado,
       l.fecha_alta,
       l.observaciones,
       l.eliminado       AS leg_eliminado
FROM empleado e
         LEFT JOIN legajo l
                   ON e.id = l.empleado_id
ORDER BY e.id;

-- =====================================================
-- 2) BÚSQUEDAS ESPECÍFICAS (para testear DAO / Service)
-- =====================================================

-- 2.1) Buscar empleado por DNI (coincide con EmpleadoDAO.buscarPorDni)
-- Reemplazar '30123456' por el DNI que se quiera probar
SELECT e.id              AS emp_id,
       e.nombre,
       e.apellido,
       e.dni,
       e.email,
       e.fecha_ingreso,
       e.area,
       e.eliminado       AS emp_eliminado,
       l.id              AS leg_id,
       l.nro_legajo,
       l.categoria,
       l.estado,
       l.fecha_alta,
       l.observaciones,
       l.eliminado       AS leg_eliminado
FROM empleado e
         LEFT JOIN legajo l
                   ON e.id = l.empleado_id
                       AND l.eliminado = FALSE
WHERE e.dni = '30123456'
  AND e.eliminado = FALSE;

-- 2.2) Buscar empleados por nombre o apellido (LIKE)
-- Reemplazar '%gar%' por el patrón que quieras probar
SELECT e.id              AS emp_id,
       e.nombre,
       e.apellido,
       e.dni,
       e.email,
       e.fecha_ingreso,
       e.area,
       e.eliminado       AS emp_eliminado,
       l.id              AS leg_id,
       l.nro_legajo,
       l.categoria,
       l.estado,
       l.fecha_alta,
       l.observaciones,
       l.eliminado       AS leg_eliminado
FROM empleado e
         LEFT JOIN legajo l
                   ON e.id = l.empleado_id
                       AND l.eliminado = FALSE
WHERE (e.nombre   LIKE '%gar%'
    OR e.apellido LIKE '%gar%')
  AND e.eliminado = FALSE
ORDER BY e.apellido, e.nombre;

-- 2.3) Buscar legajo por número (coincide con LegajoDAO.buscarPorNroLegajo)
-- Reemplazar 'DEV-2020-001' por el número de legajo que se quiera probar
SELECT *
FROM legajo
WHERE nro_legajo = 'DEV-2020-001'
  AND eliminado = FALSE;

-- =====================================================
-- 3) CASOS ESPECIALES Y BORRADO LÓGICO
-- =====================================================

-- 3.1) Ver empleados marcados como eliminados (baja lógica)
SELECT *
FROM empleado
WHERE eliminado = TRUE
ORDER BY id;

-- 3.2) Ver legajos marcados como eliminados (baja lógica)
SELECT *
FROM legajo
WHERE eliminado = TRUE
ORDER BY id;

-- 3.3) Empleados con legajo INACTIVO (por ejemplo licencia)
SELECT e.id              AS emp_id,
       e.nombre,
       e.apellido,
       e.dni,
       e.eliminado       AS emp_eliminado,
       l.id              AS leg_id,
       l.nro_legajo,
       l.estado,
       l.eliminado       AS leg_eliminado
FROM empleado e
         JOIN legajo l
              ON e.id = l.empleado_id
WHERE l.estado   = 'INACTIVO'
  AND e.eliminado = FALSE;

-- 3.4) Empleados activos sin legajo asociado (si existieran)
SELECT e.*
FROM empleado e
         LEFT JOIN legajo l
                   ON e.id = l.empleado_id
                       AND l.eliminado = FALSE
WHERE e.eliminado = FALSE
  AND l.id IS NULL;

-- =====================================================
-- 4) PRUEBAS DE ÍNDICES / PERFORMANCE BÁSICA
--    (consultas típicas que se beneficiarían de índices)
-- =====================================================

-- 4.1) Búsqueda por área (usa idx_empleado_area)
SELECT *
FROM empleado
WHERE area = 'Desarrollo'
  AND eliminado = FALSE
ORDER BY fecha_ingreso DESC;

-- 4.2) Búsqueda de legajos por estado y categoría (usa idx_legajo_estado_categoria)
SELECT *
FROM legajo
WHERE estado   = 'ACTIVO'
  AND categoria = 'Semi-Senior'
  AND eliminado = FALSE
ORDER BY fecha_alta DESC;

-- =====================================================
-- 5) BLOQUE OPCIONAL: PRUEBAS RÁPIDAS DE TRANSACCIONES
--    (usar con cuidado; ideal ejecutarlo manualmente)
-- =====================================================

-- Ejemplo: empezar transacción, hacer cambios y luego ROLLBACK.
-- Esto sirve para probar que la aplicación maneja correctamente commit/rollback.
-- IMPORTANTE: ejecutar este bloque completo de forma manual cuando se necesite.

-- START TRANSACTION;
--   -- Actualizar algo de prueba (no olvidar hacer rollback después)
--   UPDATE empleado
--   SET area = 'Desarrollo - TEST'
--   WHERE id = 1;
--
--   SELECT *
--   FROM empleado
--   WHERE id = 1;
--
-- ROLLBACK;