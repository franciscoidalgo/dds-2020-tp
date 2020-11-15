package controllers;


import Persistencia.TypeAdapterHibernate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.DTO.IngresoDTO;
import controllers.convertersDTO.ConverterIngreso;
import domain.Operacion.CategorizacionOperacion.Criterio;
import domain.Operacion.Egreso.Item;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Egreso.Proveedor;
import domain.Operacion.Egreso.TipoDeItem;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Usuario.BandejaMensaje.BandejaMensaje;
import domain.Usuario.Usuario;
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
        BandejaMensaje bandejaMensaje = usuarioLogueado.getBandejaDeMensajes();
        String jMensajes = gson.toJson(bandejaMensaje.toDTO());
        response.type("application/json");
        return jMensajes;
    }

    public String mostrarProveedores(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        String jsonProveedor;
        Proveedor proveedor;
        int idEgreso;
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
        int idTipoItem;
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
        int idTipoItem;
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
        int idCriterio;
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
