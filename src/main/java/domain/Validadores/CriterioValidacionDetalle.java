package domain.Validadores;

import config.ConfiguracionValidador;
import domain.Operacion.Egreso.OperacionEgreso;

public class CriterioValidacionDetalle implements CriterioValidacion {
    @Override
    public Boolean validaEgreso(OperacionEgreso unEgreso) {
        return this.requierePresupuesto(unEgreso) ? this.verificaDetalleEgresoConAlgunPresupuesto(unEgreso) : true;
    }


    private Boolean requierePresupuesto(OperacionEgreso unEgreso) {

        return ConfiguracionValidador.cantPresupuestos >0;
    }

    private Boolean verificaDetalleEgresoConAlgunPresupuesto(OperacionEgreso unEgreso) {
        return unEgreso.getPresupuestos().stream().
                anyMatch(p -> p.coincidenPedido(unEgreso));
    }


    @Override
    public String resultado(OperacionEgreso unEgreso) {
        if (this.validaEgreso(unEgreso)) {
            return "Solicitud Coinciden Con Presupuesto: Valida";
        } else {
            return "Solicitud Coinciden Con Presupuesto: Invalida";
        }

    }

}
