package requiredModels.operacion.ingreso;

import requiredModels.operacion.Operacion;
import requiredModels.operacion.egreso.OperacionEgreso;

import java.util.ArrayList;
import java.util.List;

public class OperacionIngreso extends Operacion {

    private double montoTotal;
    private String descripcion;
    private List<OperacionEgreso> egresos;

    //Funcionalidad
    public void agregateEgreso(OperacionEgreso operacionEgreso){ this.egresos.add(operacionEgreso); }

    public void agregarListaDeEgresos(List<OperacionEgreso> egresos){
        this.egresos.addAll(egresos);
    }

}
