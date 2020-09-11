package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ControllerEgresos {

    public ModelAndView mostrarEgresos(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("egreso", true);
        return new ModelAndView(parametros, "egreso.hbs");
    }
}
