package dao;

import config.DatabaseConnection;
import entities.EstadoLegajo;
import entities.Legajo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la entidad Legajo. Implementa
 * GenericDAO<Legajo> y gestiona toda la persistencia de los objetos Legajo en
 * la base de datos.
 *
 * Características:
 * - Implementa GenericDAO<Legajo> para operaciones CRUD estándar.
 * - Usa PreparedStatements en TODAS las consultas.
 * - No realiza JOINs (es la entidad 'B' o 'hija').
 * - Maneja la inserción/actualización de la FK 'empleado_id'.
 * - Implementa baja lógica (eliminado = TRUE).
 *
 * Esta clase es una "entidad hija": depende de Empleado. Sus métodos de creación
 * deben ser llamados dentro de una transacción manejada por el Service, que provea el
 * 'empleado_id'.
 */
public class LegajoDAO implements GenericDAO<Legajo> {

    // --- QUERIES SQL (LEGAJO) ---

    /**
     * SQL para insertar un Legajo (B). Requiere el 'empleado_id' (FK de A).
     */
    private static final String INSERT_SQL =
            "INSERT INTO legajo (nro_legajo, categoria, estado, fecha_alta, observaciones, empleado_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * SQL para actualizar un Legajo. Permite cambiar todos los campos excepto
     * el 'empleado_id', ya que la relación 1-a-1 es fija.
     */
    private static final String UPDATE_SQL =
            "UPDATE legajo SET nro_legajo = ?, categoria = ?, estado = ?, fecha_alta = ?, observaciones = ? " +
                    "WHERE id = ?";

    /**
     * SQL para realizar una BAJA LÓGICA (soft delete).
     */
    private static final String DELETE_SQL =
            "UPDATE legajo SET eliminado = TRUE WHERE id = ?";

    /**
     * SQL para leer un Legajo por ID. Filtra por 'eliminado = FALSE' sin
     * necesidad de JOINs.
     */
    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM legajo WHERE id = ? AND eliminado = FALSE";

    /**
     * SQL para leer TODOS los Legajos. Filtra por 'eliminado = FALSE' sin
     * necesidad de JOINs.
     */
    private static final String SELECT_ALL_SQL =
            "SELECT * FROM legajo WHERE eliminado = FALSE";

    // --- QUERIES DE BÚSQUEDA ---

    /**
     * SQL para buscar un Legajo por 'nro_legajo' (búsqueda exacta).
     * El 'nro_legajo' es UNIQUE en la BD.
     */
    private static final String SELECT_BY_NRO_LEGAJO_SQL =
            "SELECT * FROM legajo WHERE nro_legajo = ? AND eliminado = FALSE";

    /**
     * SQL para buscar Legajos por 'estado'.
     */
    private static final String SELECT_BY_ESTADO_SQL =
            "SELECT * FROM legajo WHERE estado = ? AND eliminado = FALSE";

    // --- IMPLEMENTACIÓN DE MÉTODOS GENÉRICOS (GenericDAO) ---

    /**
     * Regla de negocio: un Legajo no puede crearse sin asociarse a un Empleado.
     * Debe usarse {@link #crearTx(Legajo, long, Connection)} dentro de una transacción.
     */
    @Override
    public void crear(Legajo legajo) throws SQLException {
        throw new UnsupportedOperationException(
                "No puede crear Legajo sin Empleado. " +
                        "Use crearTx(Legajo legajo, long empleadoId, Connection conn)."
        );
    }

    /**
     * Regla de negocio: este método genérico no se usa para Legajo porque requiere
     * un empleadoId explícito. Se fuerza a usar la versión especializada con empleadoId.
     */
    @Override
    public void crearTx(Legajo legajo, Connection conn) throws SQLException {
        throw new UnsupportedOperationException(GenericDAO.UNSUPPORTED_TRANSACTION_MESSAGE);
    }

    /**
     * MÉTODO ESPECIALIZADO PARA EL SERVICE.
     * Este es el método correcto para crear un Legajo (B).
     * Es llamado por el EmpleadoService dentro de una transacción.
     *
     * @param legajo     El objeto Legajo a insertar.
     * @param empleadoId El ID del Empleado (A) al que pertenece.
     * @param conn       La conexión transaccional.
     * @throws SQLException Si hay un error de SQL.
     */
    public void crearTx(Legajo legajo, long empleadoId, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            setInsertParameters(stmt, legajo, empleadoId);
            stmt.executeUpdate();
            assignGeneratedId(stmt, legajo);
        }
    }

    // --- Métodos de Actualización (Genéricos) ---

    @Override
    public void actualizar(Legajo legajo) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            executeUpdate(legajo, conn);
        }
    }

    @Override
    public void actualizarTx(Legajo legajo, Connection conn) throws SQLException {
        executeUpdate(legajo, conn);
    }

    // --- Métodos de Eliminación (Genéricos) ---

    @Override
    public void eliminar(long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            executeSoftDelete(id, conn);
        }
    }

    @Override
    public void eliminarTx(long id, Connection conn) throws SQLException {
        executeSoftDelete(id, conn);
    }

    // --- Métodos de Lectura (Genéricos) ---

    @Override
    public Legajo leer(long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return findByIdInternal(conn, id);
        }
    }

    @Override
    public List<Legajo> leerTodos() throws SQLException {
        List<Legajo> legajos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                legajos.add(mapRowToLegajo(rs));
            }
        }
        return legajos;
    }

    // --- MÉTODOS DE BÚSQUEDA ESPECIALIZADOS ---

    /**
     * Busca un Legajo por su 'nro_legajo' (búsqueda exacta).
     *
     * @param nroLegajo El número de legajo exacto a buscar.
     * @return El Legajo encontrado o null si no existe.
     * @throws SQLException Si hay un error de base de datos.
     */
    public Legajo buscarPorNroLegajo(String nroLegajo) throws SQLException {
        if (nroLegajo == null || nroLegajo.trim().isEmpty()) {
            return null;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_NRO_LEGAJO_SQL)) {

            stmt.setString(1, nroLegajo.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToLegajo(rs);
                }
            }
        }
        return null;
    }

    /**
     * Busca Legajos por su estado (ACTIVO/INACTIVO).
     *
     * @param estado El estado a buscar.
     * @return Una lista de Legajos que coinciden (puede estar vacía).
     * @throws SQLException Si hay un error de base de datos.
     */
    public List<Legajo> buscarPorEstado(EstadoLegajo estado) throws SQLException {
        List<Legajo> legajos = new ArrayList<>();
        if (estado == null) {
            return legajos;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ESTADO_SQL)) {

            stmt.setString(1, estado.name());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    legajos.add(mapRowToLegajo(rs));
                }
            }
        }
        return legajos;
    }

    // --- MÉTODOS AUXILIARES (Helpers) ---

    /**
     * Setea los parámetros de inserción de un Legajo en el PreparedStatement.
     * Incluye el empleadoId (FK).
     */
    private void setInsertParameters(PreparedStatement stmt, Legajo legajo, long empleadoId) throws SQLException {
        stmt.setString(1, legajo.getNumeroLegajo());
        stmt.setString(2, legajo.getCategoria());

        if (legajo.getEstado() != null) {
            stmt.setString(3, legajo.getEstado().name()); // "ACTIVO" / "INACTIVO"
        } else {
            stmt.setNull(3, Types.VARCHAR);
        }

        if (legajo.getFechaAlta() != null) {
            stmt.setDate(4, java.sql.Date.valueOf(legajo.getFechaAlta()));
        } else {
            stmt.setNull(4, Types.DATE);
        }

        stmt.setString(5, legajo.getObservaciones());
        stmt.setLong(6, empleadoId);
    }

    /**
     * Extrae el ID autogenerado del PreparedStatement y lo asigna al Legajo.
     */
    private void assignGeneratedId(PreparedStatement stmt, Legajo legajo) throws SQLException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                legajo.setId(rs.getLong(1));
            }
        }
    }

    /**
     * Ejecuta la lógica de actualización. NO cierra la conexión.
     */
    private void executeUpdate(Legajo legajo, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
            stmt.setString(1, legajo.getNumeroLegajo());
            stmt.setString(2, legajo.getCategoria());

            if (legajo.getEstado() != null) {
                stmt.setString(3, legajo.getEstado().name());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }

            if (legajo.getFechaAlta() != null) {
                stmt.setDate(4, java.sql.Date.valueOf(legajo.getFechaAlta()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setString(5, legajo.getObservaciones());
            stmt.setLong(6, legajo.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar el Legajo. ID no encontrado: " + legajo.getId());
            }
        }
    }

    /**
     * Ejecuta la baja lógica (soft delete). NO cierra la conexión.
     */
    private void executeSoftDelete(long id, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            stmt.setLong(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo eliminar (baja lógica) el Legajo. ID no encontrado: " + id);
            }
        }
    }

    /**
     * Mapea una fila de ResultSet a un objeto Legajo.
     */
    private Legajo mapRowToLegajo(ResultSet rs) throws SQLException {
        Legajo legajo = new Legajo();

        legajo.setId(rs.getLong("id"));
        legajo.setNumeroLegajo(rs.getString("nro_legajo"));
        legajo.setCategoria(rs.getString("categoria"));

        String estadoStr = rs.getString("estado");
        if (estadoStr != null) {
            legajo.setEstado(EstadoLegajo.valueOf(estadoStr));
        }

        java.sql.Date fechaAlta = rs.getDate("fecha_alta");
        if (fechaAlta != null) {
            legajo.setFechaAlta(fechaAlta.toLocalDate());
        }

        legajo.setObservaciones(rs.getString("observaciones"));

        // No mapeamos empleado_id para mantener la unidireccionalidad desde Empleado.
        return legajo;
    }

    /**
     * Helper para leer un Legajo por id usando una conexión existente.
     */
    private Legajo findByIdInternal(Connection conn, long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToLegajo(rs);
                }
            }
        }
        return null;
    }
}