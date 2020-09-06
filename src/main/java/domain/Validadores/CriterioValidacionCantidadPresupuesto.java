package domain.Validadores;

import domain.Operacion.Egreso.OperacionEgreso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CriterioValidacionCantidadPresupuesto implements CriterioValidacion {
    private int limitePresupuestos;



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












