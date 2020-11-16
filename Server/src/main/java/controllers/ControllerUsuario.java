package controllers;

import domain.Entidad.*;
import domain.Entidad.CategorizacionEmpresa.Categoria;
import domain.Operacion.CategorizacionOperacion.Criterio;
import domain.Password.*;
import domain.Usuario.RolAdministrador;
import domain.Usuario.Usuario;
import passwordHasher.Guava256Hasher;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerUsuario extends Controller {

    public ModelAndView mostrarUsuario(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = getUsuarioFromRequest(request);
        Entidad entidad = getEntidadFromRequest(request);
        Organizacion organizacion = entidad.getOrganizacion();

        List<Empresa> empresas = organizacion.getEmpresas();
        List<OrganizacionSocial> organizacionesSociales = organizacion.getOrganizacionSociales();
        List<EntidadBase> entidadBases = organizacion.getEntidadesBase();
        List<Criterio> criterios = entidad.mostrarTodosCriterios();

        //entidad instanceof EntidadBase no funca! => Sol trambolica
        Empresa empresa = FactoryRepo.get(Empresa.class).buscar(entidad.getId());
        EntidadBase entidadBase = FactoryRepo.get(EntidadBase.class).buscar(entidad.getId());
        OrganizacionSocial organizacionSocial = FactoryRepo.get(OrganizacionSocial.class).buscar(entidad.getId());

        if (null != empresa) {
            parametros.put("seleccionadaEmpresa", empresa);
        }
        if (null != entidadBase) {
            parametros.put("seleccionadaBase", entidadBase);
        }
        if (null != organizacionSocial) {
            parametros.put("seleccionadaOS", organizacionSocial);
        }

        parametros.put("usuario", usuario);
        parametros.put("rol", usuario.getRol() instanceof RolAdministrador);
        parametros.put("organizacion", organizacion);
        parametros.put("entidadSeleccionada", entidad);
        parametros.put("entidadesBase", entidadBases);
        parametros.put("orgSociales", organizacionesSociales);
        parametros.put("empresas", empresas);

        parametros.put("criterios", criterios);
        return new ModelAndView(parametros, "usuario.hbs");
    }

    public String cambiarNombre(Request request, Response response) {
        Repositorio<Usuario> repositorio = FactoryRepo.get(Usuario.class);
        String nombre = request.body();
        Usuario usuario = getUsuarioFromRequest(request);

        usuario.setNombre(nombre);
        repositorio.modificar(usuario);
        response.status(200);
        response.type("application/json");
        return "{\"mensaje\":\"ok\"}";
    }

    public String cambiarApellido(Request request, Response response) {
        Repositorio<Usuario> repositorio = FactoryRepo.get(Usuario.class);
        String apellido = request.body();
        Usuario usuario = getUsuarioFromRequest(request);

        usuario.setApellido(apellido);
        repositorio.modificar(usuario);
        response.status(200);
        response.type("application/json");
        return "{\"mensaje\":\"ok\"}";
    }

    public String cambiarPassword(Request request, Response response) {
        Repositorio<Usuario> repositorio = FactoryRepo.get(Usuario.class);
        Usuario usuario = getUsuarioFromRequest(request);
        String password = request.body();
        Guava256Hasher guava = new Guava256Hasher();
        String passwordHash = guava.hashPassword(password);
        ValidatePassword validatePassword = instanciarValidadorPassword();

        response.type("application/json");

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
        Organizacion organizacion = getOrganizacionFromRequest(request);
        Entidad entidad = organizacion.getEntidades().stream().filter(entidad1 -> id.equals(entidad1.getId())).findFirst().get();


        usuario.setEntidadPertenece(entidad);
        usuarioFactoryRepo.modificar(usuario);
        response.type("application/json");
        response.status(200);
        return "{\"mensaje\":\"ok\"}";

    }


    private ValidatePassword instanciarValidadorPassword() {
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

        validatePassword.setPasswordCriteria(passwordCriteria);

        return validatePassword;
    }
}
