package controllers;

import APIAsociadora.ServicioAsociacion;
import Persistencia.TypeAdapterHibernate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerIngreso {
    public ModelAndView mostrarIngresos(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Repositorio<OperacionEgreso> repo = FactoryRepo.get(OperacionEgreso.class);
        List<OperacionEgreso> egresosSinVincular =  repo.buscarTodos().stream().filter(egreso -> !egreso.estaAsociado()).collect(Collectors.toList());
        parametros.put("egresos", egresosSinVincular);
        return new ModelAndView(parametros, "ingreso.hbs");
    }

    public Response submitIngreso(Request request,Response response){
        Gson gson = new Gson();
        Repositorio<OperacionIngreso> operacionIngresoRepositorio = FactoryRepo.get(OperacionIngreso.class);
        Repositorio<OperacionEgreso> operacionEgresoRepositorio = FactoryRepo.get(OperacionEgreso.class);
        List<Integer> idsEgresos = Arrays.asList(gson.fromJson(request.queryParams("listaEgresos"), Integer[].class));
        OperacionIngreso operacionIngreso = new OperacionIngreso(Float.parseFloat(request.queryParams("montoTotal")), request.queryParams("descripcion"));
        for(Integer id : idsEgresos){
            OperacionEgreso aux = operacionEgresoRepositorio.buscar(id);
            operacionIngreso.agregateEgreso(aux);
            operacionEgresoRepositorio.modificar(aux);
        }
        operacionIngresoRepositorio.agregar(operacionIngreso);
        response.redirect("/dashboard");
        return response;
    }
}
