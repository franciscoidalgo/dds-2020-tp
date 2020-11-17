package domain.Operacion.Egreso;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "presupuesto")
public class Presupuesto extends EntidadPersistente {

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "detalle_id")
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
    public double getMontoTotal() {
        return montoTotal;
    }
    public void setDetalle(DetalleOperacion detalle) {
        this.detalle = detalle;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    //Funcionalidad
    public double montoTotal() {
        return this.getDetalle().calcularMontoTotal();
    }

    public Boolean coincidenPedido(OperacionEgreso unEgreso){
        return this.detalle.coincidenPedido(unEgreso.getDetalle());
    }

    public Boolean coincideProveedor(OperacionEgreso unEgreso){
        return this.getDetalle().coincidenProveedores(unEgreso.getDetalle());
    }


}
