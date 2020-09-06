package domain.Entidad.CategorizacionEmpresa;

import java.util.ArrayList;

public class Sector {


    private String nombre;
    private String descripcion;
    private ArrayList<Categoria> categoria;


    public Sector(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = new ArrayList<>();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategoria(ArrayList<Categoria> categoria) {
        this.categoria = categoria;
    }

    public void agregateCategoria(Categoria unaCategoria) {
        categoria.add(unaCategoria);
    }
}





