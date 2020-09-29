package requiredModels.operacion.ingreso;

import domain.criterio.CriterioAsociacion;
import requiredModels.operacion.Operacion;
import requiredModels.operacion.egreso.OperacionEgreso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OperacionIngreso extends Operacion {

    private double montoTotal;
    private String descripcion;
    private List<OperacionEgreso> egresos;

    //Funcionalidad
    public void agregateEgreso(OperacionEgreso operacionEgreso){ this.egresos.add(operacionEgreso); }

    public void agregarListaDeEgresos(List<OperacionEgreso> egresos, CriterioAsociacion criterioAsociacion){
        List<OperacionEgreso> egresosValidos = egresos.stream()
                .filter(criterioAsociacion.getCriterio())
                .collect(Collectors.toList());
    }

}
