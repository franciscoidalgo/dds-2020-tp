package domain.Operacion.Egreso;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "presupuesto")
public class Presupuesto extends EntidadPersistente {

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private DetalleOperacion detalle;

    @Column(name = "monto_total")
    private double montoTotal;

    //Constructors
    public Presupuesto(DetalleOperacion detalle, double montoTotal) {
        this.detalle = detalle;
        this.montoTotal =montoTotal;
    }

    public Presupuesto() {

    }

    //Getters y Setters
    public DetalleOperacion getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleOperacion detalle) {
        this.detalle = detalle;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    //Funcionalidad
    public double montoTotal() {
        return this.montoTotal;
    }

    public Boolean coincidenPedido(OperacionEgreso unEgreso){
        return detalle.coincidenPedido(unEgreso.getDetalle());
    }

    public Boolean coincideProveedor(OperacionEgreso unEgreso){
        return this.getDetalle().coincidenProveedores(unEgreso.getDetalle());
    }


}
