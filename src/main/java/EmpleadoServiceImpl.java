package Service;

/**
 * Implementación del servicio de negocio para la entidad Empleado.
 * Capa intermedia entre la UI y el DAO que aplica validaciones de negocio complejas.
 *
 * Responsabilidades:
 * - Validar datos de persona ANTES de persistir (......)
 * - Garantizar unicidad en el sistema (.....)
 * - COORDINAR operaciones entre Empleado y Legajo (transaccionales)
 * - Proporcionar métodos de búsqueda especializados
 * - Implementar eliminación SEGURA de empleado/legajo (evita FKs huérfanas)
 *
 * Patrón: Service Layer con inyección de dependencias y coordinación de servicios
 */
public class EmpleadoServiceImpl implements EmpleadoService<Empleado> {
    /**
     * DAO para acceso a datos de empleados.
     * Inyectado en el constructor (Dependency Injection).
     */
    private final EmpleadoDAO empleadoDAO;

    

}
