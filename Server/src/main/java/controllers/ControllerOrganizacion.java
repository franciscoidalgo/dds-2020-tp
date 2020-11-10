package controllers;

import Persistencia.TypeAdapterHibernate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Entidad.*;
import domain.Usuario.RolAdministrador;
import domain.Usuario.Usuario;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.Request;
import spark.Response;

public class ControllerOrganizacion {

    public String cambiarNombre(Request request, Response response) throws Exception {
        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        Organizacion organizacion = usuario.getEntidadPertenece().getOrganizacion();
        String nombreNuevo = request.body();

        if (usuario.getRol() instanceof RolAdministrador) {
            organizacion.setNombre(nombreNuevo);
            FactoryRepo.get(Organizacion.class).modificar(organizacion);
            response.status(200);
            response.type("application/json");
            return "{\"mensaje\":\"ok\"}";
        } else {
            response.status(400);
            response.type("application/json");
            return "{\"mensaje\":\"No se pudo cambiar el nombre\"}";
        }
    }

}
