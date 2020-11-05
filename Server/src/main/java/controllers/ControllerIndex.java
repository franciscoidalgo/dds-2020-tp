package controllers;

import domain.Usuario.Usuario;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ControllerIndex {


    public ControllerIndex(){

    }

    public ModelAndView mostrarIndice (Request request, Response response){
        RepositorioDeUsuarios repositorio = FactoryRepoUsuario.get();
        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = repositorio.buscar(request.session().attribute("userId"));
        //Usuario usuario = repositorio.buscar(Integer.parseInt(request.cookie("idUsuario")));
        parametros.put("usuario", usuario);
        return new ModelAndView(parametros, "dashboard.hbs");

    }

}
