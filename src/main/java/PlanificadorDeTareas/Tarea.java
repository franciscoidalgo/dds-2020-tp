//
// https://docs.oracle.com/javase/6/docs/api/java/util/Timer.html
//


package PlanificadorDeTareas;

import java.util.Timer;

public class Tarea {

    Timer timer = new Timer();
    TareaValidacion task = new TareaValidacion();

    public void planificarTareaValidacion (int delay, int period) {

        timer.schedule(task, delay, period);

    }
}
