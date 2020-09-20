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
        if(request.session().attribute("loginFailed") != null){
            parametros.put("loginFailed", request.session().attribute("loginFailed"));
            request.session().removeAttribute("loginFailed");
        }
        return new ModelAndView(parametros, "auth.hbs");
    }


    public Response login (Request request, Response response){
        try{
            RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios(
                    new DAOMemoria<>(TestUsuariosEnMemoria.generarUsuariosDePrueba())
            );
            String nombreDeUsuario = request.queryParams("usuario");
            String contrasenia = PasswordHasher.hash(request.queryParams("password"));
            if(repositorioDeUsuarios.verificarExistencia(nombreDeUsuario, contrasenia)){
                Usuario usuario = repositorioDeUsuarios.buscarUsuario(nombreDeUsuario, contrasenia);


                request.session(true);
                request.session().attribute("id", usuario.getId());
                request.session().attribute("loginFailed", false);

                //response.cookie("id", String.valueOf(usuario.getId()),86400);

                response.redirect("/dashboard");
            }else{
                request.session().attribute("loginFailed", true);
                response.redirect("/auth");
            }

        } catch (Exception e) {
            request.session().attribute("loginFailed", true);
            response.redirect("/auth");
        }
        finally {
            return response;
        }
    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        //response.removeCookie("id");
        response.redirect("/");
        return response;
    }
}
