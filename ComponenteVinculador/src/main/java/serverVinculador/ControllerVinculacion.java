package serverVinculador;

import com.google.gson.Gson;
import domain.criterio.CriterioAsociacion;
import requiredModels.operacion.egreso.OperacionEgreso;
import requiredModels.operacion.ingreso.OperacionIngreso;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ControllerVinculacion {
    public String vincular (Request request, Response response){
        Gson gson = new Gson();
        OperacionIngreso operacionIngreso = gson.fromJson(request.queryParams("ingreso"), OperacionIngreso.class);
        List<OperacionEgreso> egresos = Arrays.asList(gson.fromJson(request.queryParams("listaEgresos"), OperacionEgreso[].class));
        LocalDate fechaDesde = LocalDate.parse(request.queryParams("fechaDesde"));
        LocalDate fechaHasta = LocalDate.parse(request.queryParams("fechaHasta"));
        CriterioAsociacion criterio = new CriterioAsociacion();
        criterio.generarCriterioConFechas(fechaDesde, fechaHasta);
        operacionIngreso.agregarListaDeEgresos(egresos, criterio);
        String jIngreso = gson.toJson(operacionIngreso);
        System.out.println(jIngreso);
        response.type("application/json");
        return jIngreso;
    }
}
