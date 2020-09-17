package domain.Entidad;

public class EntidadBase extends Entidad{
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

    public String nombre() {
        return nombre;
    }

    public String descripcion() {
        return descripcion;
    }
}
