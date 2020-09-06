//
// https://docs.oracle.com/javase/6/docs/api/java/util/Timer.html
//


package PlanificadorDeTareas;

import domain.Operacion.Egreso.OperacionEgreso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Timer;

public class Planificador {

    private Timer timer;
    private int delay;
    private int period;
    private String path = "src/main/java/PlanificadorDeTareas/config.txt";
    private static Planificador instancia = null;

    public Planificador(){
        this.timer = new Timer();
        actualizarConfiguracion();

    }

    public static Planificador instancia() {
        if(instancia == null){
            instancia = new Planificador();
        }
        return instancia;
    }

    public void planificaTareaValidacion (OperacionEgreso unEgreso) {

        timer.schedule(this.generaTareaValidacion(unEgreso), delay, period);

    }

    private TareaValidacion generaTareaValidacion(OperacionEgreso unEgreso){
        return new TareaValidacion(unEgreso);
    }
    private void actualizarConfiguracion () {
        try {
            File configFile = new File (path);
            Scanner miScanner = new Scanner (configFile);

            while (miScanner.hasNextLine()){
                String data = miScanner.nextLine();
                int indexOfTheNumber = data.indexOf("=") + 1;
                String number = data.substring(indexOfTheNumber).trim();

                if(data.contains("delay")){
                    delay = Integer.parseInt(number);
                }
                if (data.contains("period")){
                    period = Integer.parseInt(number);
                }
            }
            miScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo de configuraciones.");
            e.printStackTrace();
        }
    }

}
