package controllers;

import APIAsociadora.ServicioAsociacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerIngreso {
    public ModelAndView mostrarIngresos(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "ingreso.hbs");
    }

    public String getIngresoAsociado(Request request, Response response) throws IOException {
        ServicioAsociacion servicioAsociacion = ServicioAsociacion.getInstancia();
        return servicioAsociacion.getIngresoAsociadoBeta(request.queryParams("ingreso"), request.queryParams("listaEgresos"));
    }
}
