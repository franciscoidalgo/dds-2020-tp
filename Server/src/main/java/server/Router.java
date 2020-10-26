package server;

import APIMercadoLibre.InfoMercadoLibre;
import config.ConfiguracionMercadoLibre;
import controllers.*;
import domain.Entidad.Usuario.RolAdministrador;
import domain.Entidad.Usuario.Usuario;
import domain.Operacion.Egreso.*;
import domain.Validadores.CriterioValidacionCantidadPresupuesto;
import domain.Validadores.CriterioValidacionDetalle;
import domain.Validadores.CriterioValidacionSeleccion;
import domain.Validadores.ValidadorDeTransparencia;
import middleware.AuthMiddleware;
import middleware.sessionManager.SessionManageSessionAttribute;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.CustomHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("ifEqual", CustomHelper.ifEqual)
                .build();
    }

    public static void init() throws Exception {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() throws Exception {
        ControllerLogin controllerLogin = new ControllerLogin(new SessionManageSessionAttribute());
        ControllerIndex controllerIndex = new ControllerIndex();
        ControllerEgresos controllerEgresos = new ControllerEgresos();
        ControllerPresupuesto controllerPresupuesto = new ControllerPresupuesto();
        ControllerIngreso controllerIngreso = new ControllerIngreso();
        ControllerMensajes controllerMensajes = new ControllerMensajes();
        ValidadorDeTransparencia validadorDeTransparencia = ValidadorDeTransparencia.instancia();

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

        Spark.post("/egreso", controllerEgresos::submitEgreso);

        Spark.get("/api/get-lista-de-provincias/:nombrePais", controllerEgresos::pasarProvincias);

        Spark.get("/api/get-lista-de-ciudades/:nombreProvincia", controllerEgresos::pasarCiudades);

        Spark.get("/ingreso", controllerIngreso::mostrarIngresos, Router.engine);

        Spark.get("/presupuesto", controllerPresupuesto::mostrarPresupuestos, Router.engine);

        Spark.get("/api/usuario/:id", apiRest::mostrarUsuario);

        Spark.post("/ingresoAsociado", controllerIngreso::getIngresoAsociado);

        Spark.post("/ingreso", controllerIngreso::submitIngreso);

        Spark.get("/mensajes", controllerMensajes::mostrarMensajes, Router.engine);

        Spark.get("/getMensajes", apiRest::mostrarMensajes);


    }
}
