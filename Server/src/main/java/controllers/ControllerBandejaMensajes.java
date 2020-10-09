package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import config.ConfiguracionMercadoLibre;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerBandejaMensajes {
    public ModelAndView mostrarBandeja(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();

        return new ModelAndView(parametros, "bandejaMensaje.hbs");
    }
}
