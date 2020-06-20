package java.Entidad;

import java.util.ArrayList;

public class Criterio {
    private String nombre;
    private Criterio criterioHijo;
    private ArrayList<CategoriaOperacion> categorias;
    private Integer nivelJerarquia;

    //Getter and Setter
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Criterio getCriterioHijo() { return criterioHijo; }
    public void setCriterioHijo(Criterio criterioHijo) { this.criterioHijo = criterioHijo; }

    public ArrayList<CategoriaOperacion> getCategorias() { return categorias; }
    public void setCategorias(ArrayList<CategoriaOperacion> categorias) { this.categorias = categorias; }

    public Integer getNivelJerarquia() { return nivelJerarquia; }
    public void setNivelJerarquia(Integer nivelJerarquia) { this.nivelJerarquia = nivelJerarquia; }

    //Constructor
    public Criterio(String nombre, Criterio criterioHijo,
                    ArrayList<CategoriaOperacion> categorias, Integer nivelJerarquia){
        this.nombre = nombre;
        this.criterioHijo = criterioHijo;
        this.categorias = categorias;
        this.nivelJerarquia = nivelJerarquia;
    }

    //Funcionalidad
    public void agregateCategoria(CategoriaOperacion categoriaOperacion){ this.categorias.add(categoriaOperacion); }
    public void borraCategoria(CategoriaOperacion categoriaOperacion){ this.categorias.remove(categoriaOperacion); }
}
