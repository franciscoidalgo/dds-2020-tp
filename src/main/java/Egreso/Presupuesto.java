package java.Egreso;

public class Presupuesto {
    private Detalle detalle;

    public float mostrarCosto(){ return this.detalle.calcularSubtotal(); }
}
