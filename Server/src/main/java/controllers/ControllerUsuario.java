package controllers;

import domain.Entidad.Entidad;
import domain.Entidad.Organizacion;
import domain.Usuario.RolAdministrador;
import domain.Usuario.Usuario;
import repositorios.factories.FactoryRepo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerUsuario {

    public ModelAndView mostrarUsuario(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = FactoryRepo.get(Usuario.class).buscar(request.session().attribute("userId"));
        Entidad entidad = usuario.getEntidadPertenece();
        Organizacion organizacion = entidad.getOrganizacion();
        List<String> nombreEntidades = organizacion.getEntidades().stream().filter(entidad1 -> !entidad1.equals(entidad)).map(Entidad::nombre).collect(Collectors.toList());
        parametros.put("usuario",usuario);
        parametros.put("rol",usuario.getRol() instanceof RolAdministrador);
        parametros.put("entidad",entidad.nombre());
        parametros.put("organizacion",organizacion);
        parametros.put("otrasEntidades",nombreEntidades);

        return new ModelAndView(parametros, "usuario.hbs");
    }

}
