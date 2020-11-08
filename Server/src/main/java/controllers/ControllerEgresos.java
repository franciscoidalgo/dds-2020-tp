package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import APIMercadoLibre.modelos.Ciudad;
import APIMercadoLibre.modelos.Provincia;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import config.ConfiguracionMercadoLibre;
import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import domain.Usuario.Usuario;
import domain.Factories.FactoryEgreso;
import domain.Operacion.Egreso.*;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerEgresos {

    public ModelAndView mostrarEgresos(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);
        Repositorio<Proveedor> repoProveedores = FactoryRepo.get(Proveedor.class);
        Repositorio<TipoDeItem> repoTipoItem = FactoryRepo.get(TipoDeItem.class);
        Repositorio<TipoComprobante> repoTipoComprobante = FactoryRepo.get(TipoComprobante.class);
        Repositorio<TipoDePago> repoTipoDePago = FactoryRepo.get(TipoDePago.class);
        Repositorio<CategoriaOperacion> repoCategorias = FactoryRepo.get(CategoriaOperacion.class);

        if (ConfiguracionMercadoLibre.usarApi) {
            InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
            parametros.put("paises", infoMercadoLibre.getListaDePaises());
            parametros.put("monedas", infoMercadoLibre.getListaDeMonedas());
        }

        parametros.put("egresos", repoEgreso.buscarTodos());
        parametros.put("egreso", true);
        parametros.put("proveedores", repoProveedores.buscarTodos());
        parametros.put("tipoItems", repoTipoItem.buscarTodos());
        parametros.put("tipoComprobante", repoTipoComprobante.buscarTodos());
        parametros.put("tipoPago", repoTipoDePago.buscarTodos());
        parametros.put("categorias", repoCategorias.buscarTodos());//TODO TOCAR PARA QUE SEA DE LA ORGANIZACION
        parametros.put("hoy", LocalDate.now());


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

    public String submitEgreso(Request request, Response response) {
        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);
        RepositorioDeUsuarios repoUsuarios = FactoryRepoUsuario.get();
        JsonObject mensajeRta = new JsonObject();


        try {
            Usuario usuarioLogueado = repoUsuarios.buscar(request.session().attribute("userId"));
            OperacionEgreso operacionEgreso = FactoryEgreso.get(request);


            usuarioLogueado.realizaOperacion(operacionEgreso);
            usuarioLogueado.darseDeAltaEn(operacionEgreso);
            operacionEgreso.validaOperacion();

            repoEgreso.agregar(operacionEgreso);
            //Response
            mensajeRta.addProperty("idEgreso", "" + operacionEgreso.getId());
            response.status(200);

            response.type("application/json");
            return new Gson().toJson(mensajeRta);

        } catch (Exception e) {
            response.status(404);
            mensajeRta.addProperty("mensaje", "No se pudo cargar operacion");
            response.type("application/json");
            return new Gson().toJson(mensajeRta);
        }


    }


}
