package Validadores;

import Egreso.OperacionEgreso;

import java.util.ArrayList;

public class ValidadorDeTransparencia {

    private ArrayList<CriterioValidacion> criteriosValidadores;


    public Boolean validaEgreso(OperacionEgreso unEgreso){
        return this.criteriosValidadores.stream()
                .map(unCriterio -> unCriterio.validaEgreso(unEgreso))
                .reduce((aBoolean, aBoolean2) ->  aBoolean && aBoolean2).get();
    }
}
