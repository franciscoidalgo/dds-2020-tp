package Criterio;

import DTOs.DTOOperacionEgreso;

import java.time.LocalDate;
import java.util.function.Predicate;

public class CriterioAsociacion {

    private Predicate<DTOOperacionEgreso> criterio;

    public void generarCriterioConFechas (LocalDate fechaDesde, LocalDate fechaHasta) {
        this.criterio = operacionEgreso -> (operacionEgreso.getFecha().isAfter(fechaDesde)
                && operacionEgreso.getFecha().isBefore(fechaHasta));
    }

    public Predicate<DTOOperacionEgreso> getCriterio (){
        return this.criterio;
    }

}