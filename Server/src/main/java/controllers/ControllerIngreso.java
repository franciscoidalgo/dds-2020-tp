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

public class ControllerIngreso {
    public ModelAndView mostrarIngresos(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Repositorio<OperacionEgreso> repo = FactoryRepo.get(OperacionEgreso.class);
        parametros.put("egresos", repo.buscarTodos());
        return new ModelAndView(parametros, "ingreso.hbs");
    }

    public String getIngresoAsociado(Request request, Response response) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        List<DataEgresos> listaIdsEgresos = Arrays.asList(gson.fromJson(request.queryParams("listaEgresos"), DataEgresos[].class));
        List<OperacionEgreso> listaEgresos = new ArrayList<>();

        Repositorio<OperacionEgreso> repo = FactoryRepo.get(OperacionEgreso.class);

        for (DataEgresos egreso : listaIdsEgresos){
            try{
                listaEgresos.add(repo.buscar(egreso.getId()));
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }

        ServicioAsociacion servicioAsociacion = ServicioAsociacion.getInstancia();
        OperacionIngreso operacionIngresoResultante = servicioAsociacion.getIngresoAsociado(request.queryParams("ingreso"), gson.toJson(listaEgresos.toArray()), "2018-12-25", "2025-12-25");
        System.out.println(operacionIngresoResultante);

        return gson.toJson(operacionIngresoResultante);
    }

    public String submitIngreso(Request request,Response response) throws IOException{
        System.out.println(request.body());

        return request.body();
    }
}
