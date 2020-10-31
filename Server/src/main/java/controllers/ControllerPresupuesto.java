package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import com.google.gson.*;
import config.ConfiguracionMercadoLibre;
import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;
import domain.Factories.FactoryDetalle;
import domain.Operacion.Egreso.*;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerPresupuesto {
    public ModelAndView mostrarPresupuestos(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);

        Repositorio<Proveedor> repoProveedores = FactoryRepo.get(Proveedor.class);
        Repositorio<TipoDeItem> repoTipoItem = FactoryRepo.get(TipoDeItem.class);
        Repositorio<TipoComprobante> repoTipoComprobante = FactoryRepo.get(TipoComprobante.class);
        Repositorio<TipoDePago> repoTipoDePago = FactoryRepo.get(TipoDePago.class);
        Repositorio<CategoriaOperacion> repoCategorias = FactoryRepo.get(CategoriaOperacion.class);

        List<OperacionEgreso> egresosSinVincular =  repoEgreso.buscarTodos().stream().filter(egreso -> egreso.getCantPresupuestos() > egreso.getPresupuestos().size()).collect(Collectors.toList());
        if(ConfiguracionMercadoLibre.usarApi){
            InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
            parametros.put("paises", infoMercadoLibre.getListaDePaises());
            parametros.put("monedas", infoMercadoLibre.getListaDeMonedas());
        }


        parametros.put("egreso", false);
        parametros.put("proveedores", repoProveedores.buscarTodos());
        parametros.put("tipoItems", repoTipoItem.buscarTodos());
        parametros.put("tipoComprobante", repoTipoComprobante.buscarTodos());
        parametros.put("tipoPago", repoTipoDePago.buscarTodos());
        parametros.put("categorias", repoCategorias.buscarTodos());//TODO TOCAR PARA QUE SEA DE LA ORGANIZACION
        parametros.put("hoy", LocalDate.now());

        parametros.put("egresos", egresosSinVincular);
        return new ModelAndView(parametros, "egreso.hbs");
    }

    public Response submitPresupuesto(Request request,Response response) throws Exception {
        Repositorio<OperacionEgreso> operacionEgresoRepositorio = FactoryRepo.get(OperacionEgreso.class);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        try{
            Integer idEgreso = rootObject.get("idEgresos").getAsInt();
            DetalleOperacion detalleOperacion = FactoryDetalle.get(request);
            Presupuesto presupuesto = new Presupuesto();
            presupuesto.setDetalle(detalleOperacion);
            presupuesto.setMontoTotal(presupuesto.montoTotal());
            OperacionEgreso operacionEgreso = operacionEgresoRepositorio.buscar(idEgreso);
            operacionEgreso.agregaPresupuesto(presupuesto);
            operacionEgresoRepositorio.modificar(operacionEgreso);
        response.status(200);
        }catch (Exception e){
            response.status(404);
            response.body("No se pudo cargar");
            response.type("application/json");
        }
      return response;
    }

}
