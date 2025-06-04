package uap.edu.bo.escuela_tecnica.feature.crud;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repositorio genérico JPA simple
 */
@Repository
@Transactional
public class CrudRepositoryEM {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Guarda o actualiza una entidad
     */
    public <T> T guardar(T entidad) {
        if (entityManager.contains(entidad)) {
            return entityManager.merge(entidad);
        } else {
            entityManager.persist(entidad);
            return entidad;
        }
    }

    /**
     * Busca por ID
     */
    public <T> Optional<T> buscarPorId(Class<T> claseEntidad, Object id) {
        return Optional.ofNullable(entityManager.find(claseEntidad, id));
    }

    /**
     * Lista todas las entidades
     */
    public <T> List<T> listarTodos(Class<T> claseEntidad) {
        String jpql = "SELECT e FROM " + claseEntidad.getSimpleName() + " e";
        TypedQuery<T> query = entityManager.createQuery(jpql, claseEntidad);
        return query.getResultList();
    }

    /**
     * Elimina una entidad
     */
    public <T> void eliminar(T entidad) {
        if (!entityManager.contains(entidad)) {
            entidad = entityManager.merge(entidad);
        }
        entityManager.remove(entidad);
    }

    /**
     * Elimina por ID
     */
    public <T> void eliminarPorId(Class<T> claseEntidad, Object id) {
        T entidad = entityManager.find(claseEntidad, id);
        if (entidad != null) {
            entityManager.remove(entidad);
        }
    }

    /**
     * Cuenta total de registros
     */
    public <T> long contar(Class<T> claseEntidad) {
        String jpql = "SELECT COUNT(e) FROM " + claseEntidad.getSimpleName() + " e";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }

    /**
     * Actualiza un campo específico
     */
    public <T> int actualizarCampo(Class<T> claseEntidad, Object id, String nombreCampo, Object valor) {
        String jpql = "UPDATE " + claseEntidad.getSimpleName() +
                      " e SET e." + nombreCampo + " = :valor WHERE e.id = :id";
        return entityManager.createQuery(jpql)
            .setParameter("valor", valor)
            .setParameter("id", id)
            .executeUpdate();
    }

    /**
     * Ejecuta una consulta JPQL personalizada
     */
    public <T> List<T> ejecutarConsulta(String jpql, Class<T> claseResultado, Object... parametros) {
        TypedQuery<T> query = entityManager.createQuery(jpql, claseResultado);

        for (int i = 0; i < parametros.length; i++) {
            query.setParameter(i + 1, parametros[i]);
        }

        return query.getResultList();
    }

    /**
     * Ejecuta una consulta SQL nativa
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> ejecutarConsultaNativa(String sql) {
        Query query = entityManager.createNativeQuery(sql, Map.class);
        return query.getResultList();
    }

    /**
     * Ejecuta una consulta SQL nativa
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> ejecutarConsultaNativa(String sql, Class<T> claseEntidad) {
        Query query = entityManager.createNativeQuery(sql, claseEntidad);
        return query.getResultList();
    }

    /**
     * Ejecuta una consulta SQL nativa con parámetros
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> ejecutarConsultaNativa(String sql, Class<T> claseEntidad, Object... parametros) {
        Query query = entityManager.createNativeQuery(sql, claseEntidad);

        for (int i = 0; i < parametros.length; i++) {
            query.setParameter(i + 1, parametros[i]);
        }

        return query.getResultList();
    }

    /**
     * Ejecuta un update/delete nativo
     */
    public int ejecutarActualizacionNativa(String sql, Object... parametros) {
        Query query = entityManager.createNativeQuery(sql, Map.class);

        for (int i = 0; i < parametros.length; i++) {
            query.setParameter(i + 1, parametros[i]);
        }

        return query.executeUpdate();
    }

    /**
     * Refresca una entidad desde la base de datos
     */
    public <T> void refrescar(T entidad) {
        entityManager.refresh(entidad);
    }

    /**
     * Limpia el contexto de persistencia
     */
    public void limpiarContexto() {
        entityManager.clear();
    }

    /**
     * Fuerza sincronización con la base de datos
     */
    public void sincronizar() {
        entityManager.flush();
    }
}
