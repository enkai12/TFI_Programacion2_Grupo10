package service;

import dao.GenericDAO;
import entities.Legajo;
import java.util.List;

/**
 * Implementación del servicio de negocio para la entidad Legajo.
 * Capa intermedia entre la UI y el DAO que aplica validaciones de negocio.
 *
 * Responsabilidades:
 * - Validar que los datos del legajo sean correctos ANTES de persistir
 * - Aplicar reglas de negocio
 * - Delegar operaciones de BD al DAO
 * - Transformar excepciones técnicas en errores de negocio comprensibles
 *
 * Patrón: Service Layer con inyección de dependencias
 */
public class LegajoServiceImpl implements GenericService<Legajo> {
    /**
     * DAO para acceso a datos de domicilios.
     * Inyectado en el constructor (Dependency Injection).
     * Usa GenericDAO para permitir testing con mocks.
     */
    private final GenericDAO<Legajo> legajoDAO;

    /**
     *  Constructor con inyección de dependencias
     * Valida que el DAO no sea null
     * @param legajoDAO 
     */
    public LegajoServiceImpl(GenericDAO legajoDAO) {
        if (legajoDAO == null) {
            throw new IllegalArgumentException("LegajoDAO no puede ser null");
        }
        this.legajoDAO = legajoDAO;
    }

    // Métodos
    @Override
    public void insertar(Legajo legajo) throws Exception {
        validateLegajo(legajo); 
        legajoDAO.crear(legajo);
    }

    //  Método que valida las reglas de negocio para crear un nuevo legajo
    private void validateLegajo(Legajo legajo) {
        
        // Validación de Nulidad (Objeto)
        if (legajo == null) {
            throw new IllegalArgumentException("El legajo no puede ser null");
        }

        // VALIDACIONES DE CAMPOS "NOT NULL"

        if (legajo.getNumeroLegajo() == null || legajo.getNumeroLegajo().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de legajo no puede estar vacío");
        }

        if (legajo.getEstado() == null) {
            throw new IllegalArgumentException("El Estado del Legajo no puede ser null");
        }


        // VALIDACIONES DE LONGITUD (varchar)

        if (legajo.getNumeroLegajo().trim().length() > 20) {
            throw new IllegalArgumentException("El número de legajo no puede exceder los 20 caracteres");
        }

        if (legajo.getCategoria() != null && legajo.getCategoria().length() > 30) {
            throw new IllegalArgumentException("La categoría no puede exceder los 30 caracteres");
        }

        if (legajo.getObservaciones() != null && legajo.getObservaciones().length() > 255) {
            throw new IllegalArgumentException("Las observaciones no pueden exceder los 255 caracteres");
        }

        // --- 4. VALIDACIÓN DE UNICIDAD (Regla de Negocio) ---

        
    }
    
    @Override
    public void actualizar(Legajo entidad) throws Exception {
       
    }

    @Override
    public void eliminar(long id) throws Exception {
       
    }
    
    /**
     * Llama al médoto leer en LegajoDAO
     * @param id
     * @return La fila a un objeto Legao o Null si no se encontró
     * @throws Exception 
     */
    @Override
    public Legajo getById(long id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        return legajoDAO.leer(id);
    }
      
    
    /**
     * Llama al método leerTodos en LegajoDAO
     * @return lista de legajos
     * @throws Exception 
     */
    @Override
    public List<Legajo> getAll() throws Exception {
        return legajoDAO.leerTodos();
    }
    
}