package Egreso;

public class Producto implements Item{

    private Integer cantidad;
    private Integer precioIndividual;

    public Producto (Integer precio, Integer cantidad){
        this.cantidad = cantidad;
        this.precioIndividual = precio;
    }

    public Integer calcularSubtotal() {
        return cantidad * precioIndividual;
    }
}
