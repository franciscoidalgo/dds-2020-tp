package controllers;


import Persistencia.TypeAdapterHibernate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.DTO.EgresoDTO;
import controllers.DTO.IngresoDTO;
import domain.Entidad.Usuario.Mensaje;
import domain.Entidad.Usuario.Usuario;
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
        List<Mensaje> listaMensajes = usuarioLogueado.getBandejaDeMensajes().getMensajes();
        String jMensajes = gson.toJson(listaMensajes);
        response.type("application/json");
        return jMensajes;
    }

    public String pasarEgresos(Request request, Response response) {
        Gson gson;
        Repositorio<OperacionEgreso> repositorioEgreso;
        Integer idEgreso;
        OperacionEgreso egreso;
        EgresoDTO egresoDTO;

        String jsonEgreso;

        gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();
        idEgreso = Integer.parseInt(request.params("id"));
        repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);

        egreso = repositorioEgreso.buscar(idEgreso);
        egresoDTO = generarEgresoDTO(egreso);

        jsonEgreso = gson.toJson(egresoDTO);
        response.type("application/json");

        return jsonEgreso;
    }

    public String pasarEgresosNoVinculados(Request request, Response response) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        Repositorio<OperacionEgreso> repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);
        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        String jsonEgreso;

        LocalDate fechaMax = LocalDate.parse(request.params("fechaMax"));

        //List<OperacionEgreso> egresos = usuario.getEntidadPertenece().getOperacionesEgreso();//TODO <--- ESTO TIENE QUE FUNCIONAR
        List<OperacionEgreso> egresos = repositorioEgreso.buscarTodos();

        egresos = egresos.stream().filter(egreso -> egreso.faltaVinculacion(fechaMax)).collect(Collectors.toList());

        egresos.forEach(egreso -> {
            EgresoDTO egresoDTO = generarEgresoDTO(egreso);
            egresoDTOList.add(egresoDTO);
        });

        jsonEgreso = gson.toJson(egresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }

    public String pasarTodosEgresos(Request request,Response response){

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        Repositorio<OperacionEgreso> repositorioEgreso = FactoryRepo.get(OperacionEgreso.class);
        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<EgresoDTO> egresoDTOList = new ArrayList<>();
        String jsonEgreso;

        //List<OperacionEgreso> egresos = usuario.getEntidadPertenece().getOperacionesEgreso();//TODO <--- ESTO TIENE QUE FUNCIONAR
        List<OperacionEgreso> egresos = repositorioEgreso.buscarTodos();

        egresos.forEach(egreso -> {
            EgresoDTO egresoDTO = generarEgresoDTO(egreso);
            egresoDTOList.add(egresoDTO);
        });

        jsonEgreso = gson.toJson(egresoDTOList);
        response.type("application/json");
        return jsonEgreso;
    }

    public String pasarTodosIngresos(Request request,Response response){
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapterHibernate.FACTORY).create();

        Repositorio<OperacionIngreso> repositorioIngreso = FactoryRepo.get(OperacionIngreso.class);
        RepositorioDeUsuarios repositorioUsuario = FactoryRepoUsuario.get();
        Usuario usuario = repositorioUsuario.buscar(request.session().attribute("userId"));
        List<IngresoDTO> ingresoDTOList = new ArrayList<>();
        String jsonEgreso;

        //List<OperacionEgreso> egresos = usuario.getEntidadPertenece().getOperacionesEgreso();//TODO <--- ESTO TIENE QUE FUNCIONAR
        List<OperacionIngreso> ingresosList =repositorioIngreso.buscarTodos();

        ingresosList.forEach(ingreso -> {
            ingresoDTOList.add(generarIngresoDTO(ingreso));
        });

        jsonEgreso = gson.toJson(ingresoDTOList);
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
            idTipoItem = Integer.parseInt(request.params("id"));

            tipoDeItems = tipoDeItemRepositorio.buscar(idTipoItem);

            items = itemRepositorio.buscarTodos().stream().filter(item -> item.getTipoDeItem().equals(tipoDeItems)).collect(Collectors.toList());

            jsonProveedor = gson.toJson(items);

            response.type("application/json");
            response.status(200);

            return jsonProveedor;
        } catch (Exception e) {
            response.status(404);
           return "No se encontro proveedor";
        }
    }

    private EgresoDTO generarEgresoDTO(OperacionEgreso egreso){
        EgresoDTO egresoDTO = new EgresoDTO();
        egresoDTO.setId(egreso.getId());
        egresoDTO.setItems(egreso.getItems());
        egresoDTO.setCantPresupuestos(egreso.getCantPresupuestos());
        egresoDTO.setDetalle(egreso.getDetalle());
        egresoDTO.setMedioDePago(egreso.getMedioDePago());
        egresoDTO.setMontoTotal(egreso.montoTotal());
        egresoDTO.setCategorias(egreso.getCategorias());
        egresoDTO.setFecha(egreso.getFecha());

        return egresoDTO;
    }

    private IngresoDTO generarIngresoDTO(OperacionIngreso ingreso){
        IngresoDTO ingresoDTO = new IngresoDTO();
        ingresoDTO.setId(ingreso.getId());
        ingresoDTO.setDescripcion(ingreso.getDescripcion());
        ingresoDTO.setFechaAceptacion(ingreso.getFechaAceptabilidad());
        ingresoDTO.setFechaRealizada(ingreso.getFecha());
        ingresoDTO.setMonto(ingreso.montoTotal());

        return ingresoDTO;
    }
}
