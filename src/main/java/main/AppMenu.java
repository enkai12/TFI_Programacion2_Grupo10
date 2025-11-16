package main;

import java.util.Scanner;
import dao.EmpleadoDAO;
import dao.LegajoDAO;
import service.EmpleadoServiceImpl;
import service.LegajoServiceImpl;
import service.ServiceException; // Importamos la clase de excepción

/**
 * Orquestador principal de la aplicación.
 * Controla el bucle del menú, la inicialización de dependencias
 * y el manejo centralizado de errores.
 */
public class AppMenu {

    // --- Constantes ---
    private static final int EXIT_OPTION = 0;
    private static final String INVALID_INPUT_MESSAGE = "Entrada inválida. Por favor, ingrese un número.";
    private static final String INVALID_OPTION_MESSAGE = "Opción no válida.";
    private static final String EXIT_MESSAGE = "Saliendo...";
    private static final String BUSINESS_RULE_ERROR_PREFIX = "\n[ERROR DE REGLA DE NEGOCIO]: ";
    private static final String DATA_ERROR_PREFIX = "\n[ERROR DE DATOS]: ";
    private static final String UNEXPECTED_ERROR_PREFIX = "\n[ERROR INESPERADO]: ";
    private static final String PRESS_ENTER_TO_CONTINUE_MESSAGE = "\nPresione Enter para continuar...";

    // --- Dependencias ---
    private final Scanner scanner;
    private final MenuHandler menuHandler;

    /**
     * Constructor de AppMenu.
     * Aquí se crean e inyectan todas las dependencias de la aplicación.
     */
    public AppMenu() {
        this.scanner = new Scanner(System.in);
        this.menuHandler = initializeMenuHandler();
    }

    /**
     * Inicia el bucle principal de la aplicación.
     */
    public void run() {
        boolean keepRunning = true;
        while (keepRunning) {
            Integer option = readMenuOption();
            if (option != null) {
                keepRunning = handleMenuOption(option);
            }
        }
        scanner.close();
    }

    /**
     * Muestra el menú principal y lee la opción del usuario.
     */
    private Integer readMenuOption() {
        MenuDisplay.mostrarMenuPrincipal(); // Llama al menú corregido
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(INVALID_INPUT_MESSAGE);
            return null; // Devuelve null para que el bucle principal ignore esta entrada
        }
    }

    /**
     * Procesa la opción del usuario y delega al MenuHandler.
     */
    private boolean handleMenuOption(int option) {
        if (option == EXIT_OPTION) {
            System.out.println(EXIT_MESSAGE);
            return false; // Termina el bucle
        }

        // Interfaz funcional para ejecutar la acción correspondiente
        MenuAction action = switch (option) {
            // Opciones de Empleado
            case 1 -> () -> menuHandler.crearEmpleado();
            case 2 -> () -> menuHandler.listarEmpleados();
            case 3 -> () -> menuHandler.actualizarEmpleado();
            case 4 -> () -> menuHandler.eliminarEmpleado();
            case 5 -> () -> menuHandler.buscarEmpleadoID();
            case 6 -> () -> menuHandler.buscarEmpleadoPorDNI();

            // Opciones de Legajo (Re-numeradas)
            case 7 -> () -> menuHandler.crearLegajo();
            case 8 -> () -> menuHandler.listarLegajos();
            case 9 -> () -> menuHandler.actualizarLegajo();
            case 10 -> () -> menuHandler.eliminarLegajo();
            case 11 -> () -> menuHandler.listarLegajoPorEstado();

            default -> null; // Opción no válida
        };

        if (action == null) {
            System.out.println(INVALID_OPTION_MESSAGE);
            return true; // Continúa el bucle
        }

        executeMenuAction(action);
        return true; // Continúa el bucle
    }

    /**
     * Ejecuta una acción de menú y maneja de forma centralizada
     * todos los tipos de errores y la pausa para el usuario.
     */
    private void executeMenuAction(MenuAction action) {
        try {
            action.run(); // Ejecuta la acción (ej. crearEmpleado)

            // --- Manejo de Errores Específicos ---

        } catch (ServiceException e) {
            // Errores controlados de la capa de servicio (ej. "No se encontró el ID")
            System.err.println(DATA_ERROR_PREFIX + e.getMessage());

        } catch (UnsupportedOperationException e) {
            // Errores de funciones no implementadas (ej. "Opción 3 no implementada")
            System.err.println(BUSINESS_RULE_ERROR_PREFIX + e.getMessage());

        } catch (IllegalArgumentException e) {
            // Errores de validación de datos (ej. "El ID debe ser un número")
            System.err.println(DATA_ERROR_PREFIX + e.getMessage());

        } catch (Exception e) {
            // Captura genérica para cualquier otro error (ej. base de datos desconectada)
            System.err.println(UNEXPECTED_ERROR_PREFIX + e.getMessage());
            e.printStackTrace(); // Mostramos la traza completa para errores inesperados
        }

        // Pausa para que el usuario pueda leer el mensaje antes de volver al menú
        System.out.println(PRESS_ENTER_TO_CONTINUE_MESSAGE);
        scanner.nextLine();
    }

    /**
     * Inicializa la cadena de dependencias (DAOs, Services, MenuHandler).
     * Este es el núcleo de la Inyección de Dependencias.
     */
    private MenuHandler initializeMenuHandler() {
        // 1. DAOs (capa de datos)
        LegajoDAO legajoDAO = new LegajoDAO();
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();

        // 2. Servicios (capa de negocio)
        LegajoServiceImpl legajoService = new LegajoServiceImpl(legajoDAO);
        EmpleadoServiceImpl empleadoService = new EmpleadoServiceImpl(empleadoDAO, legajoService);

        // 3. MenuHandler (capa de presentación/controlador)
        return new MenuHandler(this.scanner, empleadoService, legajoService);
    }

    /**
     * Interfaz funcional para representar cualquier acción del menú
     * que pueda lanzar una excepción.
     */
    @FunctionalInterface
    private interface MenuAction {
        // Se usa 'throws Exception' para capturar cualquier tipo de error
        // en el bloque 'executeMenuAction'
        void run() throws Exception;
    }
}