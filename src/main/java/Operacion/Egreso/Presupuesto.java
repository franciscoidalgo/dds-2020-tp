package Operacion.Egreso;

public class Presupuesto {

    private DetalleCompra detalle;

    public Presupuesto(DetalleCompra detalle) {
        this.detalle = detalle;
    }

    //Getters y Setters
    public DetalleCompra getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleCompra detalle) {
        this.detalle = detalle;
    }

    //Funcionalidad
    public double montoTotal() {
        return this.detalle.montoTotal();
    }

    public Boolean coincidenSolicitud(OperacionEgreso unEgreso){
        return this.getDetalle().coincidenSolicitud(unEgreso.getDetalleValidable());
    }

    public Boolean coincideProveedor(OperacionEgreso unEgreso){
        return this.getDetalle().coincidenProveedores(unEgreso.getDetalleValidable());
    }


}
