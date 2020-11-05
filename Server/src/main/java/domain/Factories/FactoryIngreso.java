package domain.Factories;

/*
public class FactoryIngreso {
    public static OperacionIngreso get(Request request) {
        Gson gson = new Gson();
        Repositorio<OperacionEgreso> operacionEgresoRepositorio = FactoryRepo.get(OperacionEgreso.class);
        Repositorio<Usuario> usuarioRepositorio = FactoryRepo.get(Usuario.class);
        Usuario usuario =usuarioRepositorio.buscar(request.session().attribute("userId"));

        OperacionIngreso operacionIngreso = new OperacionIngreso(LocalDate.parse(request.queryParams("fecha")),Float.parseFloat(request.queryParams("montoTotal")),usuario.getEntidadPertenece(),request.queryParams("descripcion"), LocalDate.parse(request.queryParams("fechaAceptabilidad")));
        List<Integer> idsEgresos = Arrays.asList(gson.fromJson(request.queryParams("listaEgresos"), Integer[].class));
        for(Integer id : idsEgresos){
        OperacionEgreso aux = operacionEgresoRepositorio.buscar(id);
        aux.setIngreso(operacionIngreso);
        operacionEgresoRepositorio.modificar(aux);
        }
        return  operacionIngreso;
    }
}

*/
