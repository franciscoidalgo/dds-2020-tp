package middleware;

import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class AuthMiddleware {

    public Response verificarSesion (Request request, Response response){
        if(!this.estaLogueado(request)){
            this.safeRedirect(request, response,"/auth");
        }else if(request.pathInfo().equals("/")||request.pathInfo().equals("/auth")){
            this.safeRedirect(request, response, "/dashboard");
        }
        return response;
    }

    private boolean estaLogueado(Request request){
        return request.session().attribute("id") != null;
    }

    private void safeRedirect(Request request,Response response, String path){
        if(!request.pathInfo().equals(path)) response.redirect(path);
    }

}
