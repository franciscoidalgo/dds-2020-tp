package server;

import controllers.ControllerEgresos;
import controllers.ControllerIndex;
import controllers.ControllerLogin;
import controllers.ControllerPresupuesto;
import middleware.AuthMiddleware;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure(){
        ControllerLogin controllerLogin = new ControllerLogin();
        ControllerIndex controllerIndex = new ControllerIndex();
        ControllerEgresos controllerEgresos = new ControllerEgresos();
        ControllerPresupuesto controllerPresupuesto = new ControllerPresupuesto();

        AuthMiddleware authMiddleware = new AuthMiddleware();

        Spark.before("/", authMiddleware::verificarSesion);

        Spark.get("/auth", controllerLogin::inicio, Router.engine);

        Spark.post("/auth", controllerLogin::login);

        Spark.before("/dashboard", authMiddleware::ingresoConSesionIniciada);

        Spark.get("/dashboard", controllerIndex::mostrarIndice, Router.engine);

        Spark.before("/egreso", authMiddleware::ingresoConSesionIniciada);

        Spark.get("/egreso", controllerEgresos::mostrarEgresos, Router.engine);

        Spark.before("/ingreso", authMiddleware::ingresoConSesionIniciada);

        Spark.get("/ingreso", controllerPresupuesto::mostrarIngresos, Router.engine);

    }
}
