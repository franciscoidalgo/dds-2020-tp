package controllers;

import com.google.gson.Gson;
import domain.Entidad.Usuario.BandejaMensaje;
import domain.Entidad.Usuario.Mensaje;
import domain.Entidad.Usuario.Usuario;
import repositorios.RepositorioBandejasDeMensajes;
import repositorios.RepositorioDeUsuarios;
import repositorios.TestUsuariosEnMemoria;
import repositorios.daos.DAOMemoria;
import repositorios.factories.FactoryRepoBandeja;
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
        RepositorioBandejasDeMensajes repositorioBandejasDeMensajes = FactoryRepoBandeja.get();
        Gson gson = new Gson();
        List<Mensaje> listaMensajes;
        try{
            listaMensajes =
                    repositorioBandejasDeMensajes.buscarBandejaDeUsuario(request.session().attribute("userId"))
                            .getMensajes();
        }catch(Exception e){
            RepositorioDeUsuarios repositorioDeUsuarios = FactoryRepoUsuario.get();
            BandejaMensaje bandejaDelUsuario = new BandejaMensaje();
            bandejaDelUsuario.setUsuario(repositorioDeUsuarios.buscar(request.session().attribute("userId")));
            bandejaDelUsuario.getMensajes().add(new Mensaje("Bienvenida", "¡Bienvenid@ al sistema de gestion Gesoc!"));
            listaMensajes = bandejaDelUsuario.getMensajes();
            repositorioBandejasDeMensajes.agregar(bandejaDelUsuario);
        }
        String jMensajes = gson.toJson(listaMensajes);
        response.type("application/json");
        return jMensajes;
    }
}
