package app.dao;


import java.util.Collection;

public interface Repository<K, V extends Identifiable<K>> {

    Collection<V> findAll();
    V findById(K id);
    V create(V entity);
    V update(V entity);
    boolean deleteById(K id); //throws NonexistingEntityException;
    Long count();
}
