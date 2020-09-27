package PlanificadorDeTareas;

import domain.Operacion.Egreso.OperacionEgreso;
import domain.Validadores.ValidadorDeTransparencia;

import java.util.Date;
import java.util.TimerTask;

public class TareaValidacion extends TimerTask {


    private final OperacionEgreso egreso;

    /*Constructor*/
    public TareaValidacion(OperacionEgreso unEgreso) {
        this.egreso = unEgreso;
    }

    /*Funcionalidad*/
    @Override
    public void run() {
        System.out.println("Ejecución tarea validación: "+ new Date());
        ValidadorDeTransparencia.instancia().notificaResultados(this.egreso);
    }

}
