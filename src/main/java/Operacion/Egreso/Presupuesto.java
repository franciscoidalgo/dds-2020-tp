package java.Operacion.Egreso;

import java.Entidad.Criterio;

public class Presupuesto {
    private Detalle detalle;
    private Criterio criterio;

    //Getter and Setter
    public Detalle getDetalle() { return detalle; }
    public void setDetalle(Detalle detalle) { this.detalle = detalle; }

    public Criterio getCriterio() { return criterio; }
    public void setCriterio(Criterio criterio) { this.criterio = criterio; }

    //Funcionalidad
    public float mostrarCosto(){ return this.detalle.calcularSubtotal(); }

    //Falta mostraCategoria: Que carajo es eso?
}
