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
        }


        parametros.put("egreso", false);
        parametros.put("proveedores", repoProveedores.buscarTodos());
        parametros.put("tipoItems", repoTipoItem.buscarTodos());
        parametros.put("tipoComprobante", repoTipoComprobante.buscarTodos());
        parametros.put("categorias", repoCategorias.buscarTodos());//TODO TOCAR PARA QUE SEA DE LA ORGANIZACION
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
            System.out.println("***********me llego la request el id");
            int idEgreso = rootObject.get("idEgreso").getAsInt();
            System.out.println("***********Tengo el id");
            DetalleOperacion detalleOperacion = FactoryDetalle.get(request);
            System.out.println("***********detalle");
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
