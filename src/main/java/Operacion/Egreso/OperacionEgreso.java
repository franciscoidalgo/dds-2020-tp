
package Operacion.Egreso;


import Entidad.CategorizacionOperacion.Criterio;
import Operacion.Operacion;
import PlanificadorDeTareas.Tarea;
import PlanificadorDeTareas.TareaValidacion;
import Usuario.Mensaje;
import Usuario.Usuario;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TimerTask;

public class OperacionEgreso extends Operacion {
    //Atributos
    private Proveedor proveedor;
    private Comprobante comprobante;
    private MedioDePago medioDePago;
    private Detalle detalle;
    private ArrayList<Usuario> revisores;
    private Integer cantMinPresupuestos;
    private ArrayList<Presupuesto> presupuestos;
    private Criterio criterio;
    private Usuario creadoPor;

    //Constructor

    public OperacionEgreso(int i, LocalTime now, Proveedor proveedor, Comprobante comprobante, MedioDePago medioDePago, Detalle detalle, Integer cantMinPresupuestos, Criterio criterio) {
        this.proveedor = proveedor;
        this.comprobante = comprobante;
        this.medioDePago = medioDePago;
        this.detalle = detalle;
        this.revisores = new ArrayList<>();
        this.cantMinPresupuestos = cantMinPresupuestos;
        this.presupuestos = new ArrayList<>();
        this.criterio = criterio;
        this.creadoPor = null;
    }

    //Getter Setter
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public Comprobante getComprobantes() { return comprobante; }
    public void setComprobantes(Comprobante comprobantes) { this.comprobante = comprobantes; }

    public MedioDePago getMedioDePago() { return medioDePago; }
    public void setMedioDePago(MedioDePago medioDePago) { this.medioDePago = medioDePago; }

    public Detalle getDetalle() { return detalle; }
    public void setDetalle(Detalle detalle) { this.detalle = detalle; }

    public ArrayList<Usuario> getRevisores() { return revisores; }
    public void setRevisores(ArrayList<Usuario> revisores) { this.revisores = revisores; }

    public Integer getCantMinPresupuestos() { return cantMinPresupuestos; }
    public void setCantMinPresupuestos(Integer cantMinPresupuestos) { this.cantMinPresupuestos = cantMinPresupuestos; }

    public ArrayList<Presupuesto> getPresupuestos() { return presupuestos; }
    public void setPresupuestos(ArrayList<Presupuesto> presupuestos) { this.presupuestos = presupuestos; }

    public Criterio getCriterio() { return criterio; }
    public void setCriterio(Criterio criterio) { this.criterio = criterio; }

    //Metodos
    public boolean validarOperacion(OperacionEgreso operacionEgreso)
    {//Todo: Terminar parte validaciones
        return true;
    }


    public void agregaPresupuesto(Presupuesto unPresupuesto){
        presupuestos.add(unPresupuesto);
    }

    public void sacaPresupuesto(Presupuesto unPresupuesto){
        presupuestos.remove(unPresupuesto);
    }

    public void agregateRevisor(Usuario unRevisor){
        this.revisores.add(unRevisor);
    }

    public void sacaRevisor(Usuario unRevisor){
        this.revisores.remove(unRevisor);
    }

    @Override
    public void registrate() {
        //Todo:Falta esta parte de persistencia.
        super.registrate();
    }

    @Override
    public double montoTotal() {
        return this.subtotalPresupuestos()+this.detalle.calcularSubtotal();
    }

    private double subtotalPresupuestos(){
        //(aBoolean, aBoolean2) -> Boolean.logicalAnd(aBoolean,aBoolean2)
        return this.presupuestos.stream()
                .mapToDouble(presupuesto -> presupuesto.mostrarCosto())
                .sum();
    }

    private Mensaje generaMensaje(){
        //Todo: agregar en el 2do parametro cuales validaciones pasaron y cuales no
        return new Mensaje("Operacion Egreso #"+Long.toString(this.nroOperacion),"Paso validaciones");
    }
    public void notificaRevisores(){
        this.revisores.stream()
                .forEach(usuario ->
                            usuario.getBandejaDeMensajes().agregateMensaje(this.generaMensaje()) );
    }


}
