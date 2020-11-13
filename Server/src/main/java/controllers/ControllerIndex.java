package controllers;

import domain.Entidad.Entidad;
import domain.Entidad.Organizacion;
import domain.Usuario.Usuario;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerIndex {


    public ControllerIndex(){

    }

    public ModelAndView mostrarIndice (Request request, Response response){
        RepositorioDeUsuarios repositorio = FactoryRepoUsuario.get();
        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = repositorio.buscar(request.session().attribute("userId"));
        Entidad entidadSeleccionada = usuario.getEntidadPertenece();
        Organizacion organizacion = entidadSeleccionada.getOrganizacion();

        List<Entidad> entidadList = organizacion.getEntidades().stream()
                    .filter(entidad -> entidad!= entidadSeleccionada)
                        .collect(Collectors.toList());

        parametros.put("usuario", usuario);
        parametros.put("idSeleccionada",entidadSeleccionada.getId());
        parametros.put("nombreSeleccionada",entidadSeleccionada.nombre());
        parametros.put("entidades",entidadList);
        return new ModelAndView(parametros, "dashboard.hbs");

    }

}
