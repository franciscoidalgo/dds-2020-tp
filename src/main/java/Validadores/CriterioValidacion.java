package Validadores;

import Operacion.Egreso.OperacionEgreso;

public interface CriterioValidacion {

    Boolean validaEgreso(OperacionEgreso unEgreso);
    String resultado(OperacionEgreso unEgreso);


}
