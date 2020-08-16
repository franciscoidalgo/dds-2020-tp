package Validadores;

import Operacion.Egreso.OperacionEgreso;
import PlanificadorDeTareas.Tarea;
import PlanificadorDeTareas.TareaValidacion;
import Usuario.Mensaje;

import java.util.ArrayList;
import java.util.List;

public class ValidadorDeTransparencia {


    private List<CriterioValidacion> criteriosValidadores;

    public ValidadorDeTransparencia() {
        CriterioValidacion criterioCantidad = new ValidarCantidadPresupuesto();
        CriterioValidacion criterioDetalle = new ValidarCriterioSeleccion();
        CriterioValidacion criterioSeleccion = new ValidarDetalle();

        criteriosValidadores = new ArrayList<>();
        criteriosValidadores.add(criterioCantidad);
        criteriosValidadores.add(criterioDetalle);
        criteriosValidadores.add(criterioSeleccion);
    }

    public List<CriterioValidacion> getCriteriosValidadores() {
        return criteriosValidadores;
    }

    public void setCriteriosValidadores(List<CriterioValidacion> criteriosValidadores) {
        this.criteriosValidadores = criteriosValidadores;
    }

    public Boolean validaEgreso(OperacionEgreso unEgreso) {
        return this.criteriosValidadores.stream()
                .map(unCriterio -> unCriterio.validaEgreso(unEgreso))
                .reduce(Boolean::logicalAnd).get();
    }

    public void realizaValidacion(OperacionEgreso unEgreso) {
        Tarea planificador;
        TareaValidacion task;
        task = new TareaValidacion(unEgreso, this);
        planificador = new Tarea(task);
    }

    public void notificaResultados(OperacionEgreso unEgreso) {
        unEgreso.notificaRevisores(this.generaMensaje(unEgreso));
    }

    private Mensaje generaMensaje(OperacionEgreso unEgreso) {

        return new Mensaje(this.asuntoMensaje(unEgreso), this.cuerpoMensaje(unEgreso));
    }

    private String detalleResultado(OperacionEgreso unEgreso) {
        return this.getCriteriosValidadores().stream().
                map(criterioValidacion -> criterioValidacion.resultado(unEgreso))
                .reduce((s, s2) -> s + '\n' + s2).get();

    }

    private String tituloResultado(OperacionEgreso unEgreso) {
        return this.validaEgreso(unEgreso) ? "Operacion Valida" : "Operacion invalida";
    }

    private String cuerpoMensaje(OperacionEgreso unEgreso) {
        return this.tituloResultado(unEgreso) + "\n En detalle: \n" + this.detalleResultado(unEgreso);
    }

    private String asuntoMensaje(OperacionEgreso unEgreso) {
        return "Operacion Egreso #" + Long.toString(unEgreso.getNroOperacion());
    }


}
