package controllers;

import com.google.gson.Gson;
import domain.Entidad.Usuario.BandejaMensaje;
import domain.Entidad.Usuario.Mensaje;
import repositorios.RepositorioBandejasDeMensajes;
import repositorios.factories.FactoryRepoBandeja;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerMensajes {
    public ModelAndView mostrarMensajes(Request request, Response response) {
        RepositorioBandejasDeMensajes repositorioBandejasDeMensajes = FactoryRepoBandeja.get();
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "bandejaMensaje.hbs");
    }
}
