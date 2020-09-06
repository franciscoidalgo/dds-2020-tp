//
// https://docs.oracle.com/javase/6/docs/api/java/util/Timer.html
//


package PlanificadorDeTareas;

import config.ConfiguracionScheduler;
import domain.Operacion.Egreso.OperacionEgreso;
import java.util.Timer;

public class Planificador {

    private Timer timer;
    private static Planificador instancia = null;

    public Planificador(){
        this.timer = new Timer();
    }

    public static Planificador instancia() {
        if(instancia == null){
            instancia = new Planificador();
        }
        return instancia;
    }

    public void planificaTareaValidacion (OperacionEgreso unEgreso) {

        timer.schedule(this.generaTareaValidacion(unEgreso), ConfiguracionScheduler.delay, ConfiguracionScheduler.period);

    }

    private TareaValidacion generaTareaValidacion(OperacionEgreso unEgreso){
        return new TareaValidacion(unEgreso);
    }

}
