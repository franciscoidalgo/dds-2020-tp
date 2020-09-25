package componenteVinculador;

import middleware.AuthMiddleware;
import middleware.sessionManager.SessionManageSessionAttribute;
import spark.Spark;

public class RouterVinculacion {

    public static void init() {
        RouterVinculacion.configure();
    }

    private static void configure() {
        AuthMiddleware authMiddleware = new AuthMiddleware(new SessionManageSessionAttribute());
        ControllerVinculacion controllerVinculacion = new ControllerVinculacion();
        Spark.get("/api/vinculacion", controllerVinculacion::vincular);
    }
}
