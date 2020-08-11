package Operacion.Egreso;

import Entidad.CategorizacionOperacion.CategoriaOperacion;

import java.util.List;

public class Presupuesto {
    private Detalle detalle;
    private List<CategoriaOperacion> categorias;
    private double montoTotal;

    public Presupuesto(Detalle detalle, List<CategoriaOperacion> categorias) {
        this.detalle = detalle;
        this.categorias = categorias;

        this.montoTotal = montoTotal();
    }

    //Getter and Setter
    public Detalle getDetalle() { return detalle; }
    public void setDetalle(Detalle detalle) { this.detalle = detalle; }

    public List<CategoriaOperacion> getCriterio() { return categorias; }
    public void setCategoriaOperacion(List<CategoriaOperacion> categorias) { this.categorias = categorias; }

    public double getMontoTotal() { return montoTotal; }
    public double montoTotal() {
        return this.detalle.calcularSubtotal();
    }

    //Funcionalidad
    public double mostrarCosto(){ return this.detalle.calcularSubtotal(); }

}
