package main;
import java.util.Scanner;
import dao.EmpleadoDAO;
import dao.LegajoDAO;
import service.EmpleadoServiceImpl;
import service.LegajoServiceImpl;

// ... existing code ...

public class AppMenu {

    private static final int EXIT_OPTION = 0;
    private static final String INVALID_INPUT_MESSAGE = "Entrada invalida. Por favor, ingrese un número.";

    /**
     * Scanner único compartido por toda la aplicación. IMPORTANTE: Solo debe
     * haber UNA instancia de Scanner(System.in). Múltiples instancias causan
     * problemas de buffering de entrada.
     */
    private final Scanner scanner;
    /**
     * Handler que ejecuta las operaciones del menú. Contiene toda la lógica de
     * interacción con el usuario.
     */
    private final MenuHandler menuHandler;
    /**
     * Flag que controla el loop principal del menú. Se setea a false cuando el
     * usuario selecciona "0 - Salir".
     */
    private boolean running;

    // ... existing code ...

    public AppMenu() {
        this.scanner = new Scanner(System.in);
        EmpleadoServiceImpl empleadoService = createEmpleadoService();
        this.menuHandler = new MenuHandler(scanner, empleadoService);
        this.running = true;
    }

    // ... existing code ...

    public void run() {
        while (running) {
            Integer opcion = readMenuOption();
            if (opcion != null) {
                handleMenuOption(opcion);
            }
        }
        scanner.close();
    }

    /**
     * Muestra el menú principal, lee la opción del usuario y la convierte a int.
     * Maneja internamente los errores de formato de número.
     *
     * @return La opción ingresada o null si la entrada fue inválida.
     */
    private Integer readMenuOption() {
        MenuDisplay.mostrarMenuPrincipal();
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(INVALID_INPUT_MESSAGE);
            return null;
        }
    }

    /**
     * Procesa la opción seleccionada por el usuario y delega a MenuHandler.
     *
     * @param opcion Número de opción ingresado por el usuario
     */
    private void handleMenuOption(int opcion) {
        switch (opcion) {
            case 1 ->
                    menuHandler.crearEmpleado();
            case 2 ->
                    menuHandler.listarEmpleados();
            case 3 ->
                    menuHandler.actualizarEmpleado();
            case 4 ->
                    menuHandler.eliminarEmpleado();
            case 5 ->
                    menuHandler.buscarEmpleadoID();
            case 6 ->
                    menuHandler.crearLegajo();
            case 7 ->
                    menuHandler.listarLegajos();
            case 8 ->
                    menuHandler.actualizarLegajo();
            case 9 ->
                    menuHandler.eliminarLegajo();
            case 10 ->
                    menuHandler.listarLegajoPorEstado();
            case EXIT_OPTION -> {
                System.out.println("Saliendo...");
                running = false;
            }
            default ->
                    System.out.println("Opción no valida.");
        }
    }

    /**
     * método que crea la cadena de dependencias
     *
     * Flujo de creación: 1- EmpleadoDAO → ccceso a datos de empleados 2-
     * LegajoDAO → ccceso a datos de legajos 3- LegajoServiceImpl → usa
     * LegajoDAO 4- EmpleadoServiceImpl → usa EmpleadoDAO y LegajoServiceImpl
     *
     * @return EmpleadoServiceImpl completamente inicializado
     */
    private EmpleadoServiceImpl createEmpleadoService() {
        // 1. Creamos los DAOs (Data Access Objects)
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        LegajoDAO legajoDAO = new LegajoDAO();

        // 2. Creamos el servicio de Legajo
        LegajoServiceImpl legajoService = new LegajoServiceImpl(legajoDAO);

        // 3. Creamos y retornamos el servicio de Empleado
        return new EmpleadoServiceImpl(empleadoDAO, legajoService);
    }
}
