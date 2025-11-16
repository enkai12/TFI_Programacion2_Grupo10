package main;

/**
 * Clase utilitaria para mostrar el menú de la aplicación.
 * Solo contiene métodos estáticos de visualización (no tiene estado).
 */
public final class MenuDisplay {

    private static final String MENU_HEADER = "\n========= MENU PRINCIPAL =========";
    private static final String SEPARATOR_EMPLEADO = "--- Gestión de Empleados ---";
    private static final String SEPARATOR_LEGAJO = "---- Gestión de Legajos ----";
    private static final String SEPARATOR_SALIR = "------------------------------";

    // Opciones de Empleado
    private static final String OPTION_1_CREAR_EMPLEADO = "1. Crear Empleado (y Legajo)";
    private static final String OPTION_2_LISTAR_EMPLEADOS = "2. Listar Empleados";
    private static final String OPTION_3_ACTUALIZAR_EMPLEADO = "3. Actualizar Empleado";
    private static final String OPTION_4_ELIMINAR_EMPLEADO = "4. Eliminar Empleado";
    private static final String OPTION_5_BUSCAR_EMPLEADO_ID = "5. Buscar Empleado por ID";
    private static final String OPTION_6_BUSCAR_EMPLEADO_DNI = "6. Buscar Empleado por DNI";

    // Opciones de Legajo (Re-numeradas)
    private static final String OPTION_7_LISTAR_LEGAJOS = "7. Listar Legajos";
    private static final String OPTION_8_LISTAR_LEGAJOS_POR_ESTADO = "8. Listar Legajos por Estado";

    private static final String OPTION_0_SALIR = "0. Salir";
    private static final String PROMPT_MESSAGE = "Ingrese una opcion: ";

    /**
     * Constructor privado para evitar la instanciación.
     */
    private MenuDisplay() {
        // Evita la instanciación
    }

    /**
     * Muestra el menú principal escribiendo en {@link System#out}.
     */
    public static void mostrarMenuPrincipal() {
        mostrarMenuPrincipal(System.out);
    }

    /**
     * Muestra el menú principal usando el {@link java.io.PrintStream} indicado.
     *
     * @param out flujo de salida donde se imprimirá el menú
     */
    public static void mostrarMenuPrincipal(java.io.PrintStream out) {
        printMenuHeader(out);
        printEmpleadoOptions(out);
        printLegajoOptions(out);
        printExitOption(out);
        printPrompt(out);
    }

    private static void printMenuHeader(java.io.PrintStream out) {
        out.println(MENU_HEADER);
    }

    private static void printEmpleadoOptions(java.io.PrintStream out) {
        out.println(SEPARATOR_EMPLEADO);
        out.println(OPTION_1_CREAR_EMPLEADO);
        out.println(OPTION_2_LISTAR_EMPLEADOS);
        out.println(OPTION_3_ACTUALIZAR_EMPLEADO);
        out.println(OPTION_4_ELIMINAR_EMPLEADO);
        out.println(OPTION_5_BUSCAR_EMPLEADO_ID);
        out.println(OPTION_6_BUSCAR_EMPLEADO_DNI);
    }

    private static void printLegajoOptions(java.io.PrintStream out) {
        out.println(SEPARATOR_LEGAJO);
        out.println(OPTION_7_LISTAR_LEGAJOS);
        out.println(OPTION_8_LISTAR_LEGAJOS_POR_ESTADO);
    }

    private static void printExitOption(java.io.PrintStream out) {
        out.println(SEPARATOR_SALIR);
        out.println(OPTION_0_SALIR);
    }

    private static void printPrompt(java.io.PrintStream out) {
        out.print(PROMPT_MESSAGE);
    }
}