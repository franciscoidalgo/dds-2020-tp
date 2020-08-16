package Validadores;

import Operacion.Egreso.OperacionEgreso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ValidarCantidadPresupuesto implements CriterioValidacion {
    private int limitePresupuestos;


    public ValidarCantidadPresupuesto() {
        this.actualizarConfiguracion();
    }

    @Override
    public Boolean validaEgreso(OperacionEgreso unEgreso) {
        return this.limitePresupuestos == unEgreso.getPresupuestos().size();
    }

    @Override
    public String resultado(OperacionEgreso unEgreso) {
        if(this.validaEgreso(unEgreso)){
            return "Cantidad Presupuesto: Valida";
        }else{
            return "Cantidad Presupuesto: Invalida";
        }

    }


    private void actualizarConfiguracion () {
        try {
            File configFile = new File ("src/main/java/PlanificadorDeTareas/config.txt");
            Scanner miScanner = new Scanner (configFile);
            while (miScanner.hasNextLine()){
                String data = miScanner.nextLine();
                int indexOfTheNumber = data.indexOf("=") + 1;
                String number = data.substring(indexOfTheNumber).trim();

                if(data.contains("cantPresupuestos")){
                    limitePresupuestos = Integer.parseInt(number);
                }
            }
            miScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo de configuraciones.");
            e.printStackTrace();
        }
    }




}













