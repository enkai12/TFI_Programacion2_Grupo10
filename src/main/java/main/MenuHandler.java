package main;

import entities.Empleado;
import entities.EstadoLegajo;
import entities.Legajo;
import service.EmpleadoServiceImpl;
import service.LegajoServiceImpl;
import service.ServiceException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Controlador de las operaciones del menú (Menu Handler).
 * Gestiona toda la lógica de interacción con el usuario.
 */
public class MenuHandler {

    private final Scanner scanner;
    private final EmpleadoServiceImpl empleadoService;
    private final LegajoServiceImpl legajoService;

    // Constantes para logging / mensajes
    private static final String EMPLOYEE_LIST_HEADER = "--- Listando Empleados ---";
    private static final String LEGAJO_LIST_HEADER = "--- Listando Legajos ---";
    private static final String SEPARATOR = "------------------------------";
    private static final String NO_EMPLOYEES_FOUND = "No hay empleados para mostrar.";
    private static final String NO_LEGAJOS_FOUND = "No hay legajos para mostrar.";
    private static final String INVALID_DATE_MESSAGE = "Formato de fecha inválido. Use AAAA-MM-DD.";

    // Títulos / prompts reutilizables
    private static final String TITLE_CREAR_EMPLEADO_LEGAJO = "== Crear Empleado y Legajo ==";
    private static final String TITLE_BUSCAR_EMPLEADO_ID = "== Buscar empleado por ID ==";
    private static final String TITLE_BUSCAR_EMPLEADO_DNI = "== Buscar empleado por DNI ==";
    private static final String PROMPT_ID_EMPLEADO = "ID del empleado a buscar: ";
    private static final String PROMPT_DNI_EMPLEADO = "DNI del empleado a buscar: ";
    private static final String INVALID_OPTION_STATE_MESSAGE = "Opción inválida. Intente nuevamente.\n";

    /**
     * Constructor con inyección de dependencias (DI).
     */
    public MenuHandler(Scanner scanner,
            EmpleadoServiceImpl empleadoService,
            LegajoServiceImpl legajoService) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner no puede ser null");
        }
        if (empleadoService == null) {
            throw new IllegalArgumentException("EmpleadoService no puede ser null");
        }
        if (legajoService == null) {
            throw new IllegalArgumentException("LegajoService no puede ser null");
        }
        this.scanner = scanner;
        this.empleadoService = empleadoService;
        this.legajoService = legajoService;
    }

    // --- MÉTODOS DE EMPLEADO ---

    public void crearEmpleado() throws Exception {
        System.out.println(TITLE_CREAR_EMPLEADO_LEGAJO);
        Empleado empleadoNuevo = readEmpleadoWithLegajoFromInput();
        empleadoService.insertar(empleadoNuevo); // Llama al servicio
        System.out.println("\n¡Empleado y legajo creados exitosamente!");
    }

    public void listarEmpleados() throws Exception {
        List<Empleado> empleados = empleadoService.getAll();
        printList(EMPLOYEE_LIST_HEADER, NO_EMPLOYEES_FOUND, empleados);
    }

    public void actualizarEmpleado() throws Exception {
        System.out.println("== Actualizar Empleado ==");
        long id = readEmpleadoIdFromInput();

        Empleado empleado = empleadoService.getById(id);
        if (empleado == null) {
            throw new ServiceException("No se encontró ningún empleado con el ID: " + id);
        }
        if (empleado.getLegajo() == null) {
            throw new ServiceException("Error de datos: El empleado no tiene un legajo asociado para actualizar.");
        }

        System.out.println("\nEditando datos del empleado. Deje el campo en blanco para no modificar.");

        // Read updated Empleado fields
        System.out.print("Nombre [" + empleado.getNombre() + "]: ");
        String nombre = scanner.nextLine().trim();
        if (!nombre.isEmpty())
            empleado.setNombre(nombre);

        System.out.print("Apellido [" + empleado.getApellido() + "]: ");
        String apellido = scanner.nextLine().trim();
        if (!apellido.isEmpty())
            empleado.setApellido(apellido);

        System.out.print("DNI [" + empleado.getDni() + "]: ");
        String dni = scanner.nextLine().trim();
        if (!dni.isEmpty()) {
            empleado.setDni(dni);
        }

        System.out.print("Email [" + (empleado.getEmail() != null ? empleado.getEmail() : "") + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            empleado.setEmail(email);
        }

        System.out.print("Área [" + empleado.getArea() + "]: ");
        String area = scanner.nextLine().trim();
        if (!area.isEmpty())
            empleado.setArea(area);

        System.out.print("Fecha de Ingreso (AAAA-MM-DD) [" + empleado.getFechaIngreso() + "]: ");
        String fechaIngresoStr = scanner.nextLine().trim();
        if (!fechaIngresoStr.isEmpty()) {
            try {
                empleado.setFechaIngreso(LocalDate.parse(fechaIngresoStr));
            } catch (DateTimeParseException e) {
                System.out.println(INVALID_DATE_MESSAGE);
                throw e;
            }
        }

        System.out.println("\n--- Editando datos del Legajo ---");
        Legajo legajo = empleado.getLegajo();

        System.out.print("Número de Legajo [" + legajo.getNumeroLegajo() + "]: ");
        String numeroLegajo = scanner.nextLine().trim();
        if (!numeroLegajo.isEmpty())
            legajo.setNumeroLegajo(numeroLegajo);

        System.out.print("Categoría [" + legajo.getCategoria() + "]: ");
        String categoria = scanner.nextLine().trim();
        if (!categoria.isEmpty())
            legajo.setCategoria(categoria);

        System.out.println("Estado del legajo [" + legajo.getEstado() + "]:");
        System.out.println("  1 - ACTIVO");
        System.out.println("  2 - INACTIVO");
        System.out.print("Opción (deje en blanco para no cambiar): ");
        String estadoOpt = scanner.nextLine().trim();
        if (!estadoOpt.isEmpty()) {
            switch (estadoOpt) {
                case "1":
                    legajo.setEstado(EstadoLegajo.ACTIVO);
                    break;
                case "2":
                    legajo.setEstado(EstadoLegajo.INACTIVO);
                    break;
                default:
                    System.out.println("Opción de estado no válida. No se cambiará.");
            }
        }

        System.out.print("Observaciones [" + legajo.getObservaciones() + "]: ");
        String observaciones = scanner.nextLine().trim();
        if (!observaciones.isEmpty())
            legajo.setObservaciones(observaciones);

        System.out.print("Fecha de Alta (AAAA-MM-DD) [" + legajo.getFechaAlta() + "]: ");
        String fechaAltaStr = scanner.nextLine().trim();
        if (!fechaAltaStr.isEmpty()) {
            try {
                legajo.setFechaAlta(LocalDate.parse(fechaAltaStr));
            } catch (DateTimeParseException e) {
                System.out.println(INVALID_DATE_MESSAGE);
                throw e;
            }
        }

        empleadoService.actualizar(empleado);
        System.out.println("\n¡Empleado actualizado exitosamente!");
    }

    public void eliminarEmpleado() throws Exception {
        System.out.println("== Eliminar Empleado ==");
        long id = readEmpleadoIdFromInput(); // re-uses existing helper
        empleadoService.eliminar(id);
        System.out.println("\nEmpleado con ID " + id + " eliminado exitosamente (junto con su legajo).");
    }

    public void buscarEmpleadoID() throws Exception {
        System.out.println(TITLE_BUSCAR_EMPLEADO_ID);
        long id = readEmpleadoIdFromInput();
        Empleado empleado = empleadoService.getById(id);
        if (empleado == null) {
            throw new ServiceException("No se encontró ningún empleado con el ID: " + id);
        }
        System.out.println("Empleado encontrado:");
        System.out.println(empleado);
    }

    public void buscarEmpleadoPorDNI() throws Exception {
        System.out.println(TITLE_BUSCAR_EMPLEADO_DNI);
        System.out.print(PROMPT_DNI_EMPLEADO);
        String dni = scanner.nextLine().trim();
        Empleado empleado = empleadoService.getByDni(dni);
        if (empleado == null) {
            throw new ServiceException("No se encontró ningún empleado con el DNI: " + dni);
        }
        System.out.println("\nEmpleado encontrado:");
        System.out.println(empleado);
    }

    // --- MÉTODOS DE LEGAJO ---

    public void listarLegajos() throws Exception {
        List<Legajo> legajos = legajoService.getAll();
        printList(LEGAJO_LIST_HEADER, NO_LEGAJOS_FOUND, legajos);
    }

    public void listarLegajoPorEstado() throws Exception {
        System.out.println("== Listar Legajos por Estado ==");
        EstadoLegajo estado = leerEstadoLegajo(); // Re-use existing helper
        List<Legajo> legajos = legajoService.getByEstado(estado);
        printList("--- Listando Legajos con estado " + estado + " ---",
                "No se encontraron legajos con el estado " + estado + ".",
                legajos);
    }

    // --- Helpers privados (entrada de datos) ---

    /**
     * Lee desde consola los datos para un Empleado y Legajo.
     * Lanza DateTimeParseException si las fechas son incorrectas.
     */
    private Empleado readEmpleadoWithLegajoFromInput() throws DateTimeParseException {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();

        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Área: ");
        String area = scanner.nextLine().trim();

        System.out.print("Fecha de Ingreso (AAAA-MM-DD): ");
        LocalDate fechaIngreso = LocalDate.parse(scanner.nextLine()); // Puede lanzar DateTimeParseException

        System.out.println("\n--- Datos del Legajo (Requerido) ---");

        System.out.print("Número de Legajo: ");
        String numeroLegajo = scanner.nextLine().trim();

        EstadoLegajo estado = leerEstadoLegajo();

        System.out.print("Categoría: ");
        String categoria = scanner.nextLine().trim();

        System.out.print("Observaciones (opcional): ");
        String observaciones = scanner.nextLine().trim();

        System.out.print("Fecha de Alta (AAAA-MM-DD): ");
        LocalDate fechaAlta = LocalDate.parse(scanner.nextLine()); // Puede lanzar DateTimeParseException

        Legajo legajoNuevo = new Legajo(numeroLegajo, categoria, estado, fechaAlta, observaciones);
        return new Empleado(nombre, apellido, dni, email, fechaIngreso, area, legajoNuevo);
    }

    /**
     * Helper para leer y validar el ID de empleado desde consola.
     * Lanza IllegalArgumentException si no es un número.
     */
    private long readEmpleadoIdFromInput() {
        System.out.print(PROMPT_ID_EMPLEADO);
        String input = scanner.nextLine();
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            // Esto será capturado por el catch (IllegalArgumentException e) en AppMenu
            throw new IllegalArgumentException("El ID debe ser un número válido.");
        }
    }

    /**
     * Helper privado para leer y validar el Estado del Legajo.
     */
    private EstadoLegajo leerEstadoLegajo() {
        while (true) {
            System.out.println("Seleccione el estado del legajo:");
            System.out.println("  1 - ACTIVO");
            System.out.println("  2 - INACTIVO");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    return EstadoLegajo.ACTIVO;
                case "2":
                    return EstadoLegajo.INACTIVO;
                default:
                    System.out.println(INVALID_OPTION_STATE_MESSAGE);
            }
        }
    }

    // --- Helpers privados (presentación) ---

    private void printList(String header, String emptyMessage, List<?> items) {
        System.out.println(header);
        if (items == null || items.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Object item : items) {
            System.out.println(item); // Asume que .toString() está bien formateado
            System.out.println(SEPARATOR);
        }
    }

}
