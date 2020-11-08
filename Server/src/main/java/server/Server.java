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


public class Server {
    public static void main(String[] args) throws Exception {

        Spark.port(9000);
        Router.init();
        //DebugScreen.enableDebugScreen();
    }

}
