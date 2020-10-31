package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import APIMercadoLibre.modelos.Ciudad;
import APIMercadoLibre.modelos.Provincia;
import Persistencia.TypeAdapterHibernate;
import com.google.gson.*;
import config.ConfiguracionMercadoLibre;
import domain.DireccionPostal.DireccionPostal;
import domain.Entidad.CategorizacionEmpresa.Categoria;
import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;
import domain.Entidad.Usuario.Usuario;
import domain.Operacion.Egreso.*;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Operacion.Operacion;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerEgresos {

    public ModelAndView mostrarEgresos(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);
        Repositorio<Proveedor> repoProveedores = FactoryRepo.get(Proveedor.class);
        Repositorio<TipoDeItem> repoTipoItem = FactoryRepo.get(TipoDeItem.class);
        Repositorio<TipoComprobante> repoTipoComprobante = FactoryRepo.get(TipoComprobante.class);
        Repositorio<TipoDePago> repoTipoDePago = FactoryRepo.get(TipoDePago.class);
        Repositorio<CategoriaOperacion> repoCategorias = FactoryRepo.get(CategoriaOperacion.class);

        if(ConfiguracionMercadoLibre.usarApi){
            InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
            parametros.put("paises", infoMercadoLibre.getListaDePaises());
            parametros.put("monedas", infoMercadoLibre.getListaDeMonedas());
        }

        parametros.put("egresos", repoEgreso.buscarTodos());//todo chequear que este al dope

        parametros.put("egreso", true);
        parametros.put("proveedores", repoProveedores.buscarTodos());
        parametros.put("tipoItems", repoTipoItem.buscarTodos());
        parametros.put("tipoComprobante", repoTipoComprobante.buscarTodos());
        parametros.put("tipoPago", repoTipoDePago.buscarTodos());
        parametros.put("categorias", repoCategorias.buscarTodos());
        parametros.put("hoy", LocalDate.now());


        return new ModelAndView(parametros, "egreso.hbs");
    }

    public String pasarProvincias(Request request, Response response) throws IOException {
        List<Provincia> listaProvincias = InfoMercadoLibre.instancia().getListaDeProvincias().stream().filter(
                provincia -> provincia.getCountry().name.equals(request.params("nombrePais"))).collect(Collectors.toList());
        Gson gson = new Gson();
        String jProvincias = gson.toJson(listaProvincias);
        response.type("application/json");
        return jProvincias;
    }

    public String pasarCiudades(Request request, Response response) throws IOException {
        List<Ciudad> listaCiudades = Arrays.asList(InfoMercadoLibre.instancia().getListaDeProvincias().stream().filter(
                provincia -> provincia.getNombre().equals(request.params("nombreProvincia"))).findFirst().get().cities);
        Gson gson = new Gson();
        String jCiudades = gson.toJson(listaCiudades);
        response.type("application/json");
        return jCiudades;
    }

    public Response submitEgreso(Request request,Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);
        RepositorioDeUsuarios repoUsuarios = FactoryRepoUsuario.get();
        String json;

        System.out.println("Me llego una request de post");
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(request.body());
        String prettyJsonString = gson.toJson(je);

        System.out.println(prettyJsonString);

        try {
            //TODO USARLO PARA PEGARLE A LA ENTIDAD
            //Usuario usuarioLogueado = repoUsuarios.buscar(request.session().attribute("userId"));
            //usuarioLogueado.getEntidadPertenece().realizaOperacion(OperacionEgreso);

            OperacionEgreso operacionEgreso = buildearOperacionEgreso(request);

            repoEgreso.agregar(operacionEgreso);

            response.status(200);
       }catch (Exception e){

            response.status(404);
       }




        return response;
    }


    public Proveedor buildearProveedor(Request request){
        DireccionPostal direccionPostal;
        Repositorio<Proveedor> proveedorRepositorio = FactoryRepo.get(Proveedor.class);


        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.body());//Parseando la request

        JsonObject rootObject = jsonElement.getAsJsonObject();// pasando a objeto
        JsonObject jProveedor = rootObject.getAsJsonObject("proveedor");//busco en propiedad Proveedor

       Integer idProveedor = jProveedor.get("id").getAsInt();

        System.out.println(idProveedor);
        Proveedor proveedor = proveedorRepositorio.buscar(idProveedor);

        if(proveedor == null || idProveedor<0){
            proveedor = new Proveedor();
            direccionPostal = new DireccionPostal();
            proveedor.setCUIT(jProveedor.get("cuit").getAsInt());
            proveedor.setRazonSocial(jProveedor.get("razonSocial").getAsString());
            proveedor.setRazonSocial(jProveedor.get("razonSocial").getAsString());

            proveedor.setDirPostal(direccionPostal);

            direccionPostal.setPais(jProveedor.get("pais").getAsString());
            direccionPostal.setProvincia(jProveedor.get("provincia").getAsString());
            direccionPostal.setCiudad(jProveedor.get("ciudad").getAsString());
            direccionPostal.setAltura(jProveedor.get("altura").getAsInt());
            direccionPostal.setCalle(jProveedor.get("calle").getAsString());
            direccionPostal.setPiso(jProveedor.get("piso").getAsString());
            direccionPostal.setDpto(jProveedor.get("dpto").getAsString());

            proveedorRepositorio.agregar(proveedor);
        }
        return  proveedor;
    }

    public List<Pedido> buildearPedido(Request request){
        Repositorio<TipoDeItem> tipoDeItemRepositorio = FactoryRepo.get(TipoDeItem.class);
        List<Pedido> pedidos = new ArrayList<>();
        JsonParser parser = new JsonParser();

        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonArray jPedido = rootObject.getAsJsonArray("pedido");

        for (JsonElement columnElement : jPedido) {
            Pedido pedido = new Pedido();
            JsonObject field = columnElement.getAsJsonObject();
            TipoDeItem tipoDeItem = tipoDeItemRepositorio.buscar(field.get("idTipo").getAsInt());

            Item item = new Item();
            item.setDescripcion(field.get("nombre").getAsString());
            item.setPrecioUnitario(field.get("precioUnitario").getAsFloat());
            item.setTipoDeItem(tipoDeItem);

            pedido.setCantidad(field.get("cantidad").getAsInt());
            pedido.setItem(item);
            pedidos.add(pedido);
        }

      return pedidos;
    }
    public DetalleOperacion buildearDetalleOperacion(Request request){
        DetalleOperacion detalleOperacion = new DetalleOperacion();

        List<Pedido> pedidos = buildearPedido(request);
        Proveedor proveedor = buildearProveedor(request);
        Comprobante comprobante = buildearComprobante(request);

        detalleOperacion.setProveedor(proveedor);
        detalleOperacion.setCategoriaOperacion(buildearCategorias(request));
        detalleOperacion.setComprobante(comprobante);
        detalleOperacion.setPedidos(pedidos);

        return  detalleOperacion;
    }

    public MedioDePago buildearMedioDePago(Request request){
        Repositorio<TipoDePago> tipoDePagoRepositorio = FactoryRepo.get(TipoDePago.class);

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonObject jMedioDePago = rootObject.getAsJsonObject("medioDePago");

        MedioDePago medioDePago = new MedioDePago();
        TipoDePago tipoDePago = tipoDePagoRepositorio.buscar(jMedioDePago.get("idTipoDePago").getAsInt());

        medioDePago.setMoneda(jMedioDePago.get("idTipoDePago").getAsString());
        //medioDePago.setNombre(); //TODO CHEQUEAR ESTO
        medioDePago.setTipoDePago(tipoDePago);

        return  medioDePago;
    }

    public Comprobante buildearComprobante(Request request){
        Repositorio<TipoComprobante> tipoComprobanteRepositorio = FactoryRepo.get(TipoComprobante.class);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonObject jComprobante = rootObject.getAsJsonObject("comprobante");
        Comprobante comprobante = new Comprobante();

        TipoComprobante tipoComprobante = tipoComprobanteRepositorio.buscar(jComprobante.get("tipoComprobante").getAsInt());

        comprobante.setTipoComprobante(tipoComprobante);
        comprobante.setPath(jComprobante.get("path").getAsString());//TODO CAMBIAR PARA QUE LLEGUE DESDE EL SERVER Y NOSOTROS LE PONGAMOS LA RUTA QUE QUEREMOs

        return  comprobante;
    }
    public List<CategoriaOperacion> buildearCategorias(Request request){
        Repositorio<CategoriaOperacion> categoriaRepositorio = FactoryRepo.get(CategoriaOperacion.class);//TODO TOCAR PARA QUE SEA DE LA ORGANIZACION
        List<CategoriaOperacion> categorias = new ArrayList<>();
        JsonParser parser = new JsonParser();

        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonArray jCategorias = rootObject.getAsJsonArray("idCategorias");

        for (JsonElement columnElement : jCategorias) {

            JsonObject field = columnElement.getAsJsonObject();
            CategoriaOperacion  categoriaOperacion = categoriaRepositorio.buscar(field.get("id").getAsInt());
            categorias.add(categoriaOperacion);
        }

        return categorias;
    }

    public OperacionEgreso buildearOperacionEgreso(Request request){
        JsonParser parser = new JsonParser();
        OperacionEgreso operacionEgreso = new OperacionEgreso();

        //TODO: BUSCAR AL USUARIO QUE LO HIZO Y PEGARLE A SU ENTIDAD JURIDICA
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();

        LocalDate fecha = LocalDate.parse(rootObject.get("fecha").getAsString());
        Integer cantPresupuestos = rootObject.get("cantPresupuestos").getAsInt();

        DetalleOperacion detalleOperacion = buildearDetalleOperacion(request);
        MedioDePago medioDePago = buildearMedioDePago(request);

        System.out.println("Por asociar detalle");
        operacionEgreso.setDetalle(detalleOperacion);
        System.out.println("Por asociar cantPresupuesto");
        operacionEgreso.setCantPresupuestos(cantPresupuestos);
        System.out.println("Por asociar mediodepago");
        operacionEgreso.setMedioDePago(medioDePago);
        System.out.println("Por asociar fecha");
        operacionEgreso.setFecha(fecha);
        System.out.println("Por asociar montototal");
        operacionEgreso.setMontoTotal(5000);//TODO CAMBIAR ESTO


        return  operacionEgreso;
    }
}
