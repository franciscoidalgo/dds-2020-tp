package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import APIMercadoLibre.modelos.Ciudad;
import APIMercadoLibre.modelos.Provincia;
import com.google.gson.Gson;
import config.ConfiguracionMercadoLibre;
import domain.Entidad.Usuario.Usuario;
import domain.Operacion.Egreso.FactoryEgreso;
import domain.Operacion.Egreso.OperacionEgreso;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public Response submitEgreso(Request request,Response response) {
        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);
        RepositorioDeUsuarios repoUsuarios = FactoryRepoUsuario.get();
        Usuario usuarioLogueado = repoUsuarios.buscar(request.session().attribute("userId"));
        OperacionEgreso operacionEgreso = FactoryEgreso.get(request);
        operacionEgreso.agregateRevisor(usuarioLogueado);
        repoEgreso.agregar(operacionEgreso);
        request.session().attribute("egreso_actual", Integer.toString(operacionEgreso.getId()));
        response.status(200);

        return response;
    }

    public Response submitImagen(Request request, Response response) throws IOException, ServletException {
        if (request.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        }
        Part part = request.raw().getPart("file");
        Path filePath = Paths.get("src/main/resources/comprobantes/"+"egreso-"+request.session().attribute("egreso_actual")+".png");
        Files.copy(part.getInputStream(), filePath);
        return response;
    }
}
