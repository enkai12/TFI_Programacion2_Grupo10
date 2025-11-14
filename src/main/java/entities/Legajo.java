package entities;

// import java.util.Date; esto es antiguo..
import java.time.LocalDate;


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
    
    // Constructor
    
    public Legajo(String numeroLegajo, String categoria, EstadoLegajo estado, 
                  LocalDate fechaAlta, String observaciones) {
        super(); // Llama al constructor de Base (id=0, eliminado=false)
        this.numeroLegajo = numeroLegajo;
        this.categoria = categoria;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
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

    @Override
    public void eliminarRegistro() {  // Borrado lógico
        this.setEliminado(true);  // Para el atributo eliminado de la superclase
        this.setEstado(EstadoLegajo.INACTIVO); // Es necesario también cambiar el estado de legajo por su situación contractual.
        
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
