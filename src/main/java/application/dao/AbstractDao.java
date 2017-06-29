package application.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class AbstractDao<T extends Serializable> implements InterfaceDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> entityType;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T findOne(String id) {
        return entityManager.find(entityType, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager
                .createQuery("from " + entityType.getName())
                .getResultList();
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(String entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }

}
