package Operacion.Egreso;

public class Item {
    private double precio;
    private String descripcion;

    public Item(double precio, String descripcion) {
        this.precio = precio;
        this.descripcion = descripcion;
    }

    //Getters and Setters
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getDescripcion() { return descripcion; }
}
