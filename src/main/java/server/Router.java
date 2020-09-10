package server;

import controllers.ControllerIndex;
import controllers.ControllerLogin;
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
        AuthMiddleware authMiddleware = new AuthMiddleware();

        //Spark.get("/", controllerLogin::inicio, Router.engine);

        Spark.before("/", authMiddleware::verificarSesion);

        Spark.get("/auth", controllerLogin::inicio, Router.engine);

        Spark.post("/auth", controllerLogin::login);

        Spark.get("/dashboard", controllerIndex::mostrarIndice, Router.engine);

    }
}
