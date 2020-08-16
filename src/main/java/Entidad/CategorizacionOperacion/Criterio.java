package Entidad.CategorizacionOperacion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Criterio {
    private String nombre;
    private Criterio criterioHijo;
    private List<CategoriaOperacion> categorias;

    //Constructores
    public Criterio(String nombre, Criterio criterioHijo) {
        this.nombre = nombre;
        this.criterioHijo = criterioHijo;
        this.categorias = new ArrayList<>();
    }

    public Criterio(String nombre) {
        this.nombre = nombre;
        this.criterioHijo = null;
        this.categorias = new ArrayList<>();
    }

    //Getter and Setter
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Criterio getCriterioHijo() {
        return criterioHijo;
    }

    public void setCriterioHijo(Criterio criterioHijo) {
        if (this != criterioHijo) this.criterioHijo = criterioHijo;
    }

    public List<CategoriaOperacion> getCategorias() {
        return this.categorias;
    }

    public void setCategorias(List<CategoriaOperacion> categorias) {
        this.categorias = categorias;
    }

    //Funcionalidad
    public void agregateCategoria(CategoriaOperacion categoriaOperacion) {
        this.categorias.add(categoriaOperacion);
    }

    public void borraCategoria(CategoriaOperacion categoriaOperacion) {
        this.categorias.remove(categoriaOperacion);
    }

    public List<CategoriaOperacion> mostraTodasCategorias() {



            if(criterioHijo != null && !categorias.isEmpty()) {
                List<CategoriaOperacion> listaBase;
                List<CategoriaOperacion> listaHijo;
                listaBase = this.categorias;
                listaHijo = this.mostraTodasCategorias();
                listaBase.addAll(listaHijo);
                return listaBase.stream().collect(Collectors.toSet()).stream().//Saco repetidos
                        filter(categoriaOperacion -> categoriaOperacion != null).//Saco null
                        collect(Collectors.toList());
            }
            return  categorias;
    }

}
