package controllers;

import middleware.AuthMiddleware;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ControllerIndex {

    public ModelAndView mostrarIndice (Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "dashboard.hbs");

    }

}
