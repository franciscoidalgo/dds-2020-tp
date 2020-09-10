package middleware;

import spark.Request;
import spark.Response;

public class AuthMiddleware {

    public Response verificarSesion(Request request, Response response){
        if(!request.session().isNew()){
            response.redirect("/dashboard");
        }else response.redirect("/auth");
        return response;
    }
}
