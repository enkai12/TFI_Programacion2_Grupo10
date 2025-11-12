package dao;


/**
 * Data Access Object para la entidad Legajo.
 * Gestiona todas las operaciones de persistencia de legajos en la base de datos.
 *
 * Características:
 * - Implementa GenericDAO<Legajo> para operaciones CRUD estándar
 - Usa PreparedStatements en TODAS las consultas (protección contra SQL injection)

 Diferencias con LegajoDao:




 Patrón: DAO con try-with-resources para manejo automático de recursos JDBC
 */
public class LegajoDao implements GenericDao<Legajo> {
   
}
