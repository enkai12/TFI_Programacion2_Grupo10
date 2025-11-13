-- -----------------------------------------------------
-- CREACIÓN DE ESTRUCTURA
-- TFI Programación 2 - UTN
-- Dominio: Empleado -> Legajo (A->B) (1→1 unidireccional)
-- -----------------------------------------------------

-- Crear bd
DROP DATABASE IF EXISTS tpi_prog2_empleados;
CREATE DATABASE IF NOT EXISTS tpi_prog2_empleados
CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE tpi_prog2_empleados;

-- Tabla Empleado (Clase A - Padre)
CREATE TABLE IF NOT EXISTS empleado (
    id BIGINT NOT NULL AUTO_INCREMENT,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    nombre VARCHAR(80) NOT NULL,
    apellido VARCHAR(80) NOT NULL,
    dni VARCHAR(15) NOT NULL,
    email VARCHAR(120),
    fecha_ingreso DATE,
    area VARCHAR(50),
    
    -- Timestamps para auditorías
    -- permiten rastrear cuándo se creo y/o modificó cada registro 
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    PRIMARY KEY (id),
    -- El DNI debe ser único
    UNIQUE INDEX uk_dni (dni ASC),
    
    -- Índices que van ayudar a optimizar las consultas
    INDEX idx_empleado_nombre_apellido (nombre ASC, apellido ASC),
    INDEX idx_empleado_area (area ASC),
    INDEX idx_empleado_eliminado (eliminado ASC),
    INDEX idx_empleado_fecha_ingreso (fecha_ingreso ASC)
) ENGINE = InnoDB;


-- Tabla Legajo (Clase B - Hijo) Depende de Empleado
CREATE TABLE IF NOT EXISTS legajo (
    id BIGINT NOT NULL AUTO_INCREMENT,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    nro_legajo VARCHAR(20) NOT NULL,
    categoria VARCHAR(30),
    estado ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'ACTIVO', -- el default activo previene que se inserten legajos con un estado null
    fecha_alta DATE NOT NULL, -- el legajo no puede existir si no tiene una fecha de alta
    observaciones VARCHAR(255),
    
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- fk
    empleado_id BIGINT NOT NULL,
    
    PRIMARY KEY (id),
    -- El legajo debe ser único
    UNIQUE INDEX uk_nro_legajo (nro_legajo ASC),
    -- un empleado solo puede tener un legajo 1-1
    UNIQUE INDEX uk_empleado_id (empleado_id ASC),
    
    -- indices para consultas
    INDEX idx_legajo_estado (estado ASC),
    INDEX idx_legajo_categoria (categoria ASC),
    INDEX idx_legajo_eliminado (eliminado ASC),
    INDEX idx_legajo_fecha_alta (fecha_alta ASC),
    INDEX idx_legajo_estado_categoria (estado ASC, categoria ASC),
    
    -- pk
    CONSTRAINT fk_legajo_empleado
        FOREIGN KEY (empleado_id)
        REFERENCES empleado (id)
        -- ON DELETE CASCADE: Asegura que si se borra un Empleado (A),
        -- su Legajo (B) asociado se borre automáticamente
        ON DELETE CASCADE
        ON UPDATE NO ACTION -- la pk no debe cambiar
) ENGINE = InnoDB;