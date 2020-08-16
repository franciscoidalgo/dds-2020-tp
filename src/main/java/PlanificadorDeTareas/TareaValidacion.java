package PlanificadorDeTareas;

import Operacion.Egreso.OperacionEgreso;
import Validadores.ValidadorDeTransparencia;

import java.util.Date;
import java.util.TimerTask;

public class TareaValidacion extends TimerTask {

    private OperacionEgreso egreso;
    private ValidadorDeTransparencia validador;
    /*Constructor*/
    public TareaValidacion(OperacionEgreso egreso, ValidadorDeTransparencia validador) {
        this.egreso = egreso;
        this.validador = validador;
    }
    /*Funcionalidad*/
    @Override
    public void run() {
        System.out.println("Ejecución tarea validación: "+ new Date());
        /*
        System.out.println("Contenido del mensaje");
        System.out.println("Asunto: "+ this.validador.asuntoMensaje(this.egreso));
        System.out.println("Cuerpo: "+ this.validador.cuerpoMensaje(this.egreso));
        */
        this.validador.notificaResultados(this.egreso);
    }


}
