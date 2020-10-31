package domain.Validadores;


import domain.Operacion.Egreso.OperacionEgreso;

public class CriterioValidacionSeleccion implements CriterioValidacion {
    @Override
    public Boolean validaEgreso(OperacionEgreso unEgreso) {
        return !this.requierePresupuesto(unEgreso) || this.seleccionoProveedoresMasBaratos(unEgreso);
    }

    private Boolean requierePresupuesto(OperacionEgreso unEgreso) {
        return unEgreso.getCantPresupuestos() > 0;
    }

    private Boolean seleccionoProveedoresMasBaratos(OperacionEgreso unEgreso) {
        return unEgreso.getPresupuestos().stream().
                filter(p -> p.montoTotal() == this.menorPresupuestoSegun(unEgreso))
                .anyMatch(presupuesto -> presupuesto.coincideProveedor(unEgreso));
    }

    private double menorPresupuestoSegun(OperacionEgreso unEgreso) {
        return unEgreso.getPresupuestos().stream().
                reduce((p, p2) -> p.montoTotal() <= p2.montoTotal() ? p : p2).get().montoTotal();
    }

    @Override
    public String resultado(OperacionEgreso unEgreso) {
        if (this.validaEgreso(unEgreso)) {
            return "Seleccion Presupuesto: Valida";
        } else {
            return "Seleccion Presupuesto: Invalida";
        }

    }
}
