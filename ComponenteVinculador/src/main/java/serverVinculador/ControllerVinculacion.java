package serverVinculador;

import DTOs.DTOOperacionEgreso;
import DTOs.DTOOperacionIngreso;
import com.google.gson.Gson;
import Criterio.CriterioAsociacion;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ControllerVinculacion {
    public String vincular (Request request, Response response){
        Gson gson = new Gson();
        DTOOperacionIngreso operacionIngreso = gson.fromJson(request.queryParams("ingreso"), DTOOperacionIngreso.class);
        List<DTOOperacionEgreso> egresos = Arrays.asList(gson.fromJson(request.queryParams("listaEgresos"), DTOOperacionEgreso[].class));
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