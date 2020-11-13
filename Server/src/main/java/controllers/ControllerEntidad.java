package controllers;


import APIMercadoLibre.InfoMercadoLibre;
import config.ConfiguracionMercadoLibre;
import domain.Entidad.Entidad;
import domain.Entidad.Organizacion;
import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import domain.Operacion.Egreso.*;
import domain.Usuario.Usuario;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ControllerEntidad extends  Controller{
    public String eliminarEntidad(Request request, Response response){
        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        Organizacion organizacion = usuario.getEntidadPertenece().getOrganizacion();

        Repositorio<Entidad> entidadRepositorio = FactoryRepo.get(Entidad.class);
        Entidad entidad = entidadRepositorio.buscar(Integer.parseInt(request.params("id")));
        organizacion.quitarEntidad(entidad);
        response.type("application/json");

        Entidad auxSeleccion = organizacion.getEntidades().stream().filter(entidad1 -> !entidad.equals(entidad1)).findFirst().get();

        FactoryRepo.get(Usuario.class).buscarTodos().forEach(usuario1 ->{
            if(usuario1.getEntidadPertenece().equals(entidad)){
                usuario1.setEntidadPertenece(auxSeleccion);
                FactoryRepo.get(Usuario.class).modificar(usuario1);
            }
        });

         entidadRepositorio.eliminar(entidad);

        return "";
    }

    public ModelAndView mostrarNuevaEntidad(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        if (ConfiguracionMercadoLibre.usarApi) {
            InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
            parametros.put("paises", infoMercadoLibre.getListaDePaises());
        }
        parametros.put("organizacion",getOrganizacionFromRequest(request));
        return new ModelAndView(parametros, "/entidad/nueva.hbs");
    }

}
