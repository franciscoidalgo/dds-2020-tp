package controllers;


import APIMercadoLibre.InfoMercadoLibre;
import Persistencia.TypeAdapterHibernate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ConfiguracionMercadoLibre;
import domain.DireccionPostal.DireccionPostal;
import domain.Entidad.CategorizacionEmpresa.Categoria;
import domain.Entidad.CategorizacionEmpresa.Sector;
import domain.Entidad.*;
import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import domain.Operacion.CategorizacionOperacion.Criterio;
import domain.Usuario.RolEstandar;
import domain.Usuario.Usuario;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerEntidad extends Controller {


    public ModelAndView mostrarNuevaEntidad(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = getUsuarioFromRequest(request);
        List<Sector> sectores = FactoryRepo.get(Sector.class).buscarTodos();
        if (ConfiguracionMercadoLibre.usarApi) {
            InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
            parametros.put("paises", infoMercadoLibre.getListaDePaises());
        }


        if (usuario.getRol() instanceof RolEstandar) {
            return new ModelAndView(parametros, "dashboard.hbs");
        }

        parametros.put("organizacion", getOrganizacionFromRequest(request));
        parametros.put("sectores", sectores);
        return new ModelAndView(parametros, "nuevaEntidad.hbs");
    }

    public String eliminarEntidad(Request request, Response response) {
        Repositorio<Entidad> entidadRepositorio = FactoryRepo.get(Entidad.class);
        Organizacion organizacion = getOrganizacionFromRequest(request);

        Entidad entidad = entidadRepositorio.buscar(Integer.parseInt(request.params("id")));
        organizacion.quitarEntidad(entidad);

        Entidad auxSeleccion = organizacion.getEntidades().stream().filter(entidad1 -> !entidad.equals(entidad1)).findFirst().get();

        FactoryRepo.get(Usuario.class).buscarTodos().forEach(usuario1 -> {
            if (usuario1.getEntidadPertenece().equals(entidad)) {
                usuario1.setEntidadPertenece(auxSeleccion);
                FactoryRepo.get(Usuario.class).modificar(usuario1);
            }
        });

        entidadRepositorio.eliminar(entidad);
        response.type("application/json");
        return "";
    }

    public Response nuevaEntidad(Request request, Response response) {
        Repositorio<Entidad> repositorio = FactoryRepo.get(Entidad.class);
        Organizacion organizacion = getOrganizacionFromRequest(request);

        String tipo = request.queryParams("seleccion-tipo-entidad");
        String nombre = request.queryParams("nombre-ficticio");
        String descripcion = request.queryParams("descripcion");

        response.redirect("/usuario");
        response.status(200);
        //base
        if (tipo.equalsIgnoreCase("base")) {
            EntidadBase entidadBase = new EntidadBase(nombre, descripcion);
            entidadBase.setOrganizacion(organizacion);
            repositorio.agregar(entidadBase);
            return response;
        }
        //OS
        String razonSocial = request.queryParams("razon-social");
        Long cuit = Long.parseLong(request.queryParams("cuit"));
        DireccionPostal direccionPostal = getDireccionFromRequest(request);
        if (tipo.equalsIgnoreCase("os")) {
            OrganizacionSocial organizacionSocial = new OrganizacionSocial(razonSocial, nombre, cuit, descripcion, direccionPostal);
            organizacionSocial.setOrganizacion(organizacion);
            repositorio.agregar(organizacionSocial);
            return response;
        }
        //Empresa
        String actividad = request.queryParams("actividad");
        Long igj = Long.parseLong(request.queryParams("igj"));
        Sector sector = FactoryRepo.get(Sector.class).buscar(Integer.parseInt(request.queryParams("sector")));
        double promVenta = Double.parseDouble(request.queryParams("prom-venta"));
        Integer cantPersonal = Integer.parseInt(request.queryParams("cant-personal"));

        if (tipo.equalsIgnoreCase("empresa")) {
            Empresa empresa = new Empresa(razonSocial, nombre, cuit, descripcion, direccionPostal, igj, actividad, sector, cantPersonal, promVenta);
            empresa.setOrganizacion(organizacion);
            repositorio.agregar(empresa);
            return response;
        }

        response.status(404);
        return response;
    }

    public String nuevoCriterio(Request request, Response response) {
        Repositorio<Criterio> criterioRepositorio = FactoryRepo.get(Criterio.class);
        Entidad entidad = getEntidadFromRequest(request);
        Integer idCriterio = Integer.parseInt(request.params("idCriterio"));
        Criterio criterioNuevo = new Criterio(request.params("nombre"));

        if (idCriterio == -1) {
            entidad.agregaCriterio(criterioNuevo);
            criterioRepositorio.agregar(criterioNuevo);
        } else {
            Criterio criterio = criterioRepositorio.buscar(idCriterio);
            Criterio criterioNieto = criterio.getCriterioHijo();

            criterio.setCriterioHijo(criterioNuevo);
            if (null != criterioNieto) {
                criterioNuevo.setCriterioHijo(criterioNieto);
            }
            criterioRepositorio.agregar(criterioNuevo);
            FactoryRepo.get(Entidad.class).modificar(entidad);

        }

        return "";
    }

    public String nuevaCategoria(Request request, Response response) {
        Repositorio<Criterio> criterioRepositorio = FactoryRepo.get(Criterio.class);
        Repositorio<CategoriaOperacion> categoriaOperacionRepositorio = FactoryRepo.get(CategoriaOperacion.class);
        Integer idCriterio = Integer.parseInt(request.params("idCriterio"));

        CategoriaOperacion categoriaOperacion = new CategoriaOperacion(request.params("nombre"));
        Criterio criterio = criterioRepositorio.buscar(idCriterio);

        criterio.agregateCategoria(categoriaOperacion);

        categoriaOperacionRepositorio.agregar(categoriaOperacion);

        return "";
    }

    public String pasarCategoriasSegunCriterio(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Integer idCriterio = Integer.parseInt(request.params("idCriterio"));
        Criterio criterioSeleccionado = FactoryRepo.get(Criterio.class).buscar(idCriterio);

        response.type("application/json");
        response.status(200);
        return gson.toJson(criterioSeleccionado.getCategorias());
    }

    private DireccionPostal getDireccionFromRequest(Request request) {
        DireccionPostal direccionPostal = new DireccionPostal();
        direccionPostal.setDpto(request.queryParams("dpto"));
        direccionPostal.setPiso(request.queryParams("piso"));
        direccionPostal.setCalle(request.queryParams("calle"));
        direccionPostal.setAltura(Integer.parseInt(request.queryParams("altura")));
        direccionPostal.setCiudad(request.queryParams("ciudad"));
        direccionPostal.setProvincia(request.queryParams("provincia"));
        direccionPostal.setPais(request.queryParams("pais"));

        return direccionPostal;
    }
}
