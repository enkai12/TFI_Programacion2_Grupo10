package entities;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidad que representa un legajo en el sistema.
 * Hereda de Base para obtener id y eliminado.
 *
 * Relación con Empleado:
 * Asociación unidireccional 1 a 1.
 * Empleado -> Legajo
 *
 * Tabla BD: legajo
 * Campos:
 * - id: BIGINT AUTO_INCREMENT PRIMARY KEY (heredado de Base)
 * - eliminado: BOOLEAN DEFAULT FALSE (heredado de Base)
 * - nro_legajo: VARCHAR(20) NOT NULL
 * - categoria: VARCHAR(30)
 * - estado: ENUM('ACTIVO','INACTIVO') NOT NULL
 * - fecha_alta: DATE NOT NULL
 * - observaciones: VARCHAR(255)
 */
public class Legajo extends Base {

    // Atributos propios
    private String numeroLegajo;
    private String categoria;
    private EstadoLegajo estado; // Enum (estado contractual activo o inactivo)
    private LocalDate fechaAlta;
    private String observaciones;

    /**
     * Crea un nuevo Legajo con los datos obligatorios y opcionales.
     * Los campos numeroLegajo, estado y fechaAlta no pueden ser nulos.
     */
    public Legajo(String numeroLegajo, String categoria, EstadoLegajo estado,
                  LocalDate fechaAlta, String observaciones) {
        super(); // Llama al constructor de Base (id=0, eliminado=false)
        this.numeroLegajo = Objects.requireNonNull(numeroLegajo, "numeroLegajo no puede ser null");
        this.categoria = categoria;
        this.estado = Objects.requireNonNull(estado, "estado no puede ser null");
        this.fechaAlta = Objects.requireNonNull(fechaAlta, "fechaAlta no puede ser null");
        this.observaciones = observaciones;
    }

    public Legajo() {
        super(); // Inicializa id=0, eliminado=false
    }


    // Getters y Setters
    public String getNumeroLegajo() {
        return numeroLegajo;
    }
    public void setNumeroLegajo(String numeroLegajo) {
        this.numeroLegajo = numeroLegajo;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public EstadoLegajo getEstado() {
        return estado;
    }
    public void setEstado(EstadoLegajo estado) {
        this.estado = estado;
    }
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // Métodos
    /**
     * Realiza la baja lógica del legajo:
     * - Marca la entidad como eliminada (soft delete) usando la lógica común de Base.
     * - Actualiza el estado contractual del legajo a INACTIVO.
     */
    @Override
    public void eliminarRegistro() {  // Borrado lógico
        marcarEliminado(); // Método protegido de la superclase para soft delete
        setEstado(EstadoLegajo.INACTIVO); // Cambia el estado contractual del legajo
    }

    @Override
    public String toString() {
        return "Legajo [Número: " + getNumeroLegajo()
                + ", Categoría: " + getCategoria()
                + ", Estado: " + getEstado()
                + ", Fecha Alta: " + getFechaAlta()
                + ", Observaciones: " + getObservaciones() + "]";
    }

}