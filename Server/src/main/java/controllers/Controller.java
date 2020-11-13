package controllers;

import domain.Entidad.Entidad;
import domain.Entidad.Organizacion;
import domain.Usuario.Usuario;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.Request;

public abstract class Controller {
    public  Usuario getUsuarioFromRequest(Request request){
        Repositorio<Usuario> repositorioDeUsuarios = FactoryRepo.get(Usuario.class);
        Integer idUsuario = request.session().attribute("userId");
        Usuario usuarioLogueado = repositorioDeUsuarios.buscar(idUsuario);

        return usuarioLogueado;
    }

    public Entidad getEntidadFromRequest(Request request){
        return getUsuarioFromRequest(request).getEntidadPertenece();
    }

    public Organizacion getOrganizacionFromRequest(Request request){
        return getEntidadFromRequest(request).getOrganizacion();
    }

}
