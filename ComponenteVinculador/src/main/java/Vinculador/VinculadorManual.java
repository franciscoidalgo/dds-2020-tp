package Vinculador;

import DTOs.DTOOperacionEgreso;
import DTOs.DTOOperacionIngreso;

public class VinculadorManual{
    public static void vincular(DTOOperacionEgreso egreso,
                                DTOOperacionIngreso ingreso){

        if(egreso.puedeVincularse(egreso, ingreso)) {
            //LA vinculación
            realizarVinculacion(egreso, ingreso);
        }

        //Cabe destacar a este punto que no veo ningún atributo
        //Para los ingresos en el cual el mismo conozca a sus egresos
    }

    private static void realizarVinculacion(DTOOperacionEgreso egreso, DTOOperacionIngreso ingreso) {
        egreso.setEstaAsociado(true);
        egreso.setIngreso(ingreso);
        ingreso.agregateEgreso(egreso);
    }

}