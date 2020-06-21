package Operacion.Egreso;

import Entidad.CategorizacionOperacion.Criterio;

public class Presupuesto {
    private Detalle detalle;
    private Criterio criterio;

    public Presupuesto(Detalle detalle, Criterio criterio) {
        this.detalle = detalle;
        this.criterio = criterio;
    }

    //Getter and Setter
    public Detalle getDetalle() { return detalle; }
    public void setDetalle(Detalle detalle) { this.detalle = detalle; }

    public Criterio getCriterio() { return criterio; }
    public void setCriterio(Criterio criterio) { this.criterio = criterio; }


    //Funcionalidad
    public double mostrarCosto(){ return this.detalle.calcularSubtotal(); }

}
