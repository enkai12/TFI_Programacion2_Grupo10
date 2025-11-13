package service;

import java.util.List;

/**
 * Interfaz Genérica de Servicio.
 * Define el contrato estándar para las operaciones de lógica de negocio.
 * Es la capa que orquesta los DAOs y aplica las reglas de negocio.
 * (Nombres de los métodos basados en el requisito 5 del TFI)
 *
 * @param <T> El tipo de la entidad, dependiendo del servicio que extienda esta interfaz (Empleado, Legajo).
 */
public interface GenericService<T> {

    /**
     * Inserta una entidad tras validar las reglas de negocio.
     * @param entidad La entidad a crear.
     * @throws Exception Si la validación falla o hay un error de BD.
     */
    void insertar(T entidad) throws Exception;

    /**
     * Actualiza una entidad tras validar las reglas de negocio.
     * @param entidad La entidad con datos actualizados.
     * @throws Exception Si la validación falla o hay un error de BD.
     */
    void actualizar(T entidad) throws Exception;

    /**
     * Realiza la baja lógica de una entidad.
     * Si es una entidad 'padre', debe manejar la baja en cascada (Composición).
     * @param id El ID de la entidad a eliminar lógicamente.
     * @throws Exception Si hay un error de BD.
     */
    void eliminar(long id) throws Exception;

    /**
     * Obtiene una entidad por su ID.
     * @param id El ID a buscar.
     * @return La entidad encontrada, o null.
     * @throws Exception Si hay un error de BD.
     */
    T getById(long id) throws Exception;

    /**
     * Obtiene todas las entidades activas (no eliminadas).
     * @return Lista de entidades.
     * @throws Exception Si hay un error de BD.
     */
    List<T> getAll() throws Exception;
}