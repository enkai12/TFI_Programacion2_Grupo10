package service;

/**
 * Excepción personalizada para la capa de Servicio.
 * Se usa para notificar a la capa de presentación (MenuHandler, AppMenu)
 * sobre errores de negocio o de acceso a datos de una forma controlada.
 */
public class ServiceException extends Exception {

    /**
     * Constructor básico.
     * 
     * @param message Mensaje que describe el error.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructor que envuelve la causa original.
     * Útil para propagar errores de la capa DAO (ej. SQLException)
     * sin exponer los detalles de implementación.
     *
     * @param message Mensaje que describe el error.
     * @param cause   La excepción original que causó este error.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}