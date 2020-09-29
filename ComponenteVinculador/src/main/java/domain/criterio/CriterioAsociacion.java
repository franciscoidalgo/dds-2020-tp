package domain.criterio;

import requiredModels.operacion.egreso.OperacionEgreso;

import java.time.LocalDate;
import java.util.function.Predicate;

public class CriterioAsociacion {

    private Predicate<OperacionEgreso> criterio;

    public void generarCriterioConFechas (LocalDate fechaDesde, LocalDate fechaHasta) {
        this.criterio = operacionEgreso -> (operacionEgreso.getFecha().isAfter(fechaDesde)
                        && operacionEgreso.getFecha().isBefore(fechaHasta));
    }

    public Predicate<OperacionEgreso> getCriterio (){
        return this.criterio;
    }

}
