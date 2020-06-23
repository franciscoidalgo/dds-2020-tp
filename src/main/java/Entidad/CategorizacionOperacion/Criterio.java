package Entidad.CategorizacionOperacion;

import java.util.ArrayList;

public class Criterio {
    private String nombre;
    private Criterio criterioHijo;
    private ArrayList<CategoriaOperacion> categorias;

    //Constructores
    public Criterio(String nombre, Criterio criterioHijo){
        this.nombre = nombre;
        this.criterioHijo = criterioHijo;
        this.categorias = new ArrayList<>();
    }

    public Criterio(String nombre){
        this.nombre = nombre;
        this.criterioHijo = null;
        this.categorias = new ArrayList<>();
    }

    //Getter and Setter
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Criterio getCriterioHijo() { return criterioHijo; }
    public void setCriterioHijo(Criterio criterioHijo) { this.criterioHijo = criterioHijo; }

    public ArrayList<CategoriaOperacion> getCategorias() { return categorias; }
    public void setCategorias(ArrayList<CategoriaOperacion> categorias) { this.categorias = categorias; }

    //Funcionalidad
    public void agregateCategoria(CategoriaOperacion categoriaOperacion){ this.categorias.add(categoriaOperacion); }

    public void borraCategoria(CategoriaOperacion categoriaOperacion){ this.categorias.remove(categoriaOperacion); }


}
