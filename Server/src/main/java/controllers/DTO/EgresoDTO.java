package controllers.DTO;

import domain.Entidad.CategorizacionEmpresa.Categoria;
import domain.Operacion.Egreso.*;

import java.util.List;

public class EgresoDTO {

    public Integer id;
    public DetalleOperacion detalle;
    public MedioDePago medioDePago;

    public List<Pedido> pedido;

    //private List<Presupuesto> presupuestos;
    //private OperacionIngreso ingreso;
    public Integer cantPresupuestos;

    public Integer cantPresupuestosFaltantes;
    public String fecha;
    public double montoTotal;
    public List<Categoria> categorias;

    //Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public DetalleOperacion getDetalle(DetalleOperacion detalle) {
        return this.detalle;
    }

    public void setDetalle(DetalleOperacion detalle) {
        this.detalle = detalle;
    }

    public MedioDePago getMedioDePago(MedioDePago medioDePago) {
        return this.medioDePago;
    }

    public void setMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public Integer getCantPresupuestos(Integer cantPresupuestos) {
        return this.cantPresupuestos;
    }

    public void setCantPresupuestos(Integer cantPresupuestos) {
        this.cantPresupuestos = cantPresupuestos;
    }

    public DetalleOperacion getDetalle() {
        return detalle;
    }

    public MedioDePago getMedioDePago() {
        return medioDePago;
    }


    public Integer getCantPresupuestos() {
        return cantPresupuestos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }
    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Integer getCantPresupuestosFaltantes() {
        return cantPresupuestosFaltantes;
    }

    public void setCantPresupuestosFaltantes(Integer cantPresupuestosFaltantes) {
        this.cantPresupuestosFaltantes = cantPresupuestosFaltantes;
    }

    public List<Pedido> getPedido() {
        return pedido;
    }

    public void setPedido(List<Pedido> pedido) {
        this.pedido = pedido;
    }
}
