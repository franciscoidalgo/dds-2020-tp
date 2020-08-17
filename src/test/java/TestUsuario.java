import Entidad.CategorizacionOperacion.Criterio;
import Operacion.Egreso.OperacionEgreso;
import Operacion.Ingreso.OperacionIngreso;
import Usuario.Usuario;
import Validadores.ValidadorDeTransparencia;
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

    private Generador generador;


    @Before
        public void setUp() {
            generador = Generador.instancia();
            //Inicializo 2 tipos de usuarios genericos.
            unEstandar = generador.generaUsuarioEstandar();
            unAdmin= generador.generaUsuarioAdmin();
            //Inicializo ValidadorTransparencia
            generador.inicializaValidadorTransparencia();

            //Se crea 1 un ingreso y 1 egreso
            unEgreso = generador.generaEgreso(0);
            unIngreso = generador.generaIngreso(5000);

            //Se 2 crean los criterios
            unEstandar.getEntidadPertenece().creaCriterio("Proyecto Nike");
            unEstandar.getEntidadPertenece().creaCriterio("Proyecto Expansion");

            unCriterio = unEstandar.getEntidadPertenece().getCriterios().get(0);
            unCriterioHijo = unEstandar.getEntidadPertenece().getCriterios().get(1);




        }
    @Test
    public void testRevisor_EstandarYAdminPuedeDarseDeAlta(){
        unEstandar.getEntidadPertenece().realizaOperacion(unEgreso);
        unEstandar.darseDeAltaEn(unEgreso);
        unAdmin.darseDeAltaEn(unEgreso);

        Assert.assertEquals(unEgreso.getRevisores().size(),2);
    }
    @Test
    public void testUsuario_EsElReceptorMensaje(){
        unEstandar.getEntidadPertenece().realizaOperacion(unEgreso);
        unEstandar.darseDeAltaEn(unEgreso);

        Assert.assertEquals(unEgreso.getRevisores().get(0),unEstandar);
    }

    @Test
    public void testUsuarioRecibeMensaje_CuandoSeDaDeAltaEnOperacion() throws InterruptedException {
            unEstandar.getEntidadPertenece().realizaOperacion(unEgreso);
            unEstandar.darseDeAltaEn(unEgreso);
            ValidadorDeTransparencia validadorDeTransparencia = ValidadorDeTransparencia.instancia();
            unEgreso.validaOperacion();
            Thread.sleep(5000);

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
                unEstandar.getRol().daleJerarquiaA(unCriterio, unCriterioHijo);
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

            unAdmin.getRol().daleJerarquiaA(unCriterio, unCriterioHijo);
            Assert.assertEquals(unCriterio.getCriterioHijo(),unCriterioHijo);
        }catch (Exception ignored){
        }
    }

}
