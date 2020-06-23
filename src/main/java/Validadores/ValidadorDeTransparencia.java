package Validadores;

import Operacion.Egreso.OperacionEgreso;

import java.util.ArrayList;
import java.util.Date;

public class ValidadorDeTransparencia {

    private ArrayList<CriterioValidacion> criteriosValidadores;


    public Boolean validaEgreso(OperacionEgreso unEgreso){
        System.out.println("Ejecución validación egreso: " + new Date());
        return this.criteriosValidadores.stream()
                .map(unCriterio -> unCriterio.validaEgreso(unEgreso))
                .reduce((aBoolean, aBoolean2) -> Boolean.logicalAnd(aBoolean,aBoolean2)).get();
    }
}
