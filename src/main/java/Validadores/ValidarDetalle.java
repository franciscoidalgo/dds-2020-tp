package Validadores;

import Operacion.Egreso.Detalle;
import Operacion.Egreso.Item;
import Operacion.Egreso.OperacionEgreso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ValidarDetalle implements CriterioValidacion {
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
                anyMatch(p -> this.coincidenDetalles(unEgreso.getDetalle(), p.getDetalle()));
    }

    private Boolean coincidenDetalles(Detalle unDetalle, Detalle otroDetalle) {
        return unDetalle.getListaItems().stream().
                map(item -> this.perteneceItemADetalle(item, otroDetalle)).
                reduce(Boolean::logicalAnd).get();
    }

    private Boolean perteneceItemADetalle(Item unItem, Detalle unDetalle) {
        return unDetalle.getListaItems().stream().anyMatch(item -> this.coincidenItems(unItem, item));
    }

    private Boolean coincidenItems(Item unItem, Item otroItem) {
        return unItem.getDescripcion().contentEquals(otroItem.getDescripcion()) && unItem.getPrecio() == otroItem.getPrecio();
    }

    @Override
    public String resultado(OperacionEgreso unEgreso) {
        if (this.validaEgreso(unEgreso)) {
            return "Detalle Coinciden Con Presupuesto: Valida";
        } else {
            return "Detalle Coinciden Con Presupuesto: Invalida";
        }

    }

}
