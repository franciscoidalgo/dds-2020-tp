package controllers;


import com.google.gson.Gson;
import domain.Factories.FactoryIngreso;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.time.LocalDate;
import java.util.*;


public class ControllerIngreso {
    public ModelAndView mostrarIngresos(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Repositorio<OperacionEgreso> repo = FactoryRepo.get(OperacionEgreso.class);
        parametros.put("hoy", LocalDate.now());
        return new ModelAndView(parametros, "ingreso.hbs");
    }

    public Response submitIngreso(Request request,Response response) throws Exception {
        Gson gson = new Gson();
        Repositorio<OperacionIngreso> operacionIngresoRepositorio = FactoryRepo.get(OperacionIngreso.class);


        try{
            OperacionIngreso operacionIngreso = FactoryIngreso.get(request);
            operacionIngresoRepositorio.agregar(operacionIngreso);
            response.status(200);
        }catch(Exception e){
            throw new Exception("No se asocio ningun egreso");
        }

        return response;
    }
}
