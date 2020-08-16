package Validadores;

import Operacion.Egreso.OperacionEgreso;
import Operacion.Egreso.Presupuesto;
import Operacion.Egreso.Proveedor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ValidarCriterioSeleccion implements CriterioValidacion {
    @Override
    public Boolean validaEgreso(OperacionEgreso unEgreso) {
        return !this.requierePresupuesto(unEgreso) || this.buscaProveedoresMasBaratos(unEgreso).contains(unEgreso.getProveedor());
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

    private List<Proveedor> buscaProveedoresMasBaratos(OperacionEgreso unEgreso) {
        return unEgreso.getPresupuestos().stream().
                filter(p -> p.montoTotal() == this.menorPresupuestoSegun(unEgreso))
                .map(Presupuesto::getProveedor).collect(Collectors.toList());
    }

    private double menorPresupuestoSegun(OperacionEgreso unEgreso) {
        return unEgreso.getPresupuestos().stream().
                reduce((p, p2) -> p.montoTotal() <= p2.montoTotal() ? p : p2).get().montoTotal();
    }

    @Override
    public String resultado(OperacionEgreso unEgreso) {
        if (this.validaEgreso(unEgreso)) {
            return "Seleccion Presupuesto: Valida";
        } else {
            return "Seleccion Presupuesto: Invalida";
        }

    }
}
