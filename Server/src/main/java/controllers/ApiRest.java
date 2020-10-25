package controllers;

import com.google.gson.Gson;
import domain.Entidad.Usuario.Mensaje;
import domain.Entidad.Usuario.Usuario;
import repositorios.RepositorioDeUsuarios;
import repositorios.TestUsuariosEnMemoria;
import repositorios.daos.DAOMemoria;
import repositorios.factories.FactoryRepoUsuario;
import spark.Request;
import spark.Response;

import java.util.List;

public class ApiRest {
    public String mostrarUsuario(Request request, Response response) {
        Usuario usuarioAMostrar = new RepositorioDeUsuarios(
                new DAOMemoria<>(TestUsuariosEnMemoria.generarUsuariosDePrueba())
        ).buscar(new Integer(request.params("id")));
        Gson gson = new Gson();
        String jUsuario = gson.toJson(usuarioAMostrar);
        response.type("application/json");
        return jUsuario;
    }



    public String mostrarMensajes(Request request, Response response){
        RepositorioDeUsuarios repositorioDeUsuarios = FactoryRepoUsuario.get();
        Usuario usuarioLogueado = repositorioDeUsuarios.buscar(request.session().attribute("userId"));
        Gson gson = new Gson();
        List<Mensaje> listaMensajes = usuarioLogueado.getBandejaDeMensajes().getMensajes();
        String jMensajes = gson.toJson(listaMensajes);
        response.type("application/json");
        return jMensajes;
    }
}
