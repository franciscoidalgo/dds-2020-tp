package server;

import APIMercadoLibre.InfoMercadoLibre;
import Persistencia.EntityManagerHelper;
import config.ConfiguracionMercadoLibre;
import controllers.*;
import domain.Validadores.CriterioValidacionCantidadPresupuesto;
import domain.Validadores.CriterioValidacionDetalle;
import domain.Validadores.CriterioValidacionSeleccion;
import domain.Validadores.ValidadorDeTransparencia;
import middleware.AuthMiddleware;
import middleware.sessionManager.SessionManageSessionAttribute;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.CustomHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

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
        ControllerBusquedaOperacion controllerBusquedaOperacion = new ControllerBusquedaOperacion();
        ControllerPersistance controllerPersistance = new ControllerPersistance();
        ValidadorDeTransparencia validadorDeTransparencia = ValidadorDeTransparencia.instancia();

        validadorDeTransparencia.agregateCriterio(new CriterioValidacionCantidadPresupuesto());
        validadorDeTransparencia.agregateCriterio(new CriterioValidacionDetalle());
        validadorDeTransparencia.agregateCriterio(new CriterioValidacionSeleccion());

        ApiRest apiRest = new ApiRest();

        if(ConfiguracionMercadoLibre.usarApi){
            InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
        }


        AuthMiddleware authMiddleware = new AuthMiddleware(new SessionManageSessionAttribute());

        Spark.before("*",(request, response) -> {
            controllerPersistance.abrirEm(request,response);
            authMiddleware.verificarSesion(request,response);

        });


        Spark.get("/auth", controllerLogin::inicio, Router.engine);

        Spark.post("/auth", controllerLogin::login);

        Spark.get("/dashboard", controllerIndex::mostrarIndice, Router.engine);

        Spark.get("/logout", controllerLogin::logout);

        Spark.get("/egreso", controllerEgresos::mostrarEgresos, Router.engine);

        Spark.post("/egreso", controllerEgresos::submitEgreso);

        Spark.path("/api",() -> {
            Spark.get("/provincias/:nombrePais", controllerEgresos::pasarProvincias);
            Spark.get("/ciudades/:nombreProvincia", controllerEgresos::pasarCiudades);
            Spark.get("/proveedor/:id", apiRest::mostrarProveedores);
            Spark.get("/items/:idTipoItem", apiRest::mostraItemsSegunTipo);

            Spark.path("/ingreso",() -> {
                Spark.get("/todos", apiRest::pasarTodosIngresos);
                Spark.get("/:idIngreso", apiRest::pasarIngresoSegunID);
            });

            Spark.get("/categoria/:idCriterio", apiRest::pasarCategoriasSegunCriterio);

            Spark.path("/egresos",() -> {
                Spark.get("/todos", apiRest::pasarTodosEgresos);
                Spark.post("/segun-categorias", apiRest::pasarEgresosSegunCategorias);
            });

            Spark.path("/revisor",() -> {
                Spark.delete("/delete/:idEgreso", apiRest::sacarRevisor);
                Spark.put("/agregar/:idEgreso", apiRest::agregarRevisor);
            });
        });


        Spark.path("/mensajes",() -> {
            Spark.get("", controllerMensajes::mostrarBandeja, Router.engine);
            Spark.get("/todos", apiRest::mostrarMensajes);
        });



        Spark.get("/api/get-egreso/:idEgreso", apiRest::pasarEgresosSegunID);
        Spark.get("/api/get-egreso/:idEgreso/:idMensaje", apiRest::pasarEgresosMensaje);

        Spark.get("/api/get-egreso-segun-fecha/:fechaMax", apiRest::pasarEgresosSegunFecha);
        Spark.get("/api/get-egresos-vincular/:fechaMax", apiRest::pasarEgresosNoVinculados);






        Spark.get("/ingreso", controllerIngreso::mostrarIngresos, Router.engine);

        Spark.get("/presupuesto", controllerPresupuesto::mostrarPresupuestos, Router.engine);

        Spark.post("/presupuesto", controllerPresupuesto::submitPresupuesto);

        Spark.get("/api/usuario/:id", apiRest::mostrarUsuario);

        Spark.post("/ingreso", controllerIngreso::submitIngreso);

        Spark.get("/busquedaOperacion", controllerBusquedaOperacion::mostrarBusquedaOperacion,Router.engine);
        Spark.afterAfter("*", controllerPersistance::cerrarEm);

    }
}
