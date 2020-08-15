
package Operacion.Egreso;


import Entidad.CategorizacionOperacion.CategoriaOperacion;
import Entidad.CategorizacionOperacion.Criterio;
import Operacion.Operacion;
import PlanificadorDeTareas.Tarea;
import PlanificadorDeTareas.TareaValidacion;
import Usuario.Mensaje;
import Usuario.Usuario;
import Validadores.ValidadorDeTransparencia;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class OperacionEgreso extends Operacion {
    //Atributos
    private Proveedor proveedor;
    private Comprobante comprobante;
    private MedioDePago medioDePago;
    private Detalle detalle;
    private List<Usuario> revisores;
    private Integer cantMinPresupuestos;
    private List<Presupuesto> presupuestos;
    private List<CategoriaOperacion> categorias;
    private Usuario creadoPor;
    private double montoTotal;

    //Constructor
    public OperacionEgreso(Proveedor proveedor, Comprobante comprobante, MedioDePago medioDePago,
                           Detalle detalle, Integer cantMinPresupuestos, List<CategoriaOperacion> categorias,
                           Usuario creadoPor) {
        this.proveedor = proveedor;
        this.comprobante = comprobante;
        this.medioDePago = medioDePago;
        this.detalle = detalle;
        this.revisores = new ArrayList<>();
        this.cantMinPresupuestos = cantMinPresupuestos;
        this.presupuestos = new ArrayList<>();
        this.categorias = categorias;

        this.creadoPor = creadoPor;
        this.fecha = LocalTime.now();
        this.nroOperacion = 1; //Autogenerado

        this.montoTotal = montoTotal();
    }
    //Este es el proceso de registro de la operacion

    //Getter Setter
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public Comprobante getComprobantes() { return comprobante; }
    public void setComprobantes(Comprobante comprobantes) { this.comprobante = comprobantes; }

    public MedioDePago getMedioDePago() { return medioDePago; }
    public void setMedioDePago(MedioDePago medioDePago) { this.medioDePago = medioDePago; }

    public Detalle getDetalle() { return detalle; }
    public void setDetalle(Detalle detalle) { this.detalle = detalle; }

    public List<Usuario> getRevisores() { return revisores; }
    public void setRevisores(List<Usuario> revisores) { this.revisores = revisores; }

    public Integer getCantMinPresupuestos() { return cantMinPresupuestos; }
    public void setCantMinPresupuestos(Integer cantMinPresupuestos) { this.cantMinPresupuestos = cantMinPresupuestos; }

    public List<Presupuesto> getPresupuestos() { return presupuestos; }
    public void setPresupuestos(ArrayList<Presupuesto> presupuestos) { this.presupuestos = presupuestos; }

    public List<CategoriaOperacion> getCategoria() { return categorias; }
    public void setCategoriaOperacion(List<CategoriaOperacion> categorias) { this.categorias = categorias; }

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

    //Metodos
    public double getMontoTotal() { return montoTotal; }
    @Override
    protected double montoTotal() {
        return this.subtotalPresupuestos()+this.detalle.calcularSubtotal();
    }

    private double subtotalPresupuestos(){
        return this.presupuestos.stream()
                .mapToDouble(presupuesto -> presupuesto.mostrarCosto())
                .sum();
    }

    private List<Criterio> getCriterios(){
        return this.creadoPor.getEntidadPertenece().getCriterios();
    }
    public boolean validarOperacion(OperacionEgreso operacionEgreso)
    {
        return new ValidadorDeTransparencia(getCriterios()).validaEgreso(this);
    }

    private Mensaje generaMensaje(){
        getCriterios();


        //Todo: agregar en el 2do parametro cuales validaciones pasaron y cuales no
        return new Mensaje("Operacion Egreso #"+Long.toString(this.nroOperacion),"Paso validaciones");
    }
    public void notificaRevisores(){
        this.revisores.stream()
                .forEach(usuario ->
                            usuario.getBandejaDeMensajes().agregateMensaje(this.generaMensaje()) );
    }
}
