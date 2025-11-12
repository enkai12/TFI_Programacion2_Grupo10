package Dao;

/**
 * Data Access Object para la entidad Empleado.
 * Gestiona todas las operaciones de persistencia de empleados en la base de datos.
 *
 * Características:
 * - Implementa GenericDAO<Empleado> para operaciones CRUD estándar
 * - Usa PreparedStatements en TODAS las consultas (protección contra SQL injection)
 * - Proporciona búsquedas especializadas
 *
 * Patrón: DAO con try-with-resources para manejo automático de recursos JDBC
 */
public class EmpleadoDAO implements GenericDAO<Empleado> {
    
    
}
