package controllers;

import domain.Usuario.Usuario;
import middleware.sessionManager.SessionManageMethod;
import passwordHasher.PasswordHasher;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ControllerLogin {

    private final SessionManageMethod sessionManageMethod;

    public ControllerLogin(SessionManageMethod sessionManageMethod){
        this.sessionManageMethod = sessionManageMethod;
    }

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

                request.session(true);
                request.session().attribute("loginFailed", false);
                sessionManageMethod.login(request, response, usuario);
                response.redirect("/dashboard");

                return response;

            }else{
                request.session().attribute("loginFailed", true);
                response.redirect("/auth");
            }

        } catch (Exception e) {
            System.out.println(e);
            request.session().attribute("loginFailed", true);
            response.redirect("/auth");
        }
        finally {
            return response;
        }
    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        sessionManageMethod.logout(request, response);
        response.redirect("/");
        return response;
    }
}
