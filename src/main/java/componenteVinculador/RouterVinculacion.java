package componenteVinculador;

import spark.Spark;

public class RouterVinculacion {

    public static void init() {
        RouterVinculacion.configure();
    }

    private static void configure() {
        ControllerVinculacion controllerVinculacion = new ControllerVinculacion();
        Spark.get("/api/vinculacion", controllerVinculacion::vincular);
    }
}
