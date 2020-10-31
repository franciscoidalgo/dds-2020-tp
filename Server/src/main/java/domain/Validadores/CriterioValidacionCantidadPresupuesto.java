package domain.Validadores;

import domain.Operacion.Egreso.OperacionEgreso;

public class CriterioValidacionCantidadPresupuesto implements CriterioValidacion {


    @Override
    public Boolean validaEgreso(OperacionEgreso unEgreso) {
        return unEgreso.getCantPresupuestos() == unEgreso.getPresupuestos().size();
    }

    @Override
    public String resultado(OperacionEgreso unEgreso) {
        if (this.validaEgreso(unEgreso)) {
            return "Cantidad Presupuesto: Valida";
        } else {
            return "Cantidad Presupuesto: Invalida";
        }

    }

}













