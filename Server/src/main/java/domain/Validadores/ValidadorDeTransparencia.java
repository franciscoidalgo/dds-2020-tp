package domain.Validadores;

import domain.Operacion.Egreso.OperacionEgreso;
import PlanificadorDeTareas.Planificador;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            Repositorio<OperacionEgreso> repoEgresos = FactoryRepo.get(OperacionEgreso.class);
            List<OperacionEgreso> egresos = repoEgresos.buscarTodos();
            for(OperacionEgreso egreso : egresos){
                instancia.realizaValidacionAutomatica(egreso);
            }

        }
        return instancia;
    }

    public Boolean validaEgreso(OperacionEgreso unEgreso) throws Exception {
        
        Optional<Boolean> resultado = this.criteriosValidadores.stream()
                .map(unCriterio -> unCriterio.validaEgreso(unEgreso))
                .reduce(Boolean::logicalAnd);
        if (resultado.isPresent()){
            return resultado.get();
        }else{
            throw new Exception("Hubo un error al validar");
        }

        /*
        Boolean resultadoAux = true;
        for(CriterioValidacion criterioValidacion : this.criteriosValidadores){
            resultadoAux = resultadoAux && criterioValidacion.validaEgreso(unEgreso);
        }
        return resultadoAux;
*/
    }

    public void realizaValidacionAutomatica(OperacionEgreso unEgreso) {
        planificador.planificaTareaValidacion(unEgreso);
    }
    public void notificaResultados(OperacionEgreso unEgreso) throws Exception {
        notificador.enviaResultados(this,unEgreso);
    }


}
