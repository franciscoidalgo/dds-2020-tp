package controllers;

import domain.Entidad.Usuario.Usuario;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.TestUsuariosEnMemoria;
import repositorios.daos.DAOMemoria;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ControllerIndex {

    public Repositorio<Usuario>repositorio;

    public ControllerIndex(){
        this.repositorio = new RepositorioDeUsuarios(
                new DAOMemoria<>(TestUsuariosEnMemoria.generarUsuariosDePrueba())
        );
    }

    public ModelAndView mostrarIndice (Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = repositorio.buscar(request.session().attribute("id"));
        //Usuario usuario = repositorio.buscar(Integer.parseInt(request.cookie("id")));
        parametros.put("usuario", usuario);
        return new ModelAndView(parametros, "dashboard.hbs");

    }

}
