package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import APIMercadoLibre.modelos.Ciudad;
import APIMercadoLibre.modelos.Provincia;
import com.google.gson.Gson;
import config.ConfiguracionMercadoLibre;
import domain.Entidad.Usuario.Usuario;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Operacion;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerEgresos {

    public ModelAndView mostrarEgresos(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        if(ConfiguracionMercadoLibre.usarApi){
            InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
            parametros.put("paises", infoMercadoLibre.getListaDePaises());
        }
        Repositorio<OperacionEgreso> repo = FactoryRepo.get(OperacionEgreso.class);
        parametros.put("egresos", repo.buscarTodos());

        parametros.put("egreso", true);
        return new ModelAndView(parametros, "egreso.hbs");
    }

    public String pasarProvincias(Request request, Response response) throws IOException {
        List<Provincia> listaProvincias = InfoMercadoLibre.instancia().getListaDeProvincias().stream().filter(
                provincia -> provincia.getCountry().name.equals(request.params("nombrePais"))).collect(Collectors.toList());
        Gson gson = new Gson();
        String jProvincias = gson.toJson(listaProvincias);
        response.type("application/json");
        return jProvincias;
    }

    public String pasarCiudades(Request request, Response response) throws IOException {
        List<Ciudad> listaCiudades = Arrays.asList(InfoMercadoLibre.instancia().getListaDeProvincias().stream().filter(
                provincia -> provincia.getNombre().equals(request.params("nombreProvincia"))).findFirst().get().cities);
        Gson gson = new Gson();
        String jCiudades = gson.toJson(listaCiudades);
        response.type("application/json");
        return jCiudades;
    }

    public String submitEgreso(Request request,Response response) throws IOException{
        Gson gson = new Gson();
        RepositorioDeUsuarios repo = FactoryRepoUsuario.get();
        Usuario usuarioLogueado = repo.buscar(request.session().attribute("userId"));
        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);
        OperacionEgreso operacionEgreso = gson.fromJson(request.body(), OperacionEgreso.class);
        operacionEgreso.setFecha(LocalDate.now());
        operacionEgreso.setRevisores(new ArrayList<>());
        usuarioLogueado.darseDeAltaEn(operacionEgreso);
        repoEgreso.agregar(operacionEgreso);

        return request.body();
    }
}
