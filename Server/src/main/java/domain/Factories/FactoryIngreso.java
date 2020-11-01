package domain.Factories;

import com.google.gson.Gson;
import domain.Entidad.Usuario.Usuario;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.Request;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class FactoryIngreso {
    public static OperacionIngreso get(Request request) {
        Gson gson = new Gson();
        Repositorio<OperacionEgreso> operacionEgresoRepositorio = FactoryRepo.get(OperacionEgreso.class);
        Repositorio<Usuario> usuarioRepositorio = FactoryRepo.get(Usuario.class);
        Usuario usuario =usuarioRepositorio.buscar(request.session().attribute("userId"));

        OperacionIngreso operacionIngreso = new OperacionIngreso(LocalDate.parse(request.queryParams("fecha")),Float.parseFloat(request.queryParams("montoTotal")),usuario.getEntidadPertenece(),request.queryParams("descripcion"), LocalDate.parse(request.queryParams("fechaAceptabilidad")));
        List<Integer> idsEgresos = Arrays.asList(gson.fromJson(request.queryParams("listaEgresos"), Integer[].class));
        for(Integer id : idsEgresos){
        OperacionEgreso aux = operacionEgresoRepositorio.buscar(id);
        operacionEgresoRepositorio.modificar(aux);
        }
        return  operacionIngreso;
    }
}
