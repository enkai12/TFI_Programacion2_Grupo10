package Dao;


/**
 * Data Access Object para la entidad Legajo.
 * Gestiona todas las operaciones de persistencia de legajos en la base de datos.
 *
 * Características:
 * - Implementa GenericDAO<Legajo> para operaciones CRUD estándar
 * - Usa PreparedStatements en TODAS las consultas (protección contra SQL injection)
 *
 * Diferencias con LegajoDAO:
 * 
 * 
 * 
 *
 * Patrón: DAO con try-with-resources para manejo automático de recursos JDBC
 */
public class LegajoDAO implements GenericDAO<Legajo> {
   
}
