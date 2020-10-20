package domain.Validadores;

import config.ConfiguracionValidador;
import domain.Operacion.Egreso.OperacionEgreso;

public class CriterioValidacionCantidadPresupuesto implements CriterioValidacion {
    private final int limitePresupuestos = ConfiguracionValidador.cantPresupuestos;



    @Override
    public Boolean validaEgreso(OperacionEgreso unEgreso) {
        return this.limitePresupuestos == unEgreso.getPresupuestos().size();
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













