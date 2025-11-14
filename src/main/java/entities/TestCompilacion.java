package entities;

import java.time.LocalDate;

public class TestCompilacion {
    public static void main(String[] args) {
        System.out.println("testing...");
        
        // testing localdate en empleado
        Empleado emp = new Empleado();
        emp.setNombre("Agu");
        emp.setApellido("Sote");
        emp.setDni("12345678");
        emp.setFechaIngreso(LocalDate.now());
        
        // probando localdate en legajo
        Legajo leg = new Legajo();
        leg.setNumeroLegajo("LEG-001");
        leg.setCategoria("Junior");
        leg.setEstado(EstadoLegajo.ACTIVO);
        leg.setFechaAlta(LocalDate.now());
        
        System.out.println("EMPLEADO: " + emp.getNombre() + " " + emp.getApellido());
        System.out.println("FECHA INGRESO: " + emp.getFechaIngreso());
        System.out.println("LEGAJO: " + leg.getNumeroLegajo());
        System.out.println("FECHA ALTA: " + leg.getFechaAlta());
        System.out.println("Todo compila corretamente");
    }
}
