package controllers;


import domain.Entidad.Entidad;
import domain.Entidad.Organizacion;
import domain.Usuario.Usuario;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.Request;
import spark.Response;

public class ControllerEntidad {
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

}
