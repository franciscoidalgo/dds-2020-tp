import Entidad.CategorizacionOperacion.Criterio;
import Operacion.Egreso.OperacionEgreso;
import Operacion.Ingreso.OperacionIngreso;
import Usuario.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUsuario {

        private Usuario unEstandar;
        private Usuario unAdmin;
        private Criterio unCriterio;
        private Criterio unCriterioHijo;

    private OperacionEgreso unEgreso;
    private OperacionIngreso unIngreso;


    @Before
        public void setUp() {
            //Inicializo 2 tipos de usuarios genericos.
            unEstandar = new Generador().generaUsuarioEstandar();
            unAdmin= new Generador().generaUsuarioAdmin();

            //Se crea 1 un ingreso y 1 egreso
            unEgreso = new Generador().generaEgreso(0);
            unIngreso = new Generador().generaIngreso(5000);

            //Se 2 crean los criterios
            unEstandar.getEntidadPertenece().creaCriterio("Proyecto Nike");
            unEstandar.getEntidadPertenece().creaCriterio("Proyecto Expansion");

            unCriterio = unEstandar.getEntidadPertenece().getCriterios().get(0);
            unCriterioHijo = unEstandar.getEntidadPertenece().getCriterios().get(1);

        }
    @Test
    public void testUsuarioRecibeMensaje_CuandoSeDaDeAltaEnOperacion(){
            unEstandar.realizaOperacion(unEgreso);
            unEstandar.darseDeAltaEn(unEgreso);
            unEgreso.notificaRevisores();

        Assert.assertEquals(unEstandar.getBandejaDeMensajes().getMensajes().size(),1);
    }

    @Test
    public void testUsuario_AsociaEgresoAIngreso(){
        unEstandar.asociaEgresoAIngreso(unEgreso,unIngreso);

        Assert.assertEquals(unIngreso.getEgresos().size(),1);
    }

    @Test
    public void testUsuarioEstandar_NoPuedeModificarCriterio(){

            try {
                unEstandar.daleJerarquiA(unCriterio, unCriterioHijo);
                Assert.fail("No tiene permiso para hacer esto");
            }catch (Exception ignored){

            }
    }

    @Test
    public void testUsuarioAdmin_PuedeModificarCriterio(){

        try {
            Criterio unCriterio;
            Criterio unCriterioHijo;

            unCriterio = unEstandar.getEntidadPertenece().getCriterios().get(0);
            unCriterioHijo = unEstandar.getEntidadPertenece().getCriterios().get(1);

            unAdmin.daleJerarquiA(unCriterio, unCriterioHijo);
            Assert.assertEquals(unCriterio.getCriterioHijo(),unCriterioHijo);
        }catch (Exception ignored){
        }
    }

}
