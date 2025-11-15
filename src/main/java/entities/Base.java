package entities;

/**
 * Clase base abstracta para todas las entidades del sistema.
 *
 * Propósito:
 * - Proporcionar atributos comunes a las entidades Empleado y Legajo
 * - Implementar el patrón de herencia para evitar duplicación de código
 * - Soportar eliminación lógica en lugar de eliminación física
 *
 * Patrón de diseño: Template (clase base abstracta)
 */
public abstract class Base {

    /**
     * Identificador técnico de la entidad.
     * Se espera que sea autogenerado por la capa de persistencia.
     */
    private long id;

    /**
     * Indica si la entidad fue eliminada de forma lógica (soft delete).
     */
    private boolean eliminado;

    /**
     * Constructor protegido para que solo las subclases puedan instanciarse.
     * Deja que la capa de persistencia asigne el ID cuando corresponda.
     */
    protected Base() {
        // Inicialización por defecto provista por Java (id = 0, eliminado = false)
    }

    // Getters y Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    /**
     * Marca la entidad como eliminada lógicamente.
     * Método de utilidad para ser reutilizado por las subclases en eliminarRegistro().
     */
    protected final void marcarEliminado() {
        this.eliminado = true;
    }

    // Métodos abstractos

    /**
     * Define cómo se realiza la eliminación lógica de la entidad.
     * Las subclases deben implementar este método (por ejemplo,
     * llamando a {@link #marcarEliminado()} y aplicando reglas específicas).
     */
    public abstract void eliminarRegistro(); // Permitirá la eliminación lógica
}