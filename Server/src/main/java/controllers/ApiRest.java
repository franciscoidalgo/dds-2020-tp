package controllers;


import Persistencia.TypeAdapterHibernate;
import com.google.gson.*;
import controllers.DTO.EgresoDTO;
import controllers.DTO.IngresoDTO;
import controllers.convertersDTO.ConverterEgreso;
import controllers.convertersDTO.ConverterIngreso;
import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import domain.Operacion.CategorizacionOperacion.Criterio;
import domain.Operacion.Operacion;
import domain.Usuario.BandejaMensaje.BandejaMensaje;
import domain.Usuario.BandejaMensaje.Mensaje;
import domain.Usuario.Usuario;
import domain.Operacion.Egreso.*;

import domain.Operacion.Ingreso.OperacionIngreso;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.TestUsuariosEnMemoria;
import repositorios.daos.DAOMemoria;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApiRest {
    public String mostrarUsuario(Request request, Response response) {
        Usuario usuarioAMostrar = new RepositorioDeUsuarios(
                new DAOMemoria<>(TestUsuariosEnMemoria.generarUsuariosDePrueba())
        ).buscar(new Integer(request.params("id")));
        Gson gson = new Gson();
        String jUsuario = gson.toJson(usuarioAMostrar);
        response.type("application/json");
        return jUsuario;
    }


    public String mostrarMensajes(Request request, Response response) {
        RepositorioDeUsuarios repositorioDeUsuarios = FactoryRepoUsuario.get();
        Usuario usuarioLogueado = repositorioDeUsuarios.buscar(request.session().attribute("userId"));
        Gson gson = new Gson();
        //TODO BUSCAR POR MAX Y MIN MENSAJES ----> GANAR EN OPTIMIZACION
        BandejaMensaje bandejaMensaje =usuarioLogueado.getBandejaDeMensajes();
        String jMensajes = gson.toJson(bandejaMensaje.toDTO());
        response.type("application/json");
        return jMensajes;
    }


    //TODO REFACTORIZAR ESTO
    public String pasarEgresosSegunID(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Repositorio<OperacionEgreso> repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);
        Integer idEgreso;
        OperacionEgreso egreso;
        EgresoDTO egresoDTO;
        String jsonEgreso;

        idEgreso = Integer.parseInt(request.params("idEgreso"));
        egreso = repositorioEgreso.buscar(idEgreso);
        egresoDTO = generarEgresoDTO(egreso);

        jsonEgreso = gson.toJson(egresoDTO);
        response.type("application/json");

        return jsonEgreso;
    }




    public String pasarEgresosMensaje(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Repositorio<OperacionEgreso> repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);
        Repositorio<Mensaje> repositorioMensaje = FactoryRepo.get(Mensaje.class);
        Integer idEgreso;
        Integer idMensaje;
        Mensaje mensaje;
        OperacionEgreso egreso;
        EgresoDTO egresoDTO;
        String jsonEgreso;

        idEgreso = Integer.parseInt(request.params("idEgreso"));
        idMensaje = Integer.parseInt(request.params("idMensaje"));

        mensaje = repositorioMensaje.buscar(idMensaje);
        mensaje.actualizateLeido();

        repositorioMensaje.modificar(mensaje);
        egreso = repositorioEgreso.buscar(idEgreso);
        egresoDTO = generarEgresoDTO(egreso);

        jsonEgreso = gson.toJson(egresoDTO);
        response.type("application/json");

        return jsonEgreso;
    }

    public String sacarRevisor(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Repositorio<OperacionEgreso> repositorioEgreso;
        Usuario usuario = FactoryRepo.get(Usuario.class).buscar(request.session().attribute("userId"));
        Integer idEgreso;
        OperacionEgreso egreso;
        JsonObject mensajeRta = new JsonObject();


        idEgreso = Integer.parseInt(request.params("idEgreso"));
        repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);
        egreso = repositorioEgreso.buscar(idEgreso);
        List<Mensaje>mensajesBorrar =usuario.darseDeBajaEn(egreso);
        FactoryRepo.get(Usuario.class).modificar(usuario);

        mensajesBorrar.forEach(mensaje ->FactoryRepo.get(Mensaje.class).eliminar(mensaje));

        mensajeRta.addProperty("mensaje","Operacion Realizada");
        response.type("application/json");

        return gson.toJson(mensajeRta);
    }

    public String agregarRevisor(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        Repositorio<OperacionEgreso> repositorioEgreso;
        Usuario usuario = FactoryRepo.get(Usuario.class).buscar(request.session().attribute("userId"));
        Integer idEgreso;
        OperacionEgreso egreso;
        JsonObject mensajeRta = new JsonObject();


        idEgreso = Integer.parseInt(request.params("idEgreso"));
        repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);
        egreso = repositorioEgreso.buscar(idEgreso);
        usuario.darseDeAltaEn(egreso);
        FactoryRepo.get(OperacionEgreso.class).modificar(egreso);

        mensajeRta.addProperty("mensaje","Operacion Realizada");
        response.type("application/json");

        return gson.toJson(mensajeRta);
    }

    public String pasarEgresosNoVinculados(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        String jsonEgreso;

        LocalDate fechaMax = LocalDate.parse(request.params("fechaMax"));

        List<OperacionEgreso> egresos = usuario.getEntidadPertenece().getOperacionesEgreso();
        egresos = egresos.stream().filter(egreso -> egreso.podesVincularteSegunFecha(LocalDate.from(fechaMax))).collect(Collectors.toList());

        egresos.forEach(egreso -> {
            EgresoDTO egresoDTO = generarEgresoDTO(egreso);
            egresoDTOList.add(egresoDTO);
        });

        jsonEgreso = gson.toJson(egresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }

    public String pasarEgresosSegunFecha(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        String jsonEgreso;

        LocalDate fechaMax = LocalDate.parse(request.params("fechaMax"));
        List<OperacionEgreso> egresos = usuario.getEntidadPertenece().getOperacionesEgreso();

        egresos = egresos.stream()
                        .filter(egreso -> egreso.tenesFechaIgualOAnterior(LocalDate.from(fechaMax)))
                        .collect(Collectors.toList());

        egresos.forEach(egreso -> {
            EgresoDTO egresoDTO = generarEgresoDTO(egreso);
            egresoDTOList.add(egresoDTO);
        });
        jsonEgreso = gson.toJson(egresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }

    public String pasarTodosEgresos(Request request, Response response) {

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        String jsonEgreso;

        List<OperacionEgreso> egresos = usuario.getEntidadPertenece().getOperacionesEgreso();

        egresos.forEach(egreso -> {
            EgresoDTO egresoDTO = generarEgresoDTO(egreso);
            egresoDTOList.add(egresoDTO);
        });

        jsonEgreso = gson.toJson(egresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }

    public String pasarEgresosSegunCategorias(Request request, Response response) {

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<OperacionEgreso> operacionEgresos = usuario.getEntidadPertenece().getOperacionesEgreso();
        List<CategoriaOperacion> categoriasSeleccionadas = new ArrayList<>();
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        String jsonEgreso;
        //Trato el JSON
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonArray jCategorias= rootObject.getAsJsonArray("idCategorias");

        //Agrego a la lista las categorias
        for (JsonElement columnElement : jCategorias) {
            Integer idCategoria = columnElement.getAsInt();
            CategoriaOperacion categoriaOperacion = FactoryRepo.get(CategoriaOperacion.class).buscar(idCategoria);
            categoriasSeleccionadas.add(categoriaOperacion);
        }
        //Filtro los que matchean
        operacionEgresos = operacionEgresos.stream()
                .filter(operacionEgreso -> categoriasSeleccionadas.stream()
                        .allMatch(operacionEgreso::tenesCategoria))
                .collect(Collectors.toList());

        operacionEgresos.forEach(egreso -> {
            EgresoDTO egresoDTO = generarEgresoDTO(egreso);
            egresoDTOList.add(egresoDTO);
        });

        jsonEgreso = gson.toJson(egresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }


    public String pasarTodosIngresos(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<IngresoDTO> ingresoDTOList = new ArrayList<>();
        String jsonEgreso;

        List<OperacionIngreso> ingresosList = usuario.getEntidadPertenece().getOperacionesIngreso();

        ingresosList.forEach(ingreso -> {
            ingresoDTOList.add(ConverterIngreso.toDTO(ingreso));
        });

        jsonEgreso = gson.toJson(ingresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }


    public String vincularIngresos(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        IngresoDTO ingresoVincular = gson.fromJson(request.body(),IngresoDTO.class);
        Repositorio<OperacionIngreso> operacionIngresoRepositorio =FactoryRepo.get(OperacionIngreso.class) ;
        OperacionIngreso ingreso = operacionIngresoRepositorio.buscar(ingresoVincular.getId());
        List<OperacionEgreso> egresos = ingresoVincular.getListaEgresos().stream()
                                                .map(egresoDTO -> FactoryRepo.get(OperacionEgreso.class)
                                                            .buscar(egresoDTO.getId())).collect(Collectors.toList());

        egresos.forEach(operacionEgreso -> {
            try {
                ingreso.agregarEgreso(operacionEgreso);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        operacionIngresoRepositorio.modificar(ingreso);
        response.type("application/json");
        return "{\"mensaje\":\"todo ok\"}";
    }
    public String pasarIngresoPorVincular(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<IngresoDTO> ingresosDTO;
        String jsonEgreso;

        List<OperacionIngreso> ingresos = usuario.getEntidadPertenece().getOperacionesIngreso().stream()
                .filter(operacionIngreso -> operacionIngreso.saldo()>0).collect(Collectors.toList());

        ingresosDTO = ingresos.stream().map(operacionIngreso -> ConverterIngreso.toDTO(operacionIngreso)).collect(Collectors.toList()); ;
        jsonEgreso = gson.toJson(ingresosDTO);
        response.type("application/json");
        return jsonEgreso;
    }
    public String pasarIngresoSegunID(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        IngresoDTO ingresoDTO;
        Integer idIngreso;
        String jsonEgreso;

        idIngreso = Integer.parseInt(request.params("idIngreso"));
        OperacionIngreso ingreso = usuario.getEntidadPertenece().getOperacionesIngreso().stream()
                                            .filter(operacionIngreso -> idIngreso == operacionIngreso.getId())
                                            .findFirst().get();

        ingresoDTO = ConverterIngreso.toDTO(ingreso);

        jsonEgreso = gson.toJson(ingresoDTO);
        response.type("application/json");
        return jsonEgreso;
    }

    public String mostrarProveedores(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        String jsonProveedor;
        Proveedor proveedor;
        Integer idEgreso;
        Repositorio<Proveedor> proveedorRepositorio = FactoryRepo.get(Proveedor.class);

        try {
            idEgreso = Integer.parseInt(request.params("id"));
            proveedor = proveedorRepositorio.buscar(idEgreso);
            jsonProveedor = gson.toJson(proveedor);

            response.type("application/json");
            response.status(200);

            return jsonProveedor;
        } catch (Exception e) {
            response.status(404);
            return "No se encontro proveedor";
        }
    }

    public String mostraItemsSegunTipo(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        String jsonProveedor;
        List<Item> items;
        TipoDeItem tipoDeItems;
        Integer idTipoItem;
        Repositorio<Item> itemRepositorio = FactoryRepo.get(Item.class);
        Repositorio<TipoDeItem> tipoDeItemRepositorio = FactoryRepo.get(TipoDeItem.class);

        try {
            idTipoItem = Integer.parseInt(request.params("idTipoItem"));
            tipoDeItems = tipoDeItemRepositorio.buscar(idTipoItem);

            items = itemRepositorio.buscarTodos().stream()
                    .filter(item -> item.getTipoDeItem().equals(tipoDeItems))
                    .collect(Collectors.toList());
            jsonProveedor = gson.toJson(items);

            response.type("application/json");
            response.status(200);

            return jsonProveedor;
        } catch (Exception e) {
            response.status(404);
            return "No se encontro proveedor";
        }
    }


    public String pasarCategoriasSegunCriterio(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        String jsonProveedor;
        Integer idCriterio;
        Criterio criterioSeleccionado;
        Repositorio<Criterio> criterioRepositorio = FactoryRepo.get(Criterio.class);

        try {
            idCriterio = Integer.parseInt(request.params("idCriterio"));
            criterioSeleccionado = criterioRepositorio.buscar(idCriterio);

            jsonProveedor = gson.toJson(criterioSeleccionado);

            response.type("application/json");
            response.status(200);

            return jsonProveedor;
        } catch (Exception e) {
            response.status(404);
            return "No se encontro proveedor";
        }
    }

    public EgresoDTO generarEgresoDTO(OperacionEgreso egreso) {
        EgresoDTO egresoDTO = new EgresoDTO();
        egresoDTO.setId(egreso.getId());
        egresoDTO.setPedido(egreso.getDetalle().getPedidos());
        egresoDTO.setCantPresupuestos(egreso.getCantPresupuestos());
        egresoDTO.setDetalle(egreso.getDetalle());
        egresoDTO.setMedioDePago(egreso.getMedioDePago());
        egresoDTO.setMontoTotal(egreso.montoTotal());
        egresoDTO.setFecha(egreso.getFecha().toString());
        egresoDTO.setCantPresupuestosFaltantes(egreso.cantPresupuestosFaltantes());

        return egresoDTO;
    }

}
