package Egreso;

public class Servicio implements Item {

    private Integer precioServicio;

    public Servicio (Integer precioServicio){
        this.precioServicio = precioServicio;
    }

    public Integer calcularPrecio() {
        return this.precioServicio;
    }
}
