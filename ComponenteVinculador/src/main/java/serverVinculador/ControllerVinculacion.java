package serverVinculador;

import DTOs.DTOOperacionEgreso;
import DTOs.DTOOperacionIngreso;
import Vinculador.VinculadorAutomatico;
import com.google.gson.Gson;
import Criterio.CriterioAsociacion;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerVinculacion {
    public String vincular (Request request, Response response){
        Gson gson = new Gson();
        DTOOperacionIngreso operacionIngreso = gson.fromJson(request.queryParams("ingreso"), DTOOperacionIngreso.class);

        List<DTOOperacionEgreso> egresos = Arrays.asList(gson.fromJson(request.queryParams("listaEgresos"), DTOOperacionEgreso[].class));
        String criterio = request.queryParams("criterio");
        System.out.println("Tengo toda la info");

        operacionIngreso.setEgresos(new ArrayList<>());
        List<DTOOperacionIngreso> operacionIngresos = new ArrayList<>();
        operacionIngresos.add(operacionIngreso);

        System.out.println("SETIE TODO");
        VinculadorAutomatico.vincular(criterio,egresos,operacionIngresos);
        System.out.println("SETIE TODO");
        String jIngreso = gson.toJson(operacionIngreso);
       response.type("application/json");
        return jIngreso;
    }
}