package domain.Validadores;

import domain.Operacion.Egreso.OperacionEgreso;
import PlanificadorDeTareas.Planificador;

import java.util.ArrayList;
import java.util.List;

public class ValidadorDeTransparencia {


    private List<CriterioValidacion> criteriosValidadores = new ArrayList<>();
    private static ValidadorDeTransparencia instancia = null;
    private final NotificadorResultadoValidacion notificador = NotificadorResultadoValidacion.instancia();
    private final Planificador planificador = Planificador.instancia();



    /*Getters y Setters*/
    public List<CriterioValidacion> getCriteriosValidadores() {
        return criteriosValidadores;
    }

    public void setCriteriosValidadores(List<CriterioValidacion> criteriosValidadores) {
        this.criteriosValidadores = criteriosValidadores;
    }


    /*Funcionales*/
    public void agregateCriterio(CriterioValidacion unCriterio){
        if(!criteriosValidadores.contains(unCriterio)) {
            this.criteriosValidadores.add(unCriterio);
        }
    }

    public void removeCriterio(CriterioValidacion unCriterio){
        this.criteriosValidadores.remove(unCriterio);
    }

    public static ValidadorDeTransparencia instancia(){
        if (instancia == null) {
            instancia = new ValidadorDeTransparencia();
        }
        return instancia;
    }

    public Boolean validaEgreso(OperacionEgreso unEgreso) {
        return this.criteriosValidadores.stream()
                .map(unCriterio -> unCriterio.validaEgreso(unEgreso))
                .reduce(Boolean::logicalAnd).get();
    }

    public void realizaValidacionAutomatica(OperacionEgreso unEgreso) {
        planificador.planificaTareaValidacion(unEgreso);
    }
    public void notificaResultados(OperacionEgreso unEgreso) {
        notificador.enviaResultados(this,unEgreso);
    }


}
