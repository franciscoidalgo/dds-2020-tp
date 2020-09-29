package domain.Operacion.Egreso;

import domain.Entidad.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "presupuesto")
public class Presupuesto extends Entidad {

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private DetalleCompra detalle;

    @Column(name = "monto_total")
    private double montoTotal;

    //Constructors
    public Presupuesto(DetalleCompra detalle,double montoTotal) {
        this.detalle = detalle;
        this.montoTotal =montoTotal;
    }

    public Presupuesto() {

    }

    //Getters y Setters
    public DetalleCompra getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleCompra detalle) {
        this.detalle = detalle;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    //Funcionalidad
    public double montoTotal() {
        return this.montoTotal();
    }

    public Boolean coincidenSolicitud(OperacionEgreso unEgreso){
        return this.getDetalle().coincidenSolicitud(unEgreso.getDetalleValidable());
    }

    public Boolean coincideProveedor(OperacionEgreso unEgreso){
        return this.getDetalle().coincidenProveedores(unEgreso.getDetalleValidable());
    }


}
