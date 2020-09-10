package controllers;

import domain.Entidad.Entidad;
import domain.Entidad.Usuario.Usuario;
import passwordHasher.PasswordHasher;
import repositorios.RepositorioDeUsuarios;
import repositorios.TestUsuariosEnMemoria;
import repositorios.daos.DAOMemoria;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerLogin {

    public ModelAndView inicio (Request request, Response response){
        Map<String, Object> parametros= new HashMap<>();
        return new ModelAndView(parametros, "auth.hbs");
    }


    public Response login (Request request, Response response){
        try{
            RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios(
                    new DAOMemoria<>(TestUsuariosEnMemoria.generarUsuariosDePrueba())
            );
            String nombreDeUsuario = request.queryParams("usuario");
            String contrasenia = PasswordHasher.hash(request.queryParams("password"));
            System.out.println(repositorioDeUsuarios.buscarUsuario("Francisco Idalgo", contrasenia));
            if(repositorioDeUsuarios.verificarExistencia(nombreDeUsuario, contrasenia)){
                Usuario usuario = repositorioDeUsuarios.buscarUsuario(nombreDeUsuario, contrasenia);

                request.session(true);
                request.session().attribute("id", usuario.getId());

                response.redirect("/dashboard");
            }else{
                response.redirect("/auth");
            }

        } catch (Exception e) {
            response.redirect("/auth");
        }
        finally {
            return response;
        }
    }
}
