//
// https://docs.oracle.com/javase/6/docs/api/java/util/Timer.html
//


package PlanificadorDeTareas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Timer;

public class Tarea {

    private Timer timer;
    private TareaValidacion task;
    private int delay;
    private int period;
    private String path = "src/main/java/PlanificadorDeTareas/config.txt";

    public Tarea (TareaValidacion tarea){
        this.timer = new Timer();
        this.task = tarea;
        actualizarConfiguracion();
        this.planificarTareaValidacion();
    }

    private void planificarTareaValidacion () {


        timer.schedule(task, delay, period);

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
