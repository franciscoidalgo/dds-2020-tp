package controllers.DTO;

import domain.Entidad.CategorizacionEmpresa.Categoria;
import domain.Entidad.Usuario.Usuario;
import domain.Operacion.Egreso.DetalleOperacion;
import domain.Operacion.Egreso.Item;
import domain.Operacion.Egreso.MedioDePago;
import domain.Operacion.Egreso.Presupuesto;
import domain.Operacion.Ingreso.OperacionIngreso;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class EgresoDTO {



    public Integer id;
    public DetalleOperacion detalle;
    public MedioDePago medioDePago;

    public List<Item> items;
    //private Boolean estaAsociado = false;
    //private List<Presupuesto> presupuestos;
    //private OperacionIngreso ingreso;
    public Integer cantPresupuestos;
    public LocalDate fecha;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getCantPresupuestos() {
        return cantPresupuestos;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
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
}
