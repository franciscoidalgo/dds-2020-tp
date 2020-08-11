package Validadores;

import Entidad.CategorizacionOperacion.Criterio;
import Operacion.Egreso.OperacionEgreso;

import java.util.Date;
import java.util.List;

public class ValidadorDeTransparencia {

    private List<CriterioValidacion> criteriosValidadores;

    public ValidadorDeTransparencia(List<CriterioValidacion> validadores){
        criteriosValidadores = validadores;
    }

    public Boolean validaEgreso(OperacionEgreso unEgreso){
        System.out.println("Ejecución validación egreso: " + new Date());
        return this.criteriosValidadores.stream()
                .map(unCriterio -> unCriterio.validaEgreso(unEgreso))
                .reduce((aBoolean, aBoolean2) -> Boolean.logicalAnd(aBoolean,aBoolean2)).get();
    }
}
