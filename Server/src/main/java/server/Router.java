package server;

import APIMercadoLibre.InfoMercadoLibre;
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
        ControllerVinculacion controllerVinculacion = new ControllerVinculacion();
        ControllerOrganizacion controllerOrganizacion = new ControllerOrganizacion();
        ControllerUsuario controllerUsuario = new ControllerUsuario();
        ControllerEntidad controllerEntidad = new ControllerEntidad();
        ControllerNormalizaciones controllerNormalizaciones = new ControllerNormalizaciones();

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

        Spark.path("/egreso",() -> {
            /* ENVIO DE EGRESOS. */
            Spark.get("", controllerEgresos::mostrarEgresos, Router.engine);
            Spark.post("/nuevo", controllerEgresos::submitEgreso);
            Spark.get("/editar/:idEgreso", controllerEgresos::editarEgreso);

            Spark.path("/buscar",() -> {
                Spark.get("/segun-fecha/:fechaMax", controllerEgresos::pasarEgresosSegunFecha);
                Spark.get("/sin-vincular/:fechaMax", controllerEgresos::pasarEgresosNoVinculados);
                Spark.get("/todos", controllerEgresos::pasarTodosEgresos);
                Spark.post("/segun-categorias", controllerEgresos::pasarEgresosSegunCategorias);
                Spark.get("/:idEgreso", controllerEgresos::pasarEgresosSegunID);
                Spark.get("/:idEgreso/:idMensaje", controllerEgresos::pasarEgresosMensaje);
            });

        });

        Spark.path("/revisor",() -> {
            Spark.delete("/delete/:idEgreso", controllerEgresos::sacarRevisor);
            Spark.put("/agregar/:idEgreso", controllerEgresos::agregarRevisor);
        });

        Spark.path("/api",() -> {
            Spark.get("/provincias/:nombrePais", controllerNormalizaciones::pasarProvincias);
            Spark.get("/ciudades/:nombreProvincia", controllerNormalizaciones::pasarCiudades);
            Spark.get("/proveedor/:id", apiRest::mostrarProveedores);
            Spark.get("/items/:idTipoItem", apiRest::mostraItemsSegunTipo);
            Spark.get("/entidades", apiRest::mostraEntidades);

            Spark.path("/ingreso",() -> {
                Spark.get("/todos", apiRest::pasarTodosIngresos);
                Spark.get("/por-vincular", apiRest::pasarIngresoPorVincular);
                Spark.get("/:idIngreso", apiRest::pasarIngresoSegunID);
                Spark.post("/vincular", apiRest::vincularIngresos);
            });

            Spark.get("/categoria/:idCriterio", apiRest::pasarCategoriasSegunCriterio);
        });

        Spark.path("/mensajes",() -> {
            Spark.get("", controllerMensajes::mostrarBandeja, Router.engine);
            Spark.get("/todos", apiRest::mostrarMensajes);
        });

        Spark.path("/usuario",() -> {
            Spark.get("", controllerUsuario::mostrarUsuario, Router.engine);
            Spark.post("/apellido", controllerUsuario::cambiarApellido);
            Spark.post("/nombre", controllerUsuario::cambiarNombre);
            Spark.post("/password", controllerUsuario::cambiarPassword);
            Spark.get("/entidad/:idEntidad", controllerUsuario::cambiarEntidad);
        });

        Spark.path("/organizacion",() -> {
            Spark.delete("/entidad/:id",controllerEntidad::eliminarEntidad);
            Spark.post("/cambiar/nombre",controllerOrganizacion::cambiarNombre);
        });

        Spark.path("/entidad",() -> {
            Spark.get("/nuevo",controllerEntidad::mostrarNuevaEntidad,Router.engine);
        });


        Spark.get("/ingreso", controllerIngreso::mostrarIngresos, Router.engine);

        Spark.get("/vinculacion", controllerVinculacion::mostrarVinculacion, Router.engine);

        Spark.get("/presupuesto", controllerPresupuesto::mostrarPresupuestos, Router.engine);

        Spark.post("/presupuesto", controllerPresupuesto::submitPresupuesto);

        Spark.get("/api/usuario/:id", apiRest::mostrarUsuario);

        Spark.post("/ingreso", controllerIngreso::submitIngreso);

        Spark.get("/busquedaOperacion", controllerBusquedaOperacion::mostrarBusquedaOperacion,Router.engine);

        Spark.post("/imagen-comprobante", controllerEgresos::submitImagen);

        Spark.afterAfter("*", controllerPersistance::cerrarEm);

    }
}
