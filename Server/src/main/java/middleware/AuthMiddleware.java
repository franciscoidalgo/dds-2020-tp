package middleware;

import middleware.sessionManager.SessionManageMethod;
import spark.Request;
import spark.Response;



public class AuthMiddleware {

    private final SessionManageMethod sessionManageMethod;

    public AuthMiddleware(SessionManageMethod sessionManageMethod){
        this.sessionManageMethod = sessionManageMethod;
    }

    public Response verificarSesion (Request request, Response response){
        if(!this.sessionManageMethod.estaLogueado(request)){
            this.safeRedirect(request, response,"/auth");
        }else if(request.pathInfo().equals("/")||request.pathInfo().equals("/auth")){
            response.redirect("/dashboard");
        }
        return response;
    }


    private void safeRedirect(Request request,Response response, String path){
        if(!request.pathInfo().equals(path)) response.redirect(path);
    }

}
