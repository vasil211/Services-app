package app.dao;

public interface Identifiable<K>{
    K getId();
    void setId(K id);
}