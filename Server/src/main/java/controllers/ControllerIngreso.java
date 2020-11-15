package controllers;


import APIAsociadora.ServicioAsociacion;
import Persistencia.TypeAdapterHibernate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import controllers.DTO.DTOOperacionEgreso;
import controllers.DTO.DTOOperacionIngreso;
import controllers.DTO.IngresoDTO;
import controllers.convertersDTO.ConverterEgreso;
import controllers.convertersDTO.ConverterIngreso;
import domain.Entidad.Entidad;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Operacion.Ingreso.TipoIngreso;
import domain.Usuario.Usuario;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ControllerIngreso extends Controller {
    public ModelAndView mostrarIngresos(Request request, Response response) {
        Repositorio<TipoIngreso> tipoIngresoRepositorio = FactoryRepo.get(TipoIngreso.class);
        List<TipoIngreso> tipoIngresos = tipoIngresoRepositorio.buscarTodos();
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("tipoIngreso", tipoIngresos);
        parametros.put("hoy", LocalDate.now());
        return new ModelAndView(parametros, "ingreso.hbs");
    }

    public Object submitIngreso(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        Usuario usuario = getUsuarioFromRequest(request);
        JsonObject mensajeRta = new JsonObject();

        String jsonRespuesta;
        //try {

        IngresoDTO ingresoDTO = gson.fromJson(request.body(), IngresoDTO.class);

        OperacionIngreso operacionIngreso = ConverterIngreso.fromModel(ingresoDTO);
        usuario.realizaOperacion(operacionIngreso);
        FactoryRepo.get(OperacionIngreso.class).agregar(operacionIngreso);

        //Response
        mensajeRta.addProperty("idIngreso", "" + operacionIngreso.getId());
        jsonRespuesta = gson.toJson(mensajeRta);

        response.status(200);
        response.type("application/json");

        return jsonRespuesta;

        // } catch (Exception e) {
        //     mensajeRta.addProperty("mensaje", "No se pudo cargar ingreso");
        //     jsonRespuesta =gson.toJson(mensajeRta);
        //     response.status(404);
        //     response.type("application/json");
        //    return  jsonRespuesta;
        // }
    }

    public String pasarTodosIngresos(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<IngresoDTO> ingresoDTOList = new ArrayList<>();
        String jsonEgreso;

        List<OperacionIngreso> ingresosList = usuario.getEntidadPertenece().getOperacionesIngreso();

        ingresosList.forEach(ingreso -> {
            ingresoDTOList.add(ConverterIngreso.toDTO(ingreso));
        });

        jsonEgreso = gson.toJson(ingresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }

    public String vincularIngresos(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        IngresoDTO ingresoVincular = gson.fromJson(request.body(), IngresoDTO.class);
        Repositorio<OperacionIngreso> operacionIngresoRepositorio = FactoryRepo.get(OperacionIngreso.class);
        OperacionIngreso ingreso = operacionIngresoRepositorio.buscar(ingresoVincular.getId());
        List<OperacionEgreso> egresos = ingresoVincular.getListaEgresos().stream()
                .map(egresoDTO -> FactoryRepo.get(OperacionEgreso.class)
                        .buscar(egresoDTO.getId())).collect(Collectors.toList());

        egresos.forEach(operacionEgreso -> {
            try {
                ingreso.agregarEgreso(operacionEgreso);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        operacionIngresoRepositorio.modificar(ingreso);
        response.type("application/json");
        return "{\"mensaje\":\"todo ok\"}";
    }

    public String vincularAutoIngresos(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Repositorio<OperacionIngreso> operacionIngresoRepositorio = FactoryRepo.get(OperacionIngreso.class);

        ServicioAsociacion servicioAsociacion = ServicioAsociacion.getInstancia();
        Entidad entidad = getUsuarioFromRequest(request).getEntidadPertenece();

        OperacionIngreso ingreso = FactoryRepo.get(OperacionIngreso.class).buscar(Integer.parseInt(request.params("idIngreso")));
        DTOOperacionIngreso ingresoVincular = ConverterIngreso.generarIngresoVinculadorDTO(ingreso);
        LocalDate fechaAceptabilidad = ingreso.getFechaAceptabilidad();

        List<DTOOperacionEgreso> egresos = entidad.getOperacionesEgreso().stream()
                .filter(egreso -> egreso.tenesFechaIgualOAnterior(fechaAceptabilidad) && null==egreso.getIngreso())
                .map(ConverterEgreso::generarEgresoVinculadorDTO)
                .collect(Collectors.toList());

        try {
            System.out.println("POR ASOCIAR");
            ingreso = servicioAsociacion.getIngresoAsociado(gson.toJson(ingresoVincular), gson.toJson(egresos));
            System.out.println("TERMINE ASOCIACION");
            operacionIngresoRepositorio.modificar(ingreso);
            System.out.println("AGREGUE CAMBIOS AL REPO");
            response.status(200);
            response.type("application/json");
            return "{\"mensaje\":\"todo ok\"}";
        } catch (IOException e) {
            e.printStackTrace();
            response.status(400);
            response.type("application/json");
            return "{\"mensaje\":\"No se pudo asociar\"}";
        }


    }

    public String pasarIngresoPorVincular(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<IngresoDTO> ingresosDTO;
        String jsonEgreso;

        List<OperacionIngreso> ingresos = usuario.getEntidadPertenece().getOperacionesIngreso().stream()
                .filter(operacionIngreso -> operacionIngreso.saldo() > 0).collect(Collectors.toList());

        ingresosDTO = ingresos.stream().map(ConverterIngreso::toDTO).collect(Collectors.toList());
        jsonEgreso = gson.toJson(ingresosDTO);
        response.type("application/json");
        return jsonEgreso;
    }

    public String pasarIngresoSegunID(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        IngresoDTO ingresoDTO;
        int idIngreso;
        String jsonEgreso;

        idIngreso = Integer.parseInt(request.params("idIngreso"));
        OperacionIngreso ingreso = usuario.getEntidadPertenece().getOperacionesIngreso().stream()
                .filter(operacionIngreso -> idIngreso == operacionIngreso.getId())
                .findFirst().get();

        ingresoDTO = ConverterIngreso.toDTO(ingreso);

        jsonEgreso = gson.toJson(ingresoDTO);
        response.type("application/json");
        return jsonEgreso;
    }

}
