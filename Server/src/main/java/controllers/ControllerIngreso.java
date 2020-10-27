package controllers;

import APIAsociadora.ServicioAsociacion;
import com.google.gson.Gson;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerIngreso {
    public ModelAndView mostrarIngresos(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Repositorio<OperacionEgreso> repo = FactoryRepo.get(OperacionEgreso.class);
        parametros.put("egresos", repo.buscarTodos());
        return new ModelAndView(parametros, "ingreso.hbs");
    }

    public String getIngresoAsociado(Request request, Response response) throws IOException {
        Gson gson = new Gson();
        ServicioAsociacion servicioAsociacion = ServicioAsociacion.getInstancia();
        OperacionIngreso operacionIngresoResultante = servicioAsociacion.getIngresoAsociado(request.queryParams("ingreso"), request.queryParams("listaEgresos"), request.queryParams("fechaDesde"), request.queryParams("fechaHasta"));

        return gson.toJson(operacionIngresoResultante);
    }

    public String submitIngreso(Request request,Response response) throws IOException{
        System.out.println(request.body());

        return request.body();
    }
}
