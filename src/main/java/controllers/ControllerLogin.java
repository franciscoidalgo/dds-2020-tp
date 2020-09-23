package controllers;

import domain.Entidad.Usuario.Usuario;
import middleware.LoginToken;
import passwordHasher.PasswordHasher;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
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
            RepositorioDeUsuarios repositorioDeUsuarios = FactoryRepoUsuario.get();
            String nombreDeUsuario = request.queryParams("usuario");
            String contrasenia = PasswordHasher.hash(request.queryParams("password"));
            if(repositorioDeUsuarios.verificarExistencia(nombreDeUsuario, contrasenia)){
                Usuario usuario = repositorioDeUsuarios.buscarUsuario(nombreDeUsuario, contrasenia);
                int idUsuario = usuario.getId();


                request.session(true);
                /*
                request.session().attribute("id", usuario.getId());
                */
                request.session().attribute("loginFailed", false);

                response.cookie("idUsuario", String.valueOf(usuario.getId()), 86400, false, true);
                LoginToken.generarToken(usuario, request.ip());

                response.redirect("/dashboard");
            }else{
                request.session().attribute("loginFailed", true);
                response.redirect("/auth");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            request.session().attribute("loginFailed", true);
            response.redirect("/auth");
        }
        finally {
            return response;
        }
    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        //RepositorioDeTokens repositorioDeTokens = FactoryRepoTokens.get();
        //LoginToken loginToken = repositorioDeTokens.buscarPorUsuario(Integer.parseInt(request.cookie("authToken")));
        //repositorioDeTokens.eliminar(loginToken);
        response.removeCookie("idUsuario");
        response.redirect("/");
        return response;
    }
}
