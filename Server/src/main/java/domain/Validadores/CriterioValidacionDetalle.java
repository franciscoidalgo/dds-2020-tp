package domain.Validadores;

import domain.Operacion.Egreso.OperacionEgreso;

public class CriterioValidacionDetalle implements CriterioValidacion {
    @Override
    public Boolean validaEgreso(OperacionEgreso unEgreso) {
        return requierePresupuesto(unEgreso) ? verificaDetalleEgresoConAlgunPresupuesto(unEgreso) : true;
    }


    private Boolean requierePresupuesto(OperacionEgreso unEgreso) {
        return unEgreso.getCantPresupuestos() >0;
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
