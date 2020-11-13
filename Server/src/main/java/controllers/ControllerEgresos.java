package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import Persistencia.TypeAdapterHibernate;
import com.google.gson.*;
import config.ConfiguracionMercadoLibre;
import controllers.DTO.EgresoDTO;
import domain.Entidad.Entidad;
import domain.Factories.FactoryEgreso;
import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import domain.Operacion.Egreso.*;
import domain.Usuario.BandejaMensaje.Mensaje;
import domain.Usuario.Usuario;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.imageio.ImageIO;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static controllers.convertersDTO.ConverterEgreso.generarEgresoDTO;

public class ControllerEgresos extends Controller {

    private final Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();


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

        parametros.put("numeroEgreso", request.session().attribute("idEgreso"));

        return new ModelAndView(parametros, "egreso.hbs");
    }

    public String submitEgreso(Request request, Response response) {
        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);
        JsonObject mensajeRta = new JsonObject();
        Usuario usuarioLogueado = getUsuarioFromRequest(request);

        try {

            OperacionEgreso operacionEgreso = FactoryEgreso.get(request);

            /*********************** EDICION **********************/
            Integer idEgreso = request.session().attribute("idEgreso");
            request.session().removeAttribute("idEgreso");
            if (null != idEgreso) {//Si tiene ese valor en el session => Edita
                OperacionEgreso operacionEgresoEdit = getEgresofromRequest(request);
                if (puedeEditarse(operacionEgresoEdit, operacionEgreso)) {
                    operacionEgresoEdit.setMedioDePago(operacionEgreso.getMedioDePago());
                    operacionEgresoEdit.setDetalle(operacionEgreso.getDetalle());
                    operacionEgresoEdit.setCantPresupuestos(operacionEgreso.getCantPresupuestos());

                    operacionEgresoEdit.setFecha(operacionEgreso.getFecha());
                    operacionEgresoEdit.setMontoTotal(operacionEgreso.getMontoTotal());
                    repoEgreso.modificar(operacionEgresoEdit);

                    mensajeRta.addProperty("idEgreso", "" + operacionEgresoEdit.getId());
                    response.status(200);

                } else {
                    mensajeRta.addProperty("idEgreso", "" + operacionEgresoEdit.getId());
                    response.status(400);

                }
                response.type("application/json");
                return new Gson().toJson(mensajeRta);
            }

            usuarioLogueado.realizaOperacion(operacionEgreso);
            usuarioLogueado.darseDeAltaEn(operacionEgreso);
            repoEgreso.agregar(operacionEgreso);
            request.session().attribute("egreso_actual", operacionEgreso.getId());

            //Response TODO GENERALIZAR ESTO
            mensajeRta.addProperty("idEgreso", "" + operacionEgreso.getId());
            response.status(200);
            response.type("application/json");
            return gson.toJson(mensajeRta);

        } catch (Exception e) {
            e.printStackTrace();
            response.status(404);
            mensajeRta.addProperty("mensaje", "No se pudo cargar operacion");
            response.type("application/json");
            return gson.toJson(mensajeRta);
        }

    }

    public String editarEgreso(Request request, Response response) {
        Integer idEgreso = Integer.parseInt(request.params("idEgreso"));
        JsonObject mensajeRta = new JsonObject();

        request.session().attribute("idEgreso", idEgreso);
        mensajeRta.addProperty("idEgreso", "");

        response.status(200);
        response.type("application/json");
        return new Gson().toJson(mensajeRta);
    }

    public String pasarEgresosSegunID(Request request, Response response) {
        OperacionEgreso egreso = getEgresofromRequest(request);
        EgresoDTO egresoDTO = generarEgresoDTO(egreso);
        response.type("application/json");

        return gson.toJson(egresoDTO);
    }

    public String pasarEgresosSegunCategorias(Request request, Response response) {
        Entidad entidad = getEntidadFromRequest(request);
        List<OperacionEgreso> operacionEgresos = entidad.getOperacionesEgreso();
        List<CategoriaOperacion> categoriasSeleccionadas = new ArrayList<>();
        List<EgresoDTO> egresoDTOList;
        //TODO Extraer esto
        //Trato el JSON
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonArray jCategorias = rootObject.getAsJsonArray("idCategorias");

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
        response.type("application/json");
        return gson.toJson(egresoDTOList);
    }

    public String pasarTodosEgresos(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Entidad entidad = this.getEntidadFromRequest(request);

        List<EgresoDTO> egresoDTOList = convertirEgresosEnEgresosDTO(entidad.getOperacionesEgreso());

        response.type("application/json");
        return gson.toJson(egresoDTOList);
    }

    public String pasarEgresosMensaje(Request request, Response response) {
        Repositorio<Mensaje> repositorioMensaje = FactoryRepo.get(Mensaje.class);
        Mensaje mensaje;
        EgresoDTO egresoDTO;

        OperacionEgreso egreso = getEgresofromRequest(request);
        mensaje = repositorioMensaje.buscar(egreso.getId());
        mensaje.actualizateLeido();

        repositorioMensaje.modificar(mensaje);
        egresoDTO = generarEgresoDTO(egreso);

        response.type("application/json");
        return gson.toJson(egresoDTO);
    }

    public String pasarEgresosSegunFecha(Request request, Response response) {
        Entidad entidad = getEntidadFromRequest(request);
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        LocalDate fechaMax = LocalDate.parse(request.params("fechaMax"));
        List<OperacionEgreso> egresos = entidad.getOperacionesEgreso();

        egresos = egresos.stream()
                .filter(egreso -> egreso.tenesFechaIgualOAnterior(LocalDate.from(fechaMax)))
                .collect(Collectors.toList());

        egresos.forEach(egreso -> egresoDTOList.add(generarEgresoDTO(egreso)));

        response.type("application/json");

        return gson.toJson(egresoDTOList);
    }

    public String pasarEgresosNoVinculados(Request request, Response response) {
        LocalDate fechaMax = LocalDate.parse(request.params("fechaMax"));
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        Entidad entidad = getEntidadFromRequest(request);

        List<OperacionEgreso> egresos;
        egresos = entidad.getOperacionesEgreso().stream()
                .filter(egreso -> egreso.podesVincularteSegunFecha(LocalDate.from(fechaMax)))
                .collect(Collectors.toList());

        egresos.forEach(egreso -> egresoDTOList.add(generarEgresoDTO(egreso)));

        response.type("application/json");
        return gson.toJson(egresoDTOList);
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
        List<Mensaje> mensajesBorrar = usuario.darseDeBajaEn(egreso);
        FactoryRepo.get(Usuario.class).modificar(usuario);

        mensajesBorrar.forEach(mensaje -> FactoryRepo.get(Mensaje.class).eliminar(mensaje));

        mensajeRta.addProperty("mensaje", "Operacion Realizada");
        response.type("application/json");

        return gson.toJson(mensajeRta);
    }

    public String agregarRevisor(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Usuario usuario = getUsuarioFromRequest(request);
        OperacionEgreso egreso = getEgresofromRequest(request);
        JsonObject mensajeRta = new JsonObject();

        usuario.darseDeAltaEn(egreso);
        FactoryRepo.get(OperacionEgreso.class).modificar(egreso);

        mensajeRta.addProperty("mensaje", "Operacion Realizada");
        response.type("application/json");

        return gson.toJson(mensajeRta);
    }

    private List<EgresoDTO> convertirEgresosEnEgresosDTO(List<OperacionEgreso> egresos) {
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        egresos.forEach(egreso -> {
            EgresoDTO egresoDTO = generarEgresoDTO(egreso);
            egresoDTOList.add(egresoDTO);
        });
        return egresoDTOList;
    }

    private boolean puedeEditarse(OperacionEgreso operacionAEditar, OperacionEgreso operacionEditada) {
        //Tiene un ingreso asociado
        return operacionAEditar.getIngreso() == null || operacionAEditar.getIngreso().saldo() + operacionAEditar.getMontoTotal()//obtengo el saldo sin el egreso a editar
                - operacionEditada.getMontoTotal() > 0;

    }

    private OperacionEgreso getEgresofromRequest(Request request) {
        Repositorio<OperacionEgreso> repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);
        Integer idEgreso = Integer.parseInt(request.params("idEgreso"));
        return repositorioEgreso.buscar(idEgreso);
    }

    public Response submitImagen(Request request, Response response) throws IOException, ServletException {
        int id = request.session().attribute("egreso_actual");
        if (request.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        }
        Part part = request.raw().getPart("file");
        String pathString = generarPath(Integer.toString(id));
        Repositorio<OperacionEgreso> repoEgresos = FactoryRepo.get(OperacionEgreso.class);
        OperacionEgreso operacionEgreso = repoEgresos.buscar(id);
        operacionEgreso.setPath(pathString);
        repoEgresos.modificar(operacionEgreso);
        Path filePath = Paths.get(pathString);
        Files.copy(part.getInputStream(), filePath);
        return response;
    }

    private String generarPath(String id){
        return "src/main/resources/comprobantes/"+"egreso-"+ id +".pdf";
    }

    public byte[] getImagenComprobante(Request request, Response response) throws IOException {
        Path path = Paths.get(generarPath(request.params("id")));
        byte[] archivo = Files.readAllBytes(path);
        response.type("application/pdf");
        response.header("Content-Disposition","attachment");
        return archivo;
    }



}
