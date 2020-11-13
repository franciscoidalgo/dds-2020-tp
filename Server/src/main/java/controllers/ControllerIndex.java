package controllers;

import domain.Entidad.Entidad;
import domain.Entidad.Organizacion;
import domain.Usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerIndex extends Controller {


    public ControllerIndex() {

    }

    public ModelAndView mostrarIndice(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = getUsuarioFromRequest(request);
        Entidad entidadSeleccionada = getEntidadFromRequest(request);
        Organizacion organizacion = getOrganizacionFromRequest(request);
        List<Entidad> entidadList = organizacion.getEntidades().stream()
                .filter(entidad -> entidad != entidadSeleccionada)
                .collect(Collectors.toList());

        parametros.put("usuario", usuario);
        parametros.put("idSeleccionada", entidadSeleccionada.getId());
        parametros.put("nombreSeleccionada", entidadSeleccionada.nombre());
        parametros.put("entidades", entidadList);
        return new ModelAndView(parametros, "dashboard.hbs");

    }

}
