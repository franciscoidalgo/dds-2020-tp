package Entidad;

public class EntidadBase {
    //Atributos
    private String nombre;
    private String descripcion;
    //Constructor

    EntidadBase(String nombre, String descripcion){
        this.nombre=nombre;
        this.descripcion= descripcion;
    }

    //Getters-Setters

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

}
