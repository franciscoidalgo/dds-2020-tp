package server;

import controllers.*;
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
        ControllerIngreso controllerIngreso = new ControllerIngreso();
        PruebaRest pruebaRest = new PruebaRest();

        AuthMiddleware authMiddleware = new AuthMiddleware();

        Spark.before("/", authMiddleware::verificarSesion);

        Spark.before("/auth", authMiddleware::noLogueesDosVeces);

        Spark.get("/auth", controllerLogin::inicio, Router.engine);

        Spark.post("/auth", controllerLogin::login);

        Spark.before("/dashboard", authMiddleware::ingresoConSesionIniciada);

        Spark.get("/dashboard", controllerIndex::mostrarIndice, Router.engine);

        Spark.get("/logout", controllerLogin::logout);

        Spark.before("/egreso", authMiddleware::ingresoConSesionIniciada);

        Spark.get("/egreso", controllerEgresos::mostrarEgresos, Router.engine);

        Spark.before("/ingreso", authMiddleware::ingresoConSesionIniciada);

        Spark.get("/ingreso", controllerIngreso::mostrarIngresos, Router.engine);

        Spark.before("/presupuesto", authMiddleware::ingresoConSesionIniciada);

        Spark.get("/presupuesto", controllerPresupuesto::mostrarPresupuestos, Router.engine);

        Spark.get("/api/usuario/:id", pruebaRest::mostrar);

    }
}
