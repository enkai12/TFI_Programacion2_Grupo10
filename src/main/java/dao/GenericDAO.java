package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz Genérica DAO (Data Access Object).
 * Define el contrato estándar para todas las operaciones CRUD, usando los métodos solicitados por la consigna
 * (crear(T), leer(long id), etc.), que las clases DAO concretas (como EmpleadoDAO, LegajoDAO) deben implementar.
 * <p>
 * Se añaden métodos terminados en 'Tx' (Transacción) para la participación en una transacción más grande
 * controlada por otra capa (Servicio), no por una capa aislada.
 * Los métodos '*Tx' aceptan un parámetro {@link Connection} que les permite reutilizar
 * una conexión ya abierta sin abrir/cerrar su propia conexión.
 *
 * @param <T> El tipo de la entidad (Empleado, Legajo, etc.).
 */
public interface GenericDAO<T> {

    /**
     * Mensaje estándar para operaciones transaccionales no soportadas.
     */
    String UNSUPPORTED_TRANSACTION_MESSAGE =
            "Operación transaccional no soportada por este DAO. " +
                    "Sobrescriba el método *Tx correspondiente para habilitarla.";

    /**
     * Inserta una nueva entidad en la base de datos.
     * Esta versión opera con su propia conexión y autocommit.
     *
     * @param entidad La entidad a crear (T).
     * @throws SQLException Si ocurre un error de base de datos.
     */
    void crear(T entidad) throws SQLException;

    /**
     * Inserta una nueva entidad dentro de una transacción existente.
     * Acepta una Connection externa para participar en una transacción
     * manejada por la capa de Servicio. NO cierra la conexión.
     * <p>
     * Implementación por defecto:
     * - Lanza {@link UnsupportedOperationException} indicando que la operación
     *   transaccional no está soportada.
     * - Los DAO que deseen participar en transacciones deben sobrescribir este método.
     *
     * @param entidad La entidad a crear (T).
     * @param conn    La conexión transaccional externa (no se cierra aquí).
     * @throws SQLException                  Si ocurre un error de base de datos.
     * @throws UnsupportedOperationException Si la implementación no soporta operaciones transaccionales.
     */
    default void crearTx(T entidad, Connection conn) throws SQLException {
        throw new UnsupportedOperationException(UNSUPPORTED_TRANSACTION_MESSAGE);
    }

    /**
     * Actualiza una entidad existente en la base de datos.
     * Esta versión opera con su propia conexión y autocommit.
     *
     * @param entidad La entidad con los datos actualizados.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    void actualizar(T entidad) throws SQLException;

    /**
     * Actualiza una entidad dentro de una transacción existente.
     * Permite a la capa de Servicio realizar actualizaciones compuestas
     * (por ejemplo, actualizar Empleado y Legajo juntos). No cierra la conexión.
     * <p>
     * Implementación por defecto:
     * - Lanza {@link UnsupportedOperationException} indicando que la operación
     *   transaccional no está soportada.
     * - Los DAO que deseen participar en transacciones deben sobrescribir este método.
     *
     * @param entidad La entidad con los datos actualizados.
     * @param conn    La conexión transaccional externa (no se cierra aquí).
     * @throws SQLException                  Si ocurre un error de base de datos.
     * @throws UnsupportedOperationException Si la implementación no soporta operaciones transaccionales.
     */
    default void actualizarTx(T entidad, Connection conn) throws SQLException {
        throw new UnsupportedOperationException(UNSUPPORTED_TRANSACTION_MESSAGE);
    }

    /**
     * Realiza una baja lógica de una entidad por su ID.
     * (Por ejemplo UPDATE SET eliminado = TRUE WHERE id = ?).
     * Esta versión opera con su propia conexión y autocommit.
     *
     * @param id El ID (long) de la entidad a eliminar.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    void eliminar(long id) throws SQLException;

    /**
     * Realiza una baja lógica dentro de una transacción existente. No cierra la conexión.
     * <p>
     * Implementación por defecto:
     * - Lanza {@link UnsupportedOperationException} indicando que la operación
     *   transaccional no está soportada.
     * - Los DAO que deseen participar en transacciones deben sobrescribir este método.
     *
     * @param id   El ID (long) de la entidad a eliminar.
     * @param conn La conexión transaccional externa (no se cierra aquí).
     * @throws SQLException                  Si ocurre un error de base de datos.
     * @throws UnsupportedOperationException Si la implementación no soporta operaciones transaccionales.
     */
    default void eliminarTx(long id, Connection conn) throws SQLException {
        throw new UnsupportedOperationException(UNSUPPORTED_TRANSACTION_MESSAGE);
    }

    /**
     * Obtiene una entidad por su ID.
     * Debe filtrar para no incluir registros con baja lógica (eliminado = TRUE).
     *
     * @param id El ID (long) de la entidad a buscar.
     * @return La entidad (T) encontrada, o null si no existe.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    T leer(long id) throws SQLException;

    /**
     * Obtiene una lista de todas las entidades activas.
     * Debe filtrar para no incluir registros con baja lógica (eliminado = TRUE).
     *
     * @return Una List<T> con todas las entidades activas.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    List<T> leerTodos() throws SQLException;

}