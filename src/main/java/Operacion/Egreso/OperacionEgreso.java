
package Egreso;

import Usuario.Usuario;

import Entidad.CategorizacionOperacion.Criterio;
import java.Operacion.Egreso.Presupuesto;

import java.Operacion.Operacion;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OperacionEgreso extends Operacion {
    //Atributos
    private Egreso.Proveedor proveedor;
    private ArrayList<Egreso.Comprobante> comprobantes;
    private Egreso.MedioDePago medioDePago;
    private Egreso.Detalle detalle;
    private ArrayList<Usuario> revisores;
    private Integer cantMinPresupuestos;
    private ArrayList<Presupuesto> presupuestos;
    private Criterio criterio;

    //Constructor
    public OperacionEgreso(long montoTotal, long nroOperacion, Timestamp fecha,
                           Egreso.Proveedor proveedor, ArrayList<Egreso.Comprobante> comprobantes,
                           Egreso.MedioDePago medioDePago, Egreso.Detalle detalle,
                           ArrayList<Usuario> revisores, Integer cantMinPresupuestos,
                           ArrayList<Presupuesto> presupuestos, Criterio criterio){
        this.montoTotal = montoTotal;
        this.nroOperacion = nroOperacion;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.comprobantes = comprobantes;
        this.medioDePago = medioDePago;
        this.detalle = detalle;
        this.revisores = revisores;
        this.cantMinPresupuestos = cantMinPresupuestos;
        this.presupuestos = presupuestos;
        this.criterio = criterio;
    }

    //Getter Setter
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public ArrayList<Comprobante> getComprobantes() { return comprobantes; }
    public void setComprobantes(ArrayList<Comprobante> comprobantes) { this.comprobantes = comprobantes; }

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
    public boolean validarOperacion(OperacionEgreso operacionEgreso){
        return true;
    }

    public void generarEnviarMensaje(ArrayList<Usuario> revisores){

    }

    //Falta mostrar categoria que no se que cuerno es...

}
