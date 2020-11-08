package middleware.sessionManager;

import domain.Usuario.Usuario;
import spark.Request;
import spark.Response;

public interface SessionManageMethod {
    boolean estaLogueado (Request request);
    void login(Request request, Response reponse, Usuario usuario);
    void logout (Request request, Response response);
}
