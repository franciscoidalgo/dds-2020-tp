package controllers;

import domain.Entidad.Entidad;
import domain.Usuario.Usuario;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepoUsuario;
import spark.Request;

public abstract class Controller {
    public  Usuario getUsuarioFromRequest(Request request){
        RepositorioDeUsuarios repositorioDeUsuarios = FactoryRepoUsuario.get();
        Integer idUsuario = request.session().attribute("userId");
        Usuario usuarioLogueado = repositorioDeUsuarios.buscar(idUsuario);

        return usuarioLogueado;
    }

    public Entidad getEntidadFromRequest(Request request){
        Usuario usuario = getUsuarioFromRequest(request);
        return usuario.getEntidadPertenece();
    }

}
