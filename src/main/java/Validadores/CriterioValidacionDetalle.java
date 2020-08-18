package Validadores;

import Operacion.Egreso.OperacionEgreso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CriterioValidacionDetalle implements CriterioValidacion {
    @Override
    public Boolean validaEgreso(OperacionEgreso unEgreso) {
        //La validacion de la cant de presupuesto lo hace otro validador.
        return this.requierePresupuesto(unEgreso) ? this.verificaDetalleEgresoConAlgunPresupuesto(unEgreso) : true;
    }


    private Boolean requierePresupuesto(OperacionEgreso unEgreso) {

        try {
            File configFile = new File("src/main/java/PlanificadorDeTareas/config.txt");
            Scanner miScanner = new Scanner(configFile);
            while (miScanner.hasNextLine()) {
                String data = miScanner.nextLine();
                int indexOfTheNumber = data.indexOf("=") + 1;
                String number = data.substring(indexOfTheNumber).trim();

                if (data.contains("cantPresupuestos")) {
                    return Integer.parseInt(number) > 0;
                }
            }
            miScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo de configuraciones.");
            e.printStackTrace();
        }
        return false;
    }


    private Boolean verificaDetalleEgresoConAlgunPresupuesto(OperacionEgreso unEgreso) {
        return unEgreso.getPresupuestos().stream().
                anyMatch(p -> p.coincidenSolicitud(unEgreso));
    }

    @Override
    public String resultado(OperacionEgreso unEgreso) {
        if (this.validaEgreso(unEgreso)) {
            return "Solicitud Coinciden Con Presupuesto: Valida";
        } else {
            return "Solicitud Coinciden Con Presupuesto: Invalida";
        }

    }

}
