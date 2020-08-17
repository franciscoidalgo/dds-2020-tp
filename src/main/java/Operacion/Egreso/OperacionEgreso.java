
package Operacion.Egreso;


import Entidad.CategorizacionOperacion.CategoriaOperacion;
import Operacion.Operacion;
import Usuario.Mensaje;
import Usuario.Usuario;
import Validadores.ValidadorDeTransparencia;

import java.util.ArrayList;
import java.util.List;

public class OperacionEgreso extends Operacion {
    //Atributos
    private Proveedor proveedor;
    private Comprobante comprobante;
    private MedioDePago medioDePago;
    private Detalle detalle;
    private List<Usuario> revisores;
    private List<Presupuesto> presupuestos;
    private List<CategoriaOperacion> categorias;
    //Constructor

    public OperacionEgreso(Proveedor proveedor, Comprobante comprobante, MedioDePago medioDePago,
                           Detalle detalle, List<CategoriaOperacion> categorias,
                           Usuario creadoPor) {
        super(creadoPor);
        this.proveedor = proveedor;
        this.comprobante = comprobante;
        this.medioDePago = medioDePago;
        this.detalle = detalle;
        this.revisores = new ArrayList<>();
        this.presupuestos = new ArrayList<>();
        this.categorias = categorias;

    }
    //Este es el proceso de registro de la operacion

    //Getter Setter
    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Comprobante getComprobantes() {
        return comprobante;
    }

    public void setComprobantes(Comprobante comprobantes) {
        this.comprobante = comprobantes;
    }

    public MedioDePago getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
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

    public List<CategoriaOperacion> getCategoria() {
        return categorias;
    }

    public void setCategoriaOperacion(List<CategoriaOperacion> categorias) {
        this.categorias = categorias;
    }

    /*Funcionales*/
    @Override
    public double montoTotal() {
        return this.detalle.calcularSubtotal();
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
        return !this.detalle.getListaItems().isEmpty();
    }
}
