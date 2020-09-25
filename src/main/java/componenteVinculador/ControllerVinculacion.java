package componenteVinculador;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.List;

public class ControllerVinculacion {
    public String vincular (Request request, Response response){
        Gson gson = new Gson();
        OperacionIngreso operacionIngreso = gson.fromJson(request.queryParams("ingreso"), OperacionIngreso.class);
        List<OperacionEgreso> egresos = Arrays.asList(gson.fromJson(request.queryParams("listaEgresos"), OperacionEgreso[].class));
        operacionIngreso.agregarListaDeEgresos(egresos);
        String jIngreso = gson.toJson(operacionIngreso);
        return jIngreso;
    }
}
