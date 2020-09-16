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
            response.redirect("/dashboard");
        }
        return response;
    }
}
