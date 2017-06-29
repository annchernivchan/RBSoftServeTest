package application.dao;

import java.util.List;

public interface InterfaceDao<T> {

    T findOne(String id);
    List<T> findAll();
    void create(T entity);
    T update(T entity);
    void delete(T entity);
    void deleteById(String entityId);

}
