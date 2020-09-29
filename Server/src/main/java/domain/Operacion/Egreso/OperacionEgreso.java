
package domain.Operacion.Egreso;


import domain.Operacion.Operacion;
import domain.Entidad.Usuario.Mensaje;
import domain.Entidad.Usuario.Usuario;
import domain.Validadores.ValidadorDeTransparencia;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operacion_egreso")
public class OperacionEgreso extends Operacion {
    //Atributos
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "detalle_compra_id")
    private DetalleOperacion detalle;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "medio_de_pago_id")
    private MedioDePago medioDePago;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "revisores_id")
    private List<Usuario> revisores;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "presupuestos_id")
    private List<Presupuesto> presupuestos;

    @Column(name = "monto_total")
    private double montoTotal;

    //Constructor

    public OperacionEgreso(MedioDePago medioDePago, DetalleOperacion detalle, double montoTotal) {
        super();
        this.medioDePago = medioDePago;
        this.detalle = detalle;
        this.montoTotal = montoTotal;
        this.revisores = new ArrayList<>();
        this.presupuestos = new ArrayList<>();

    }

    public OperacionEgreso() {
        super();
        this.revisores = new ArrayList<>();
        this.presupuestos = new ArrayList<>();
    }
    //Getter Setter

    public MedioDePago getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public DetalleOperacion getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleOperacion detalle) {
        this.detalle = detalle;
    }

    public List<Usuario> getRevisores() {
        return revisores;
    }

    public void setRevisores(List<Usuario> revisores) {
        this.revisores = revisores;
    }

    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(ArrayList<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    public void setMontoTotal(double monto){
        this.montoTotal = monto;
    }
    /*Funcionales*/
    @Override
    public double montoTotal() {
        return montoTotal;
    }

    public void agregaPresupuesto(Presupuesto unPresupuesto) throws Exception {
        if (this.puedeAgregarPresupuesto()) {
            this.presupuestos.add(unPresupuesto);
        } else {
            throw new Exception("No es posible cargar presupuesto");
        }

    }

    public void sacaPresupuesto(Presupuesto unPresupuesto) {
        presupuestos.remove(unPresupuesto);
    }

    public void agregateRevisor(Usuario unRevisor) {
        this.revisores.add(unRevisor);
    }

    public void sacaRevisor(Usuario unRevisor) {
        this.revisores.remove(unRevisor);
    }


    public void notificaRevisores(Mensaje unMensaje) {
        this.revisores.forEach(usuario ->
                usuario.recibiMensaje(unMensaje));
    }

    public void validaOperacion() {
        /*Ver esto*/
        ValidadorDeTransparencia validadorDeTransparencia = ValidadorDeTransparencia.instancia();
        validadorDeTransparencia.realizaValidacionAutomatica(this);
    }


    private Boolean puedeAgregarPresupuesto() {
        return this.detalle.tieneItems();
    }

}
