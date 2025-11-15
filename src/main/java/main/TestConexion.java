package main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import config.DatabaseConnection;

public class TestConexion {

    private static final String MSG_CONNECTION_SUCCESS = "Conexion exitosa a la base de datos";
    private static final String MSG_CONNECTION_FAILURE = "No se pudo establecer la conexion.";
    private static final String MSG_ERROR_PREFIX = "Error al conectar a la base de datos: ";
    private static final String LABEL_USER = "Usuario conectado: ";
    private static final String LABEL_DATABASE = "Base de datos: ";
    private static final String LABEL_URL = "URL: ";
    private static final String LABEL_DRIVER = "Driver: ";

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println(MSG_CONNECTION_SUCCESS);
                printConnectionInfo(connection);
            } else {
                System.out.println(MSG_CONNECTION_FAILURE);
            }
        } catch (SQLException e) {
            System.err.println(MSG_ERROR_PREFIX + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printConnectionInfo(Connection connection) throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        System.out.println(LABEL_USER + databaseMetaData.getUserName());
        System.out.println(LABEL_DATABASE + connection.getCatalog());
        System.out.println(LABEL_URL + databaseMetaData.getURL());
        System.out.println(
                LABEL_DRIVER + databaseMetaData.getDriverName() +
                        " v" + databaseMetaData.getDriverVersion()
        );
    }
}