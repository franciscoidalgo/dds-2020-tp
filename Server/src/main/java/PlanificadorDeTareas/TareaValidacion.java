package PlanificadorDeTareas;

import Persistencia.EntityManagerHelper;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Validadores.ValidadorDeTransparencia;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
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
        try {
            EntityManagerHelper.getEntityManager();
            ValidadorDeTransparencia.instancia().notificaResultados(this.egreso);
            EntityManagerHelper.closeEntityManager();
        } catch (Exception e) {
            EntityManagerHelper.closeEntityManager();
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
