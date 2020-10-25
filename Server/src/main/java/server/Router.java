package server;

import APIMercadoLibre.InfoMercadoLibre;
import config.ConfiguracionMercadoLibre;
import controllers.*;
import middleware.AuthMiddleware;
import middleware.sessionManager.SessionManageSessionAttribute;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.CustomHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

import java.io.IOException;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("ifEqual", CustomHelper.ifEqual)
                .build();
    }

    public static void init() throws IOException {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() throws IOException {
        ControllerLogin controllerLogin = new ControllerLogin(new SessionManageSessionAttribute());
        ControllerIndex controllerIndex = new ControllerIndex();
        ControllerEgresos controllerEgresos = new ControllerEgresos();
        ControllerPresupuesto controllerPresupuesto = new ControllerPresupuesto();
        ControllerIngreso controllerIngreso = new ControllerIngreso();
        ControllerMensajes controllerMensajes = new ControllerMensajes();

        ApiRest apiRest = new ApiRest();

        if(ConfiguracionMercadoLibre.usarApi){
            InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
        }


        AuthMiddleware authMiddleware = new AuthMiddleware(new SessionManageSessionAttribute());

        Spark.before("*", authMiddleware::verificarSesion);


        Spark.get("/auth", controllerLogin::inicio, Router.engine);

        Spark.post("/auth", controllerLogin::login);

        Spark.get("/dashboard", controllerIndex::mostrarIndice, Router.engine);

        Spark.get("/logout", controllerLogin::logout);

        Spark.get("/egreso", controllerEgresos::mostrarEgresos, Router.engine);

        Spark.get("/api/get-lista-de-provincias/:nombrePais", controllerEgresos::pasarProvincias);

        Spark.get("/api/get-lista-de-ciudades/:nombreProvincia", controllerEgresos::pasarCiudades);

        Spark.get("/ingreso", controllerIngreso::mostrarIngresos, Router.engine);

        Spark.get("/presupuesto", controllerPresupuesto::mostrarPresupuestos, Router.engine);

        Spark.get("/api/usuario/:id", apiRest::mostrarUsuario);

        Spark.get("/ingresoAsociado", controllerIngreso::getIngresoAsociado);

        Spark.get("/mensajes", controllerMensajes::mostrarMensajes, Router.engine);

        Spark.get("/getMensajes", apiRest::mostrarMensajes);

    }
}
