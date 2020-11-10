package controllers;

import domain.Entidad.Entidad;
import domain.Entidad.Organizacion;
import domain.Password.*;
import domain.Usuario.RolAdministrador;
import domain.Usuario.Usuario;
import passwordHasher.Guava256Hasher;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerUsuario {

    public ModelAndView mostrarUsuario(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = FactoryRepo.get(Usuario.class).buscar(request.session().attribute("userId"));
        Entidad entidad = usuario.getEntidadPertenece();
        Organizacion organizacion = entidad.getOrganizacion();

        List<Entidad> entidades = organizacion.getEntidades().stream().filter(entidad1 -> entidad1 != entidad).collect(Collectors.toList());

        parametros.put("usuario", usuario);
        parametros.put("rol", usuario.getRol() instanceof RolAdministrador);
        //Entidad Seleccionada
        parametros.put("seleccionadaNombre", entidad.nombre());
        parametros.put("seleccionadaDescripcion", entidad.descripcion());
        parametros.put("seleccionadaId", entidad.getId());
        //Organizacion-Entidades
        parametros.put("organizacion", organizacion);
        parametros.put("entidades", entidades);
        return new ModelAndView(parametros, "usuario.hbs");
    }

    public String cambiarNombre(Request request, Response response) {
        Repositorio<Usuario> repositorio = FactoryRepo.get(Usuario.class);
        String nombre = request.body();
        Usuario usuario = repositorio.buscar(request.session().attribute("userId"));
        usuario.setNombre(nombre);
        repositorio.modificar(usuario);
        response.status(200);

        return "{\"mensaje\":\"ok\"}";
    }

    public String cambiarApellido(Request request, Response response) {
        Repositorio<Usuario> repositorio = FactoryRepo.get(Usuario.class);
        String apellido = request.body();
        Usuario usuario = repositorio.buscar(request.session().attribute("userId"));
        usuario.setApellido(apellido);
        repositorio.modificar(usuario);
        response.status(200);
        return "{\"mensaje\":\"ok\"}";
    }

    public String cambiarPassword(Request request, Response response) {
        Repositorio<Usuario> repositorio = FactoryRepo.get(Usuario.class);
        String password = request.body();
        Guava256Hasher guava = new Guava256Hasher();
        String passwordHash = guava.hashPassword(password);
        Usuario usuario = repositorio.buscar(request.session().attribute("userId"));

        //TODO INSTANCIAR ESTO EN OTRO LADO!
        ValidatePassword validatePassword = new ValidatePassword();
        ValidatePasswordLength validatePasswordLength = new ValidatePasswordLength();
        ValidatePasswordNumber validatePasswordNumber = new ValidatePasswordNumber();
        ValidatePasswordCapitalLetter validatePasswordCapitalLetter = new ValidatePasswordCapitalLetter();
        ValidatePasswordSpecialCharacter validatePasswordSpecialCharacter = new ValidatePasswordSpecialCharacter();
        ValidatePasswordDictionary validatePasswordDictionary = new ValidatePasswordDictionary();

        ArrayList<PasswordCriteria> passwordCriteria = new ArrayList<>();

        passwordCriteria.add(validatePasswordLength);
        passwordCriteria.add(validatePasswordNumber);
        passwordCriteria.add(validatePasswordCapitalLetter);
        passwordCriteria.add(validatePasswordSpecialCharacter);
        passwordCriteria.add(validatePasswordDictionary);
        validatePassword.passwordCriteria = passwordCriteria;

        if (validatePassword.validatePassword(password)) {
            usuario.setPassword(passwordHash);
            repositorio.modificar(usuario);
            response.status(200);
            return "{\"mensaje\":\"ok\"}";
        } else {
            response.status(400);

            return "{\"mensaje\":\"No se puede agregar\"}";
        }

    }


    public String cambiarEntidad(Request request, Response response) {
        Repositorio<Usuario> usuarioFactoryRepo = FactoryRepo.get(Usuario.class);
        Usuario usuario = usuarioFactoryRepo.buscar(request.session().attribute("userId"));
        Integer id;

        id = Integer.parseInt(request.params("idEntidad"));
        Organizacion organizacion = usuario.getEntidadPertenece().getOrganizacion();
        Entidad entidad = organizacion.getEntidades().stream().filter(entidad1 -> id.equals(entidad1.getId())).findFirst().get();


        usuario.setEntidadPertenece(entidad);
        usuarioFactoryRepo.modificar(usuario);
        response.status(200);
        return "{\"mensaje\":\"ok\"}";

    }

}
