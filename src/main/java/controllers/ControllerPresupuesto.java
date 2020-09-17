package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ControllerPresupuesto {
    public ModelAndView mostrarPresupuestos(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "egreso.hbs");
    }
}
