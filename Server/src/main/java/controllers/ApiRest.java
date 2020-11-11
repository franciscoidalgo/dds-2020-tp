package controllers;


import Persistencia.TypeAdapterHibernate;
import com.google.gson.*;
import controllers.DTO.EgresoDTO;
import controllers.DTO.IngresoDTO;

import controllers.convertersDTO.ConverterIngreso;
import domain.Operacion.CategorizacionOperacion.Criterio;
import domain.Usuario.BandejaMensaje.BandejaMensaje;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ApiRest {
    public String mostrarUsuario(Request request, Response response) {
        Usuario usuarioAMostrar = new RepositorioDeUsuarios(
                new DAOMemoria<>(TestUsuariosEnMemoria.generarUsuariosDePrueba())
        ).buscar(Integer.parseInt(request.params("id")));
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

    public String mostraEntidades(Request request, Response response) {
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


}
