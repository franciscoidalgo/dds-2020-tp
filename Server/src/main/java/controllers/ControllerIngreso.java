package controllers;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controllers.DTO.IngresoDTO;
import controllers.convertersDTO.ConverterIngresoSubmit;
import domain.Usuario.Usuario;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Operacion.Ingreso.TipoIngreso;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.*;


public class ControllerIngreso {
    public ModelAndView mostrarIngresos(Request request, Response response) {
        Repositorio<TipoIngreso> tipoIngresoRepositorio = FactoryRepo.get(TipoIngreso.class);
        List<TipoIngreso> tipoIngresos = tipoIngresoRepositorio.buscarTodos();
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("tipoIngreso",tipoIngresos);
        parametros.put("hoy", LocalDate.now());
        return new ModelAndView(parametros, "ingreso.hbs");
    }

    public Object submitIngreso(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        JsonObject mensajeRta = new JsonObject();

        String jsonRespuesta;

        //try {

            IngresoDTO ingresoDTO = gson.fromJson(request.body(), IngresoDTO.class);

            OperacionIngreso operacionIngreso = ConverterIngresoSubmit.fromModel(ingresoDTO);
            usuario.realizaOperacion(operacionIngreso);
            FactoryRepo.get(OperacionIngreso.class).agregar(operacionIngreso);

            //Response
            mensajeRta.addProperty("idIngreso", ""+operacionIngreso.getId());
            jsonRespuesta =gson.toJson(mensajeRta);

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
}
