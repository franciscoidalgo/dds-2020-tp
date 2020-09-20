package server;

import APIMercadoLibre.InfoMercadoLibre;
import controllers.*;
import middleware.AuthMiddleware;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.CustomHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

import java.io.IOException;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .withHelper("isEqual", CustomHelper.isEqual)
                .build();
    }

    public static void init() throws IOException {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() throws IOException {
        ControllerLogin controllerLogin = new ControllerLogin();
        ControllerIndex controllerIndex = new ControllerIndex();
        ControllerEgresos controllerEgresos = new ControllerEgresos();
        ControllerPresupuesto controllerPresupuesto = new ControllerPresupuesto();
        ControllerIngreso controllerIngreso = new ControllerIngreso();
        PruebaRest pruebaRest = new PruebaRest();
        InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();

        AuthMiddleware authMiddleware = new AuthMiddleware();

        Spark.before("*", authMiddleware::verificarSesion);


        Spark.get("/auth", controllerLogin::inicio, Router.engine);

        Spark.post("/auth", controllerLogin::login);

        Spark.get("/dashboard", controllerIndex::mostrarIndice, Router.engine);

        Spark.get("/logout", controllerLogin::logout);

        Spark.get("/egreso", controllerEgresos::mostrarEgresos, Router.engine);

        Spark.get("/ingreso", controllerIngreso::mostrarIngresos, Router.engine);

        Spark.get("/presupuesto", controllerPresupuesto::mostrarPresupuestos, Router.engine);

        Spark.get("/api/usuario/:id", pruebaRest::mostrar);

    }
}
