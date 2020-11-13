package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import Persistencia.TypeAdapterHibernate;
import com.google.gson.*;
import config.ConfiguracionMercadoLibre;
import controllers.DTO.EgresoDTO;
import domain.Entidad.Entidad;
import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import domain.Usuario.BandejaMensaje.Mensaje;
import domain.Usuario.Usuario;
import domain.Factories.FactoryEgreso;
import domain.Operacion.Egreso.*;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static controllers.convertersDTO.ConverterEgreso.generarEgresoDTO;

public class ControllerEgresos extends Controller{

    public ModelAndView mostrarEgresos(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);
        Repositorio<Proveedor> repoProveedores = FactoryRepo.get(Proveedor.class);
        Repositorio<TipoDeItem> repoTipoItem = FactoryRepo.get(TipoDeItem.class);
        Repositorio<TipoComprobante> repoTipoComprobante = FactoryRepo.get(TipoComprobante.class);
        Repositorio<TipoDePago> repoTipoDePago = FactoryRepo.get(TipoDePago.class);
        Repositorio<CategoriaOperacion> repoCategorias = FactoryRepo.get(CategoriaOperacion.class);

        if (ConfiguracionMercadoLibre.usarApi) {
            InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
            parametros.put("paises", infoMercadoLibre.getListaDePaises());
            parametros.put("monedas", infoMercadoLibre.getListaDeMonedas());
        }

        parametros.put("egresos", repoEgreso.buscarTodos());
        parametros.put("egreso", true);
        parametros.put("proveedores", repoProveedores.buscarTodos());
        parametros.put("tipoItems", repoTipoItem.buscarTodos());
        parametros.put("tipoComprobante", repoTipoComprobante.buscarTodos());
        parametros.put("tipoPago", repoTipoDePago.buscarTodos());
        parametros.put("categorias", repoCategorias.buscarTodos());//TODO TOCAR PARA QUE SEA DE LA ORGANIZACION
        parametros.put("hoy", LocalDate.now());

        parametros.put("numeroEgreso",request.session().attribute("idEgreso"));

        return new ModelAndView(parametros, "egreso.hbs");
    }

    public String submitEgreso(Request request, Response response) {
        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);
        JsonObject mensajeRta = new JsonObject();
        Usuario usuarioLogueado;

        try {

            OperacionEgreso operacionEgreso = FactoryEgreso.get(request);

            /*********************** EDICION **********************/
            Integer idEgreso =request.session().attribute("idEgreso");
            request.session().removeAttribute("idEgreso");
            if(null != idEgreso){//Si tiene ese valor en el session => Edita
                OperacionEgreso operacionEgresoEdit = FactoryRepo.get(OperacionEgreso.class).buscar(idEgreso);
                if(puedeEditarse(operacionEgresoEdit,operacionEgreso)) {
                    operacionEgresoEdit.setMedioDePago(operacionEgreso.getMedioDePago());
                    operacionEgresoEdit.setDetalle(operacionEgreso.getDetalle());
                    operacionEgresoEdit.setCantPresupuestos(operacionEgreso.getCantPresupuestos());

                    operacionEgresoEdit.setFecha(operacionEgreso.getFecha());
                    operacionEgresoEdit.setMontoTotal(operacionEgreso.getMontoTotal());
                    repoEgreso.modificar(operacionEgresoEdit);

                    mensajeRta.addProperty("idEgreso", "" + operacionEgresoEdit.getId());
                    response.status(200);
                    response.type("application/json");

                    return new Gson().toJson(mensajeRta);
                }else{
                    mensajeRta.addProperty("idEgreso", "" + operacionEgresoEdit.getId());
                    response.status(400);
                    response.type("application/json");

                    return new Gson().toJson(mensajeRta);
                }
            }



            usuarioLogueado = getUsuarioFromRequest(request);
            usuarioLogueado.realizaOperacion(operacionEgreso);
            usuarioLogueado.darseDeAltaEn(operacionEgreso);
            repoEgreso.agregar(operacionEgreso);
            request.session().attribute("egreso_actual", operacionEgreso.getId());

            //Response TODO GENERALIZAR ESTO
            mensajeRta.addProperty("idEgreso", "" + operacionEgreso.getId());
            response.status(200);
            response.type("application/json");
            return new Gson().toJson(mensajeRta);

        } catch (Exception e) {
            response.status(404);
            mensajeRta.addProperty("mensaje", "No se pudo cargar operacion");
            response.type("application/json");
            return new Gson().toJson(mensajeRta);
        }

    }

    public String editarEgreso(Request request,Response response) throws  IOException{
        Integer idEgreso = Integer.parseInt(request.params("idEgreso"));
        JsonObject mensajeRta = new JsonObject();
        request.session().attribute("idEgreso",idEgreso);
        mensajeRta.addProperty("idEgreso", "");
        response.status(200);
        response.type("application/json");
        return new Gson().toJson(mensajeRta);
    }

    //TODO REFACTORIZAR ESTO

    public String pasarEgresosSegunID(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Repositorio<OperacionEgreso> repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);
        int idEgreso;
        OperacionEgreso egreso;
        EgresoDTO egresoDTO;
        String jsonEgreso;

        idEgreso = Integer.parseInt(request.params("idEgreso"));
        egreso = repositorioEgreso.buscar(idEgreso);
        egresoDTO = generarEgresoDTO(egreso);

        jsonEgreso = gson.toJson(egresoDTO);
        response.type("application/json");

        return jsonEgreso;
    }

    public String pasarEgresosSegunCategorias(Request request, Response response) {

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        Entidad entidad = this.getEntidadFromRequest(request);
        List<OperacionEgreso> operacionEgresos = entidad.getOperacionesEgreso();
        List<CategoriaOperacion> categoriasSeleccionadas = new ArrayList<>();
        List<EgresoDTO> egresoDTOList;
        String jsonEgreso;
        //Trato el JSON
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonArray jCategorias= rootObject.getAsJsonArray("idCategorias");

        //Agrego a la lista las categorias
        for (JsonElement columnElement : jCategorias) {
            int idCategoria = columnElement.getAsInt();
            CategoriaOperacion categoriaOperacion = FactoryRepo.get(CategoriaOperacion.class).buscar(idCategoria);
            categoriasSeleccionadas.add(categoriaOperacion);
        }
        //Filtro los que matchean
        operacionEgresos = operacionEgresos.stream()
                .filter(operacionEgreso -> categoriasSeleccionadas.stream()
                        .allMatch(operacionEgreso::tenesCategoria))
                .collect(Collectors.toList());

        egresoDTOList = convertirEgresosEnEgresosDTO(operacionEgresos);

        jsonEgreso = gson.toJson(egresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }

    public String pasarTodosEgresos(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Entidad entidad = this.getEntidadFromRequest(request);
        List<EgresoDTO> egresoDTOList;
        String jsonEgreso;

        egresoDTOList = convertirEgresosEnEgresosDTO(entidad.getOperacionesEgreso());

        jsonEgreso = gson.toJson(egresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }

    public String pasarEgresosMensaje(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Repositorio<OperacionEgreso> repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);
        Repositorio<Mensaje> repositorioMensaje = FactoryRepo.get(Mensaje.class);
        int idEgreso;
        int idMensaje;
        Mensaje mensaje;
        OperacionEgreso egreso;
        EgresoDTO egresoDTO;
        String jsonEgreso;

        idEgreso = Integer.parseInt(request.params("idEgreso"));
        idMensaje = Integer.parseInt(request.params("idMensaje"));

        mensaje = repositorioMensaje.buscar(idMensaje);
        mensaje.actualizateLeido();

        repositorioMensaje.modificar(mensaje);
        egreso = repositorioEgreso.buscar(idEgreso);
        egresoDTO = generarEgresoDTO(egreso);

        jsonEgreso = gson.toJson(egresoDTO);
        response.type("application/json");

        return jsonEgreso;
    }

    public String pasarEgresosSegunFecha(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        String jsonEgreso;

        LocalDate fechaMax = LocalDate.parse(request.params("fechaMax"));
        List<OperacionEgreso> egresos = usuario.getEntidadPertenece().getOperacionesEgreso();

        egresos = egresos.stream()
                .filter(egreso -> egreso.tenesFechaIgualOAnterior(LocalDate.from(fechaMax)))
                .collect(Collectors.toList());

        egresos.forEach(egreso -> {
            EgresoDTO egresoDTO = generarEgresoDTO(egreso);
            egresoDTOList.add(egresoDTO);
        });
        jsonEgreso = gson.toJson(egresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }

    public String pasarEgresosNoVinculados(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        String jsonEgreso;

        LocalDate fechaMax = LocalDate.parse(request.params("fechaMax"));

        List<OperacionEgreso> egresos = usuario.getEntidadPertenece().getOperacionesEgreso();
        egresos = egresos.stream().filter(egreso -> egreso.podesVincularteSegunFecha(LocalDate.from(fechaMax))).collect(Collectors.toList());

        egresos.forEach(egreso -> {
            EgresoDTO egresoDTO = generarEgresoDTO(egreso);
            egresoDTOList.add(egresoDTO);
        });

        jsonEgreso = gson.toJson(egresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }

    public String sacarRevisor(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Repositorio<OperacionEgreso> repositorioEgreso;
        Usuario usuario = this.getUsuarioFromRequest(request);
        int idEgreso;
        OperacionEgreso egreso;
        JsonObject mensajeRta = new JsonObject();

        idEgreso = Integer.parseInt(request.params("idEgreso"));
        repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);
        egreso = repositorioEgreso.buscar(idEgreso);
        List<Mensaje>mensajesBorrar =usuario.darseDeBajaEn(egreso);
        FactoryRepo.get(Usuario.class).modificar(usuario);

        mensajesBorrar.forEach(mensaje ->FactoryRepo.get(Mensaje.class).eliminar(mensaje));

        mensajeRta.addProperty("mensaje","Operacion Realizada");
        response.type("application/json");

        return gson.toJson(mensajeRta);
    }

    public String agregarRevisor(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Repositorio<OperacionEgreso> repositorioEgreso;
        Usuario usuario = FactoryRepo.get(Usuario.class).buscar(request.session().attribute("userId"));
        int idEgreso;
        OperacionEgreso egreso;
        JsonObject mensajeRta = new JsonObject();


        idEgreso = Integer.parseInt(request.params("idEgreso"));
        repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);
        egreso = repositorioEgreso.buscar(idEgreso);
        usuario.darseDeAltaEn(egreso);
        FactoryRepo.get(OperacionEgreso.class).modificar(egreso);

        mensajeRta.addProperty("mensaje","Operacion Realizada");
        response.type("application/json");

        return gson.toJson(mensajeRta);
    }

    private List<EgresoDTO> convertirEgresosEnEgresosDTO(List<OperacionEgreso> egresos ){
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        egresos.forEach(egreso -> {
            EgresoDTO egresoDTO = generarEgresoDTO(egreso);
            egresoDTOList.add(egresoDTO);
        });
        return egresoDTOList;
    }

    private boolean puedeEditarse(OperacionEgreso operacionAEditar,OperacionEgreso operacionEditada){
        //Tiene un ingreso asociado
        return operacionAEditar.getIngreso() == null || operacionAEditar.getIngreso().saldo() + operacionAEditar.getMontoTotal()//obtengo el saldo sin el egreso a editar
                - operacionEditada.getMontoTotal() > 0;

    }

    public Response submitImagen(Request request, Response response) throws IOException, ServletException {
        if (request.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        }
        Part part = request.raw().getPart("file");
        Path filePath = Paths.get("src/main/resources/comprobantes/"+"egreso-"+request.session().attribute("egreso_actual")+".png");
        Files.copy(part.getInputStream(), filePath);
        return response;
    }



}
