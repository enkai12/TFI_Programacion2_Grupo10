package main;

import entities.Empleado;
import entities.EstadoLegajo;
import entities.Legajo;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import service.EmpleadoServiceImpl;

/**
 * Controlador de las operaciones del menú (Menu Handler).
 * Gestiona toda la lógica de interacción con el usuario para operaciones CRUD.
 *
 * Responsabilidades:
 * - Capturar entrada del usuario desde consola (Scanner)
 * - Validar entrada básica (conversión de tipos, valores vacíos)
 * - Invocar servicios de negocio (EmpleadoService, LegajoService)
 * - Mostrar resultados y mensajes de error al usuario
 * - Coordinar operaciones complejas (crear empleado con legajo, etc.)
 */
public class MenuHandler {

    /**
     * Scanner compartido para leer entrada del usuario.
     * Inyectado desde AppMenu para evitar múltiples Scanners de System.in.
     */
    private final Scanner scanner;

    /**
     * Servicio de empleados para operaciones CRUD.
     */
    private final EmpleadoServiceImpl empleadoService;

    private static final String EMPLOYEE_LIST_HEADER = "Listando Empleados:";
    private static final String EMPLOYEE_SEPARATOR = "-----------------";
    private static final String LEGAJO_LIST_HEADER = "Listando Legajos: ";
    /**
     * Constructor con inyección de dependencias.
     * Valida que las dependencias no sean null (fail-fast).
     *
     * @param scanner         Scanner compartido para entrada de usuario
     * @param empleadoService Servicio de empleados
     * @throws IllegalArgumentException si alguna dependencia es null
     */
    public MenuHandler(Scanner scanner, EmpleadoServiceImpl empleadoService) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner no puede ser null");
        }
        if (empleadoService == null) {
            throw new IllegalArgumentException("EmpleadoService no puede ser null");
        }
        this.scanner = scanner;
        this.empleadoService = empleadoService;
    }

    /**
     * Opción de menú: crear empleado.
     * (Por ahora sin implementar).
     */
    public void crearEmpleado() {
        try { 
        System.out.println("== Crear empleado ==");
         
         System.out.print("Nombre: ");
         String nombre = scanner.nextLine().trim();
         System.out.print("Apellido: ");
         String apellido = scanner.nextLine().trim();
         System.out.print("DNI: ");
         String dni = scanner.nextLine().trim();
         System.out.print("Email: ");
         String email = scanner.nextLine().trim();
         System.out.print("Area: ");
         String area = scanner.nextLine().trim();
         System.out.print("Ingrese fecha de creación (AAAA-MM-DD): ");
         LocalDate fechaIngreso = LocalDate.parse(scanner.nextLine());
         
                  
         System.out.println("Datos de Legajo (se requiere crear legajo junto al empleado)");
         
         System.out.print("Numero de Legajo: ");
         String numeroLegajo = scanner.nextLine().trim();
         
         EstadoLegajo estado = leerEstadoEmpleado();
         
         System.out.print("Categoria: ");
         String categoria = scanner.nextLine().trim();
         System.out.print("Observaciones: ");
         String observaciones = scanner.nextLine().trim();
         System.out.print("Ingrese fecha de creación (AAAA-MM-DD): ");
         LocalDate fechaAlta = LocalDate.parse(scanner.nextLine());
         
         Legajo legajoNuevo = new Legajo(numeroLegajo, categoria,  estado,
                  fechaAlta, observaciones);
         Empleado empleadoNuevo = new Empleado(nombre, apellido, dni, email,fechaIngreso, area, legajoNuevo);
         
         empleadoService.insertar(empleadoNuevo);
         
          System.out.print("El empleado: " + empleadoNuevo.getNombre() + " "+ empleadoNuevo.getApellido() + " se ha creado con éxito");
         
        }
          catch (Exception e) {
            System.err.println("Error al crear Empleado: " + e.getMessage());
        }
         
    }

    
    private EstadoLegajo leerEstadoEmpleado() {
    while (true) {
        System.out.println("Seleccione el estado del empleado:");
        System.out.println("1 - ACTIVO");
        System.out.println("2 - INACTIVO");
        System.out.print("Opción: ");

        String opcion = scanner.nextLine();

        switch (opcion) {
            case "1":
                return EstadoLegajo.ACTIVO;
            case "2":
                return EstadoLegajo.INACTIVO;
            default:
                System.out.println("Opción inválida. Intente nuevamente.\n");
            }
        }
    }   
    
    /**
     * Muestra los empleados activos.
     */
    public void listarEmpleados() {
        System.out.println(EMPLOYEE_LIST_HEADER);
        try {
            List<Empleado> empleados = empleadoService.getAll();
            if (empleados.isEmpty()) {
                System.out.println("No hay empleados.");
                return;
            }
            printEmpleados(empleados);
        } catch (Exception e) {
            System.err.println("\nERROR al listar empleados: " + e.getMessage());
        }
    }

    private void printEmpleados(List<Empleado> empleados) {
        for (Empleado empleado : empleados) {
            System.out.println(empleado);
            System.out.println(EMPLOYEE_SEPARATOR);
        }
    }

    /**
     * Opción de menú: actualizar empleado.
     * (Por ahora sin implementar).
     */
    public void actualizarEmpleado() {
         try {
        System.out.println("=== Actualizar empleado ===");
        System.out.println("1 - Buscar por ID");
        System.out.println("2 - Buscar por DNI");
        System.out.print("Opción: ");

        String opcion = scanner.nextLine();
        Empleado e = null;
        

        switch (opcion) {
            case "1":
                System.out.print("Ingrese ID del empleado: ");
                Long id = Long.parseLong(scanner.nextLine());
                e = empleadoService.getById(id);
                break;

            case "2":
                System.out.print("Ingrese DNI del empleado: ");
                String dni = scanner.nextLine();
                e = empleadoService.getByDni(dni);
                break;

            default:
                System.out.println("Opción inválida.");
                return;
        }           
                      
            
            if (e == null) {
                System.out.println("Empleado no encontrada.");
                return;
            }

            System.out.print("Nuevo nombre (actual: " + e.getNombre() + ", Enter para mantener): ");
            String nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) {
                e.setNombre(nombre);
            }

            System.out.print("Nuevo apellido (actual: " + e.getApellido() + ", Enter para mantener): ");
            String apellido = scanner.nextLine().trim();
            if (!apellido.isEmpty()) {
                e.setApellido(apellido);
            }

            System.out.print("Nuevo DNI (actual: " + e.getDni() + ", Enter para mantener): ");
            String dni = scanner.nextLine().trim();
            if (!dni.isEmpty()) {
                e.setDni(dni);
            }
            
            System.out.print("Nuevo Email (actual: " + e.getEmail() + ", Enter para mantener): ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) {
                e.setEmail(email);
            }
            
            System.out.print("Nuevo Area (actual: " + e.getArea() + ", Enter para mantener): ");
            String area = scanner.nextLine().trim();
            if (!area.isEmpty()) {
                e.setDni(area);
            }
            
            System.out.print("Desea Tambien Actualizar su Legajo? :  1. = SI / 2 = NO ");
            String opc = scanner.nextLine();
            
            if (opc == "1") {                
                    Legajo l = e.getLegajo();

                    System.out.print("Nuevo Numero de Legajo (actual: " + l.getNumeroLegajo() + ", Enter para mantener): ");
                    String numeroLegajoNuevo = scanner.nextLine().trim();
                    if (!numeroLegajoNuevo.isEmpty()) {
                        l.setNumeroLegajo(numeroLegajoNuevo);
                    }

                    System.out.print("Nueva Categoria (actual: " + l.getCategoria() + ", Enter para mantener): ");
                    String categoriaNueva = scanner.nextLine().trim();
                    if (!categoriaNueva.isEmpty()) {
                        l.setCategoria(categoriaNueva);
                    }

                    System.out.print("Nuevas Observaciones (actual: " + l.getObservaciones() + ", Enter para mantener): ");
                    String observacionesNueva = scanner.nextLine().trim();
                    if (!observacionesNueva.isEmpty()) {
                        l.setObservaciones(observacionesNueva);
                    }

                    EstadoLegajo nuevoEstado = leerEstadoEmpleado();
                    if (nuevoEstado != l.getEstado()) {
                        l.setEstado(nuevoEstado);
                    }
                    e.setLegajo(l);
            }
            
            empleadoService.actualizar(e);
            System.out.println("Empleado actualizada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al actualizar persona: " + e.getMessage());
        }
    }

    /**
     * Opción de menú: eliminar empleado.
     * (Por ahora sin implementar).
     */
    public void eliminarEmpleado() {
          try {
            System.out.println("=== Eliminar empleado ===");
        System.out.println("1 - Buscar por ID");
        System.out.println("2 - Buscar por DNI");
        System.out.print("Opción: ");

        String opcion = scanner.nextLine();
        Empleado e = null;

        switch (opcion) {
            case "1":
                System.out.print("Ingrese ID del empleado: ");
                Long id = Long.parseLong(scanner.nextLine());
                e = empleadoService.getById(id);
                break;

            case "2":
                System.out.print("Ingrese DNI del empleado: ");
                String dni = scanner.nextLine();
                e = empleadoService.getByDni(dni);
                break;

            default:
                System.out.println("Opción inválida.");
                return;
            }           
        
        if (e == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }
        System.out.println("Empleado encontrado:");
        System.out.println(e);
        
        System.out.print("Confirmar eliminación del empleado y su legajo? (s/N): ");
        String conf = scanner.nextLine().trim();
        if (!conf.equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada.");
            return;
        }
        
        empleadoService.eliminar(e.getId());
                    
        
          }catch (Exception e) {
            System.err.println("Error al eliminar persona: " + e.getMessage());
        }
        
    }

    /**
     * Opción de menú: buscar empleado por ID (o DNI según se defina luego).
     * (Por ahora sin implementar).
     */
    public void buscarEmpleadoID() {
        try {
            System.out.println("== Buscar empleado por ID ==");
            System.out.print("ID del empleado buscado: ");
            Long id = Long.parseLong(scanner.nextLine());
            Empleado empleado = empleadoService.getById(id);
            if (empleado == null) {
                System.out.println("Empleado no encontrado con ID: " + id);
                return;
            }
            System.out.println("Empleado encontrado:");
            System.out.println(empleado);
        } catch (Exception e) {
            System.err.println("ERROR al buscar empleado: " + e.getMessage());
        }
    }

    /**
     * Opción de menú: crear legajo.
     * (Por ahora sin implementar).
     */
    public void crearLegajo() {
        throw new UnsupportedOperationException("Crear legajo aún no está implementado.");
    }

    /**
     * Opción de menú: listar legajos.
     * (Por ahora sin implementar).
     */
    
    
    
     public void listarLegajos() {
        System.out.println(LEGAJO_LIST_HEADER);
        try {
            List<Legajo> legajo = empleadoService.getLegajoService().getAll();
            if (legajo.isEmpty()) {
                System.out.println("No hay empleados.");
                return;
            }
            printLegajos(legajo);
        } catch (Exception e) {
            System.err.println("\nERROR al listar empleados: " + e.getMessage());
        }
    }
     
     public void printLegajos(List<Legajo> legajos) {
        for (Legajo legajo : legajos) {
            System.out.println(legajo);
            System.out.println(EMPLOYEE_SEPARATOR);
        }
    }
    /**
     * Opción de menú: actualizar legajo.
     * (Por ahora sin implementar).
     */
    public void actualizarLegajo() {
        try {
            System.out.println("=== Actualizar Legajo ===");
        
            System.out.println("Buscar empleado a actualizar por ID : Coloque su ID");
            Long id = Long.parseLong(scanner.nextLine());
        
            Legajo l = empleadoService.getLegajoService().getById(id);
            
            if (l == null) {
                System.out.println("Legajo no encontrado.");
                return;
            }

            System.out.print("Nuevo Numero de Legajo (actual: " + l.getNumeroLegajo() + ", Enter para mantener): ");
            String numeroLegajoNuevo = scanner.nextLine().trim();
            if (!numeroLegajoNuevo.isEmpty()) {
                l.setNumeroLegajo(numeroLegajoNuevo);
            }
            
            System.out.print("Nueva Categoria (actual: " + l.getCategoria() + ", Enter para mantener): ");
            String categoriaNueva = scanner.nextLine().trim();
            if (!categoriaNueva.isEmpty()) {
                l.setCategoria(categoriaNueva);
            }
            
            System.out.print("Nuevas Observaciones (actual: " + l.getObservaciones() + ", Enter para mantener): ");
            String observacionesNueva = scanner.nextLine().trim();
            if (!observacionesNueva.isEmpty()) {
                l.setObservaciones(observacionesNueva);
            }
            
            EstadoLegajo nuevoEstado = leerEstadoEmpleado();
            if (nuevoEstado != l.getEstado()) {
                l.setEstado(nuevoEstado);;
            }
            
            empleadoService.getLegajoService().actualizar(l);
            System.out.println("Legajo actualizado exitosamente.");
            
        }
        catch (Exception e) {
            System.err.println("\nERROR al listar empleados: " + e.getMessage());
        }
        
        
    }

    /**
     * Opción de menú: eliminar legajo.
     * (Por ahora sin implementar).
     */
    public void eliminarLegajo() {
        throw new UnsupportedOperationException("Eliminar legajo aún no está implementado.");
    }

    /**
     * Opción de menú: listar legajos por estado.
     * (Por ahora sin implementar).
     */
    public void listarLegajoPorEstado() {
        try {
        System.out.println("1 - Activos");
        System.out.println("2 - Inactivos");
        int opc = Integer.parseInt(scanner.nextLine());

        EstadoLegajo estado = (opc == 1) ? EstadoLegajo.ACTIVO : EstadoLegajo.INACTIVO;
        
        List<Legajo> datos = empleadoService.getLegajoService().getByEstado(estado);
            
            
        for (Legajo l : datos) {
           System.out.println(l);
            }
        
        }catch (Exception e) {
            System.err.println("\nERROR al listar empleados: " + e.getMessage());
        }
    }
}