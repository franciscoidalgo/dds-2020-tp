package controllers;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controllers.DTO.IngresoSubmitDTO;
import controllers.convertersDTO.ConverterIngresoSubmit;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Operacion.Ingreso.TipoIngreso;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
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

    public Object submitIngreso(Request request, Response response){
        Gson gson = new Gson();
        Repositorio<OperacionIngreso> repositorio = FactoryRepo.get(OperacionIngreso.class);
        JsonObject mensajeRta = new JsonObject();
        String jsonRespuesta;

        try {
            IngresoSubmitDTO ingresoSubmitDTO = gson.fromJson(request.body(), IngresoSubmitDTO.class);
            OperacionIngreso operacionIngreso = ConverterIngresoSubmit.toModel(ingresoSubmitDTO);
            repositorio.agregar(operacionIngreso);

            mensajeRta.addProperty("idIngreso", ""+operacionIngreso.getId());
            jsonRespuesta =gson.toJson(mensajeRta);

            response.status(200);
            response.type("application/json");

            return jsonRespuesta;

        } catch (Exception e) {
            mensajeRta.addProperty("mensaje", "No se pudo cargar ingreso");
            jsonRespuesta =gson.toJson(mensajeRta);
            response.status(404);
            response.type("application/json");
            return  jsonRespuesta;
        }
    }
}
