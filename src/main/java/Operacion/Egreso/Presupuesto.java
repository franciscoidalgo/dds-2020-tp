package Operacion.Egreso;

import Entidad.CategorizacionOperacion.CategoriaOperacion;

import java.util.List;

public class Presupuesto {
    private Detalle detalle;
    private List<CategoriaOperacion> categorias;
    private Proveedor proveedor;
    private Comprobante comprobante;

    public Presupuesto(Detalle detalle, List<CategoriaOperacion> categorias, Proveedor proveedor) {
        this.detalle = detalle;
        this.categorias = categorias;
        this.proveedor = proveedor;
        this.comprobante = null;
    }


    //Getter and Setter
    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
        this.detalle = detalle;
    }

    public List<CategoriaOperacion> getCriterio() {
        return categorias;
    }

    public void setCategoriaOperacion(List<CategoriaOperacion> categorias) {
        this.categorias = categorias;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    //Funcionalidad
    public double montoTotal() {
        return this.detalle.calcularSubtotal();
    }

}
