package controllers;

import domain.Operacion.CategorizacionOperacion.Criterio;
import domain.Entidad.Entidad;
import domain.Usuario.Usuario;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerBusquedaOperacion {
    public ModelAndView mostrarBusquedaOperacion(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = FactoryRepoUsuario.get().buscar(request.session().attribute("userId"));
        Entidad entidad = usuario.getEntidadPertenece();

        List<Criterio> criterios = entidad.getCriterios();

        parametros.put("criterios",criterios);
        return new ModelAndView(parametros, "busquedaOperacion.hbs");
    }
}
