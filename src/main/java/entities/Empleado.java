package entities;

import java.time.LocalDate;

/**
 * Entidad que representa un empleado en el sistema. Hereda de Base para obtener
 * id y eliminado.
 *
 * Relación con Legajo: Asociación unidireccional 1 a 1. Empleado -> Legajo
 *
 * Tabla BD: empleado
 * Campos:
 * - id: BIGINT AUTO_INCREMENT PRIMARY KEY (heredado de Base)
 * - eliminado: BOOLEAN DEFAULT FALSE (heredado de Base)
 * - nombre: VARCHAR(80) NOT NULL
 * - apellido: VARCHAR(80) NOT NULL
 * - dni: VARCHAR(15) UNIQUE NOT NULL
 * - email: VARCHAR(120)
 * - fecha_ingreso: DATE
 * - area: VARCHAR(50)
 */
public class Empleado extends Base {

    private static final String LEGAJO_NO_ASIGNADO = "No asignado";

    // Atributos propios
    private String nombre;    // obligatorio
    private String apellido;  // obligatorio
    private String dni;       // obligatorio, único e irrepetible
    private String email;     // opcional, único
    private LocalDate fechaIngreso; // Inicio de la relación laboral
    private String area;

    // Asociación 1:1 unidireccional con Legajo
    private Legajo legajo;

    // Constructores
    public Empleado(String nombre,
                    String apellido,
                    String dni,
                    String email,
                    LocalDate fechaIngreso,
                    String area,
                    Legajo legajo) {
        super(); // Llama al constructor de Base (id=0, eliminado=false)
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.area = area;
        this.legajo = legajo;
    }

    public Empleado() { // Explícito, para el Menú y su construcción a través de setters
        super();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Legajo getLegajo() {
        return legajo;
    }

    public void setLegajo(Legajo legajo) {
        this.legajo = legajo;
    }

    @Override
    public void eliminarRegistro() { // Borrado lógico
        marcarEliminado();
    }

    @Override
    public String toString() {
        return "Empleado (ID: " + getId() + ")"
                + "\nNombre: " + getNombre()
                + "\nApellido: " + getApellido()
                + "\nDNI: " + getDni()
                + "\nEmail: " + getEmail()
                + "\nFecha de Ingreso: " + getFechaIngreso()
                + "\nArea: " + getArea()
                + "\nEliminado: " + isEliminado()
                + "\nLegajo: " + buildLegajoDescription();
    }

    private String buildLegajoDescription() {
        return (legajo != null) ? legajo.toString() : LEGAJO_NO_ASIGNADO;
    }
}