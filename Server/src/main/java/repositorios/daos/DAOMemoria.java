package repositorios.daos;

import domain.Entidad.EntidadPersistente;
import repositorios.BusquedaCondicional;

import java.util.List;

public class DAOMemoria<T> implements DAO<T>{

    private final List<EntidadPersistente> entidades;

    public DAOMemoria (List<EntidadPersistente> entidades){
        this.entidades = entidades;
    }


    @Override
    public List<T> buscarTodos() {
        return (List<T>) this.entidades;
    }

    @Override
    public T buscar(int id) {
        return (T) this.entidades
                .stream()
                .filter(e->e.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public T buscar(BusquedaCondicional condicional) {
        return (T) this.entidades
                .stream()
                .filter(condicional.getCondicionPredicado())
                .findFirst()
                .get();
    }

    @Override
    public void agregar(Object unObjeto) {
        this.entidades.add((EntidadPersistente) unObjeto);
    }

    @Override
    public void modificar(Object object) {

    }

    @Override
    public void eliminar(Object unObjeto) {
        this.entidades.remove(unObjeto);
    }
}
