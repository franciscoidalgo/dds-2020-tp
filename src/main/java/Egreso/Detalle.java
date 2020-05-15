package Egreso;

public class Detalle {


    private int cantidad;
    //private float subtotal;
    private Producto producto;

    //constructor
    Detalle(Producto producto, int cantidad){
        this.cantidad=cantidad;
        this.producto = producto;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public float subtotal(){
        return producto.getPrecioUnitario()*cantidad;
    }
}
