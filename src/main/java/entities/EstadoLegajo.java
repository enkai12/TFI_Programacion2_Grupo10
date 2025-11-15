package entities;

/**
 * Define la situación contractual del empleado dentro de un {@code Legajo}.
 *
 * - {@link #ACTIVO}: El empleado se encuentra trabajando.
 * - {@link #INACTIVO}: El empleado no se encuentra trabajando (sin importar el motivo).
 */
public enum EstadoLegajo {

    /**
     * El empleado se encuentra trabajando.
     */
    ACTIVO("Empleado activo / en servicio"),

    /**
     * El empleado no se encuentra trabajando (sin importar el motivo).
     */
    INACTIVO("Empleado inactivo / fuera de servicio");

    private final String descripcion;

    EstadoLegajo(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve una descripción legible del estado contractual.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Indica si el legajo corresponde a un empleado actualmente activo.
     */
    public boolean isActivo() {
        return this == ACTIVO;
    }

    /**
     * Indica si el legajo corresponde a un empleado actualmente inactivo.
     */
    public boolean isInactivo() {
        return this == INACTIVO;
    }
}