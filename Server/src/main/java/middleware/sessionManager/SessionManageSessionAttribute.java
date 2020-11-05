package middleware.sessionManager;

import domain.Usuario.Usuario;
import spark.Request;
import spark.Response;

public class SessionManageSessionAttribute implements SessionManageMethod {
    @Override
    public boolean estaLogueado(Request request) {
        return request.session().attribute("userId") != null;
    }

    @Override
    public void login(Request request, Response response, Usuario usuario) {
        request.session().attribute("userId", usuario.getId());
    }

    @Override
    public void logout(Request request, Response response) {
    }
}
