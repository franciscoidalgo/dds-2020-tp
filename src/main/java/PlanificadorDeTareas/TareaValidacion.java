package PlanificadorDeTareas;

import Operacion.Egreso.OperacionEgreso;
import Operacion.Operacion;
import Password.*;
import Validadores.ValidadorDeTransparencia;

import java.util.Date;
import java.util.TimerTask;

public class TareaValidacion extends TimerTask {

    @Override
    public void run() {
        System.out.println("Ejecución tarea validación: "+ new Date());
    }
}
