package middleware;

import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class AuthMiddleware {

    public Response verificarSesion(Request request, Response response){
        if(request.session().attribute("id") != null){
            response.redirect("/dashboard");
        }else response.redirect("/auth");
        return response;
    }

    public Response ingresoConSesionIniciada (Request request, Response response){
        if(request.session().attribute("id") == null){
            response.redirect("/auth");
        }
        return response;
    }

    public Response noLogueesDosVeces (Request request, Response response){
        if(request.session().attribute("id") != null){
            halt(401, "<h1>No se puede acceder: ya se encuentra logueado</h1>" +
                    "<img src='/img/error.jpg'>");
        }
        return response;
    }
}
