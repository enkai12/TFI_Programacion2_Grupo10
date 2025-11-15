-- -----------------------------------------------------
-- Script: DATOS DE PRUEBA
-- TFI Programación 2 - UTN
-- -----------------------------------------------------
USE
tpi_prog2_empleados;

-- empleados activos con legajos válidos
INSERT INTO empleado (nombre,
                      apellido,
                      dni,
                      email,
                      fecha_ingreso,
                      area,
                      eliminado)
VALUES
    -- Desarrollo (4 empleados)
    ('Juan', 'Pérez', '30123456', 'juan.perez@empresa.com', '2020-05-10', 'Desarrollo', FALSE),
    ('María', 'Gómez', '35789012', 'maria.gomez@empresa.com', '2023-11-01', 'Desarrollo', FALSE),
    ('Carlos', 'López', '32456789', 'carlos.lopez@empresa.com', '2021-02-15', 'Desarrollo', FALSE),
    ('Ana', 'Rodríguez', '33445566', 'ana.rodriguez@empresa.com', '2022-08-20', 'Desarrollo', FALSE),
    -- Ventas (3 empleados)
    ('Luis', 'Martínez', '31234567', 'luis.martinez@empresa.com', '2019-03-12', 'Ventas', FALSE),
    ('Sofia', 'García', '29876543', 'sofia.garcia@empresa.com', '2020-07-25', 'Ventas', FALSE),
    ('Diego', 'Fernández', '32654321', 'diego.fernandez@empresa.com', '2023-01-30', 'Ventas', FALSE),
    -- RRHH (2 empleados)
    ('Valeria', 'Dominguez', '34567890', 'valeria.dominguez@empresa.com', '2018-11-05', 'RRHH', FALSE),
    ('Miguel', 'Silva', '31887766', 'miguel.silva@empresa.com', '2021-09-15', 'RRHH', FALSE),
    -- Contabilidad (2 empleados)
    ('Laura', 'Torres', '33221144', 'laura.torres@empresa.com', '2017-06-18', 'Contabilidad', FALSE),
    ('Andrés', 'Ramírez', '30998877', 'andres.ramirez@empresa.com', '2022-04-22', 'Contabilidad', FALSE),
    -- Marketing (2 empleados)
    ('Carmen', 'Vargas', '35566778', 'carmen.vargas@empresa.com', '2020-12-10', 'Marketing', FALSE),
    ('Roberto', 'Castro', '32765432', 'roberto.castro@empresa.com', '2023-03-08', 'Marketing', FALSE),
    -- Soporte Técnico (2 empleados)
    ('Elena', 'Mendoza', '34443322', 'elena.mendoza@empresa.com', '2019-08-14', 'Soporte', FALSE),
    ('Javier', 'Ríos', '33665544', 'javier.rios@empresa.com', '2021-05-19', 'Soporte', FALSE);

-- legajos para empleados activos
INSERT INTO legajo (nro_legajo,
                    categoria,
                    estado,
                    fecha_alta,
                    observaciones,
                    empleado_id,
                    eliminado)
VALUES
    -- Desarrollo
    ('DEV-2020-001', 'Senior', 'ACTIVO', '2020-05-10', 'Tech Lead - Especialista en Java y Microservicios', 1, FALSE),
    ('DEV-2023-001', 'Junior', 'ACTIVO', '2023-11-01', 'Recién incorporado - En programa de entrenamiento', 2, FALSE),
    ('DEV-2021-001', 'Semi-Senior', 'ACTIVO', '2021-02-15', 'Full Stack Developer - React y Node.js', 3, FALSE),
    ('DEV-2022-001', 'Semi-Senior', 'ACTIVO', '2022-08-20', 'Mobile Developer - React Native', 4, FALSE),
    -- Ventas
    ('VTS-2019-001', 'Senior', 'ACTIVO', '2019-03-12', 'Team Leader Ventas Corporativas', 5, FALSE),
    ('VTS-2020-001', 'Semi-Senior', 'ACTIVO', '2020-07-25', 'Ejecutiva de Cuentas Estratégicas', 6, FALSE),
    ('VTS-2023-001', 'Junior', 'ACTIVO', '2023-01-30', 'Asistente Comercial', 7, FALSE),
    -- RRHH
    ('RRHH-2018-001', 'Senior', 'ACTIVO', '2018-11-05', 'Gerente de Recursos Humanos', 8, FALSE),
    ('RRHH-2021-001', 'Semi-Senior', 'ACTIVO', '2021-09-15', 'Especialista en Reclutamiento IT', 9, FALSE),
    -- Contabilidad
    ('CONT-2017-001', 'Senior', 'ACTIVO', '2017-06-18', 'Contadora Pública - Jefa de Departamento', 10, FALSE),
    ('CONT-2022-001', 'Junior', 'ACTIVO', '2022-04-22', 'Asistente Contable', 11, FALSE),
    -- Marketing
    ('MKT-2020-001', 'Semi-Senior', 'ACTIVO', '2020-12-10', 'Especialista en Marketing Digital', 12, FALSE),
    ('MKT-2023-001', 'Junior', 'ACTIVO', '2023-03-08', 'Community Manager', 13, FALSE),
    -- Soporte Técnico
    ('SOP-2019-001', 'Semi-Senior', 'ACTIVO', '2019-08-14', 'Soporte Nivel 2', 14, FALSE),
    ('SOP-2021-001', 'Junior', 'ACTIVO', '2021-05-19', 'Soporte Nivel 1', 15, FALSE);

-- empleados inactivos o eliminados para testear
INSERT INTO empleado (nombre,
                      apellido,
                      dni,
                      email,
                      fecha_ingreso,
                      area,
                      eliminado)
VALUES ('Lucía', 'Hernández', '28001002', 'lucia.hernandez@ex-empresa.com', '2019-01-01', 'RRHH', TRUE),
       ('Pedro', 'Gutiérrez', '29555001', 'pedro.gutierrez@ex-empresa.com', '2018-07-15', 'Desarrollo', TRUE),
       ('Marta', 'Suárez', '27123456', 'marta.suarez@ex-empresa.com', '2016-03-20', 'Contabilidad', TRUE);

INSERT INTO legajo (nro_legajo,
                    categoria,
                    estado,
                    fecha_alta,
                    observaciones,
                    empleado_id,
                    eliminado)
VALUES ('RRHH-2019-002', 'Senior', 'INACTIVO', '2019-01-01', 'Baja voluntaria - Renuncia', 16, TRUE),
       ('DEV-2018-002', 'Senior', 'INACTIVO', '2018-07-15', 'Despido - Reducción de personal', 17, TRUE),
       ('CONT-2016-001', 'Senior', 'INACTIVO', '2016-03-20', 'Jubilación', 18, TRUE);

-- casos especiales para testear

-- Empleado activo con legajo INACTIVO (licencia)
INSERT INTO empleado (nombre,
                      apellido,
                      dni,
                      email,
                      fecha_ingreso,
                      area,
                      eliminado)
VALUES ('Gabriela', 'Molina', '33889900', 'gabriela.molina@empresa.com', '2020-09-10', 'Ventas', FALSE);

INSERT INTO legajo (nro_legajo,
                    categoria,
                    estado,
                    fecha_alta,
                    observaciones,
                    empleado_id,
                    eliminado)
VALUES ('VTS-2020-002', 'Semi-Senior', 'INACTIVO', '2020-09-10', 'Licencia médica prolongada', 19, FALSE);

-- Empleado recién incorporado (sin observaciones)
INSERT INTO empleado (nombre,
                      apellido,
                      dni,
                      email,
                      fecha_ingreso,
                      area,
                      eliminado)
VALUES ('Fernando', 'Luna', '36667788', 'fernando.luna@empresa.com', '2024-01-08', 'Marketing', FALSE);

INSERT INTO legajo (nro_legajo,
                    categoria,
                    estado,
                    fecha_alta,
                    observaciones,
                    empleado_id,
                    eliminado)
VALUES ('MKT-2024-001', 'Trainee', 'ACTIVO', '2024-01-08', NULL, 20, FALSE);