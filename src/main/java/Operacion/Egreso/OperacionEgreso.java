
package Operacion.Egreso;


import Operacion.Operacion;
import Usuario.Mensaje;
import Usuario.Usuario;
import Validadores.ValidadorDeTransparencia;

import java.util.ArrayList;
import java.util.List;

public class OperacionEgreso extends Operacion {
    //Atributos
    private DetalleCompra detalle;
    private MedioDePago medioDePago;
    private List<Usuario> revisores;
    private List<Presupuesto> presupuestos;

    //Constructor

    public OperacionEgreso(MedioDePago medioDePago,
                           DetalleCompra detalle,
                           Usuario creadoPor) {
        super(creadoPor);
        this.medioDePago = medioDePago;
        this.detalle = detalle;
        this.revisores = new ArrayList<>();
        this.presupuestos = new ArrayList<>();

    }
    //Este es el proceso de registro de la operacion

    //Getter Setter

    public MedioDePago getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public DetalleCompra getDetalleValidable() {
        return detalle;
    }

    public void setDetalleValidable(DetalleCompra detalle) {
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

    /*Funcionales*/
    @Override
    public double montoTotal() {
        return this.detalle.montoTotal();
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
        return this.detalle.solicitudTieneItems();
    }

}
