package middleware;

import repositorios.RepositorioDeTokens;
import repositorios.factories.FactoryRepoTokens;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;


public class AuthMiddleware {

    public Response verificarSesion (Request request, Response response){
        if(!this.estaLogueado(request)){
            this.safeRedirect(request, response,"/auth");
        }else if(request.pathInfo().equals("/")||request.pathInfo().equals("/auth")){
            response.redirect("/dashboard");
        }
        return response;
    }

    private boolean estaLogueado(Request request){
        RepositorioDeTokens repositorioDeTokens = FactoryRepoTokens.get();
        if(request.cookie("idUsuario")!=null){
            int idUsuario = Integer.parseInt(request.cookie("idUsuario"));
            try{
                return repositorioDeTokens.existeToken(idUsuario);
            }catch (Exception e){
                return false;
            }

        }else return false;
    }

    private void safeRedirect(Request request,Response response, String path){
        if(!request.pathInfo().equals(path)) response.redirect(path);
    }

}
