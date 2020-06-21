package Entidad.CategorizacionOperacion;

import java.util.ArrayList;

public class Criterio {
    private String nombre;
    private Criterio criterioHijo;
    private ArrayList<CategoriaOperacion> categorias;
    private Integer nivelJerarquia;

    //Constructor
    public Criterio(String nombre, Criterio criterioHijo, Integer nivelJerarquia){
        this.nombre = nombre;
        this.criterioHijo = criterioHijo;
        this.categorias = new ArrayList<>();
        this.nivelJerarquia = nivelJerarquia;
    }


    //Getter and Setter
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Criterio getCriterioHijo() { return criterioHijo; }
    public void setCriterioHijo(Criterio criterioHijo) { this.criterioHijo = criterioHijo; }

    public ArrayList<CategoriaOperacion> getCategorias() { return categorias; }
    public void setCategorias(ArrayList<CategoriaOperacion> categorias) { this.categorias = categorias; }

    public Integer getNivelJerarquia() { return nivelJerarquia; }
    public void setNivelJerarquia(Integer nivelJerarquia) {
        this.nivelJerarquia = nivelJerarquia;
        this.jerarquizate(); //<==== Todo: A implementar!
    }



    //Funcionalidad
    public void agregateCategoria(CategoriaOperacion categoriaOperacion){ this.categorias.add(categoriaOperacion); }
    public void agregateGenerandoCategoria(String unaDescripcion){
        this.agregateCategoria(new CategoriaOperacion(unaDescripcion));
    }
    public void borraCategoria(CategoriaOperacion categoriaOperacion){ this.categorias.remove(categoriaOperacion); }
    private void jerarquizate(){
        /** Todo:Logica de rejerarquizacion cuando se modifique;
                    -Armar una lista sacando todos los criterios (de padres e hijos)
                    -Ordenar Lista por jerarquia
                    -Rearmar los criterio por descendencia
        **/
    }

}
