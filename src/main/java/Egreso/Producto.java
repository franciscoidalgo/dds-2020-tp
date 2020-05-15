package Egreso;

public class Producto {
    //Atributos
    private float precioUnitario;
    private String nombre;
    private String descripcion;

    //Constructor
    Producto(String nombre, String descripcion, float precio){
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.precioUnitario=precio;
    }

    //Metodos
    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
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
}
