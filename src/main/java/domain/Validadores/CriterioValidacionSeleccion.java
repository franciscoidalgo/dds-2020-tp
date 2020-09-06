package domain.Validadores;

import domain.Operacion.Egreso.OperacionEgreso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CriterioValidacionSeleccion implements CriterioValidacion {
    @Override
    public Boolean validaEgreso(OperacionEgreso unEgreso) {
        return !this.requierePresupuesto(unEgreso) || this.seleccionoProveedoresMasBaratos(unEgreso);
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

    private Boolean seleccionoProveedoresMasBaratos(OperacionEgreso unEgreso) {
        return unEgreso.getPresupuestos().stream().
                filter(p -> p.montoTotal() == this.menorPresupuestoSegun(unEgreso))
                .anyMatch(presupuesto -> presupuesto.coincideProveedor(unEgreso));
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
