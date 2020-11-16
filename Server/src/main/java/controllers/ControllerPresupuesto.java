package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import com.google.gson.*;
import config.ConfiguracionMercadoLibre;
import domain.Entidad.Entidad;
import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import domain.Factories.FactoryDetalle;
import domain.Operacion.Egreso.*;
import domain.Usuario.Usuario;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerPresupuesto extends Controller {
    public ModelAndView mostrarPresupuestos(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);
        Repositorio<Proveedor> repoProveedores = FactoryRepo.get(Proveedor.class);
        Repositorio<TipoDeItem> repoTipoItem = FactoryRepo.get(TipoDeItem.class);
        Repositorio<TipoComprobante> repoTipoComprobante = FactoryRepo.get(TipoComprobante.class);
        Repositorio<CategoriaOperacion> repoCategorias = FactoryRepo.get(CategoriaOperacion.class);
        Usuario usuario = FactoryRepoUsuario.get().buscar(request.session().attribute("userId"));

        List<OperacionEgreso> egresosSinVincular =  usuario.getEntidadPertenece().getOperacionesEgreso().stream().filter(egreso -> egreso.getCantPresupuestos() > egreso.getPresupuestos().size()).collect(Collectors.toList());
        Entidad entidad = getEntidadFromRequest(request);

        if(ConfiguracionMercadoLibre.usarApi){
            InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
            parametros.put("paises", infoMercadoLibre.getListaDePaises());
        }


        parametros.put("egreso", false);
        parametros.put("proveedores", repoProveedores.buscarTodos());
        parametros.put("tipoItems", repoTipoItem.buscarTodos());
        parametros.put("tipoComprobante", repoTipoComprobante.buscarTodos());
        parametros.put("categorias", entidad.mostrarTodasCategorias());
        parametros.put("hoy", LocalDate.now());

        parametros.put("egresos", egresosSinVincular);
        return new ModelAndView(parametros, "egreso.hbs");
    }

    public String submitPresupuesto(Request request, Response response) throws Exception {
        Repositorio<OperacionEgreso> operacionEgresoRepositorio = FactoryRepo.get(OperacionEgreso.class);
        JsonParser parser = new JsonParser();
        JsonObject mensajeRta = new JsonObject();
        try{
            JsonElement jsonElement = parser.parse(request.body());
            JsonObject rootObject = jsonElement.getAsJsonObject();
            int idEgreso = rootObject.get("idEgreso").getAsInt();
            DetalleOperacion detalleOperacion = FactoryDetalle.get(request);
            Presupuesto presupuesto = new Presupuesto();
            presupuesto.setDetalle(detalleOperacion);
            presupuesto.setMontoTotal(presupuesto.montoTotal());
            OperacionEgreso operacionEgreso = operacionEgresoRepositorio.buscar(idEgreso);
            operacionEgreso.agregaPresupuesto(presupuesto);
            operacionEgresoRepositorio.modificar(operacionEgreso);
            //response
            mensajeRta.addProperty("idEgreso", ""+operacionEgreso.getId());
            response.status(200);
            response.type("application/json");
            return new Gson().toJson(mensajeRta);
        }catch (Exception e){
            response.status(404);
            mensajeRta.addProperty("mensaje", "No se pudo cargar operacion");
            response.type("application/json");
            return new Gson().toJson(mensajeRta);
        }
    }

}
