package repositorios.daos;

import repositorios.BusquedaCondicional;

import java.util.List;

public interface DAO<T> {
    List<T> buscarTodos();
    T buscar (int id);
    T buscar (BusquedaCondicional busquedaCondicional);
    void agregar (Object object);
    void modificar (Object object);
    void eliminar (Object object);
}
