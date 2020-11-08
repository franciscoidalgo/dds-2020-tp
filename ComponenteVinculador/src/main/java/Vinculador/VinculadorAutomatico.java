package Vinculador;

import DTOs.DTOOperacionEgreso;
import DTOs.DTOOperacionIngreso;

import java.time.LocalDate;
import java.util.*;

public class VinculadorAutomatico{
    //Atributos
    private static String criterio;

    //Getters & Setters
    public static String getCriterio() { return criterio; }
    public static void setCriterio(String criterio) { VinculadorAutomatico.criterio = criterio; }


    //Funcionalidades
    public static void vincular(String criterio,
                                List<DTOOperacionEgreso> egresos,
                                List<DTOOperacionIngreso> ingresos) {

        setCriterio(criterio);

        switch(criterio){
            case "ORDEN_VALOR_PRIMERO_EGRESO":
                vincularSegunValorPrimeroEgreso(egresos, ingresos);
            break;
            case "ORDEN_VALOR_PRIMERO_INGRESO":
                vincularSegunValorPrimeroIngreso(egresos, ingresos);
            break;
            case "FECHA":
                vincularSegunFecha(egresos, ingresos);
            break;
        }
    }

    public static void vincular(String criterio,
                                List<DTOOperacionEgreso> egresos,
                                List<DTOOperacionIngreso> ingresos,
                                List<String> criterios){

        setCriterio(criterio);

        switch(criterio){
            case "MIX":
                vincularMix(egresos, ingresos, criterios);
            break;
        }
    }

    private static void vincularMix(
            List<DTOOperacionEgreso> egresos,
            List<DTOOperacionIngreso> ingresos,
            List<String> criterios) {

        for(String criterio : criterios){
            switch(criterio){
                case "ORDEN_VALOR_PRIMERO_EGRESO":
                    vincularSegunValorPrimeroEgreso(egresos, ingresos);
                break;
                case "ORDEN_VALOR_PRIMERO_INGRESO":
                    vincularSegunValorPrimeroIngreso(egresos, ingresos);
                break;
                case "FECHA":
                    vincularSegunFecha(egresos, ingresos);
                break;
            }
        }
    }

    private static void vincularSegunFecha(
            List<DTOOperacionEgreso> egresos,
            List<DTOOperacionIngreso> ingresos) {

        DTOOperacionEgreso.ordenarPorFecha(egresos);
        DTOOperacionIngreso.ordenarPorFecha(ingresos);

        for(DTOOperacionIngreso ingreso : ingresos) {
            double auxMonto = ingreso.getMontoTotal();
            for (DTOOperacionEgreso egreso : egresos) {
                if(egreso.puedeVincularse(egreso, ingreso)
                        && auxMonto > egreso.getMontoTotal()){

                    realizarVinculacion(egreso, ingreso);
                    auxMonto -= egreso.getMontoTotal();
                }
            }
        }
    }

    private static void vincularSegunValorPrimeroIngreso(
            List<DTOOperacionEgreso> egresos,
            List<DTOOperacionIngreso> ingresos) {

        DTOOperacionEgreso.ordenarPorMonto(egresos);
        DTOOperacionIngreso.ordenarPorMonto(ingresos);

        int iteraciones = egresos.size() < ingresos.size() ? egresos.size() : ingresos.size();

        for(int i=0; i<iteraciones; i++){
            if(egresos.get(i).puedeVincularse(egresos.get(i), ingresos.get(i))){
                realizarVinculacion(egresos.get(i), ingresos.get(i));
            }
        }
    }

    private static void vincularSegunValorPrimeroEgreso(
            List<DTOOperacionEgreso> egresos,
            List<DTOOperacionIngreso> ingresos) {

        DTOOperacionEgreso.ordenarPorMonto(egresos);
        DTOOperacionIngreso.ordenarPorMonto(ingresos);

        for(DTOOperacionIngreso ingreso : ingresos) {
            double auxMonto = ingreso.getMontoTotal();
            for (DTOOperacionEgreso egreso : egresos) {
                if(egreso.puedeVincularse(egreso, ingreso)
                    && auxMonto > egreso.getMontoTotal()){

                    realizarVinculacion(egreso, ingreso);
                    auxMonto -= egreso.getMontoTotal();
                }
            }
        }

    }

    private static void realizarVinculacion(DTOOperacionEgreso egreso, DTOOperacionIngreso ingreso) {
        egreso.setEstaAsociado(true);
        egreso.setIngreso(ingreso);
        ingreso.agregateEgreso(egreso);
    }
}