package controllers;

import domain.Entidad.*;
import domain.Usuario.RolAdministrador;
import domain.Usuario.RolEstandar;
import domain.Usuario.Usuario;
import repositorios.factories.FactoryRepo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ControllerDashboard extends Controller {


    public ControllerDashboard() {

    }

    public ModelAndView mostrarIndice(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = getUsuarioFromRequest(request);

        Entidad entidadSeleccionada = getEntidadFromRequest(request);
        Organizacion organizacion = getOrganizacionFromRequest(request);

        List<Empresa> empresas = organizacion.getEmpresas();
        List<OrganizacionSocial> organizacionesSociales = organizacion.getOrganizacionSociales();
        List<EntidadBase> entidadBases = organizacion.getEntidadesBase();

        parametros.put("usuario", usuario);
        parametros.put("idSeleccionada", entidadSeleccionada.getId());
        parametros.put("nombreSeleccionada", entidadSeleccionada.getNombre());

        parametros.put("entidadesBase", entidadBases);
        parametros.put("orgSociales", organizacionesSociales);
        parametros.put("empresas", empresas);
        return new ModelAndView(parametros, "dashboard.hbs");

    }

}
