package Entidad;

public class EntidadBase implements Entidad{
    //Atributos
    private String nombre;
    private String descripcion;
    //Constructor

    EntidadBase(String nombre, String descripcion){
        this.nombre=nombre;
        this.descripcion= descripcion;
    }

    //Getters-Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String nombre() {
        return nombre;
    }

    @Override
    public String descripcion() {
        return descripcion;
    }
}
