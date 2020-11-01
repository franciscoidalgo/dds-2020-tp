package server;

import domain.Entidad.Organizacion;
import domain.Factories.FactoryEgreso;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Validadores.CriterioValidacionCantidadPresupuesto;
import domain.Validadores.CriterioValidacionDetalle;
import domain.Validadores.CriterioValidacionSeleccion;
import domain.Validadores.ValidadorDeTransparencia;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.Spark;
import spark.debug.DebugScreen;
import java.util.List;

public class Server {
    public static void main(String[] args) throws Exception {
        iniciarScheduler();
        Spark.port(9000);
        Router.init();
        DebugScreen.enableDebugScreen();
    }

    private static void iniciarScheduler(){
        ValidadorDeTransparencia validadorDeTransparencia = ValidadorDeTransparencia.instancia();
        CriterioValidacionCantidadPresupuesto criterioCantPresupuesto = new CriterioValidacionCantidadPresupuesto();
        CriterioValidacionDetalle criterioDetalle = new CriterioValidacionDetalle();
        CriterioValidacionSeleccion criterioSeleccion = new CriterioValidacionSeleccion();

        validadorDeTransparencia.agregateCriterio(criterioSeleccion);
        validadorDeTransparencia.agregateCriterio(criterioDetalle);
        validadorDeTransparencia.agregateCriterio(criterioCantPresupuesto);

        Repositorio<OperacionEgreso> operacionEgresoRepositorio = FactoryRepo.get(OperacionEgreso.class);
        List<OperacionEgreso> operacionEgresos = operacionEgresoRepositorio.buscarTodos();

        operacionEgresos.forEach(OperacionEgreso::validaOperacion);
    }
}
