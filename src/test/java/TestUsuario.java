import Entidad.CategorizacionOperacion.Criterio;
import Operacion.Egreso.OperacionEgreso;
import Operacion.Ingreso.OperacionIngreso;
import Usuario.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUsuario {

        Usuario unEstandar;
        Usuario unAdmin;


    OperacionEgreso unEgreso;
    OperacionIngreso unIngresoBajo;

    OperacionIngreso unIngresoAlto;
        @Before
        public void setUp() throws Exception {

            unEstandar = new Generador().generaUsuarioEstandar();
            unAdmin= new Generador().generaUsuarioAdmin();

            unEgreso = new Generador().generaEgreso(false,0);
            unIngresoBajo = new Generador().generaIngreso(5000);

            unEstandar.realizaOperacion(unEgreso);
        }
    @Test
    public void testUsuarioRecibeMensaje_CuandoSeDaDeAltaEnOperacion(){

            unEstandar.darseDeAltaEn(unEgreso);
            unEgreso.notificaRevisores();

        Assert.assertEquals(unEstandar.getBandejaDeMensajes().getMensajes().size(),1);
    }

    @Test
    public void testUsuarioEstandar_NoPuedeModificarCriterio(){

            try {
                Criterio unCriterio;
                unEstandar.getEntidadPertenece().creaCriterio("Bonito", null, 0);

                unCriterio = unEstandar.getEntidadPertenece().getCriterios().get(0);

                unEstandar.daleJerarquiA(unCriterio, 1000);
                Assert.fail("No tiene permiso para hacer esto");
            }catch (Exception e){

            }
    }

    @Test
    public void testUsuarioAdmin_PuedeModificarCriterio(){

        try {
            Criterio unCriterio;
            unEstandar.getEntidadPertenece().creaCriterio("Bonito", null, 0);

            unCriterio = unEstandar.getEntidadPertenece().getCriterios().get(0);

            unAdmin.daleJerarquiA(unCriterio, 1000);
            Assert.assertTrue(unCriterio.getNivelJerarquia()==1000);
        }catch (Exception e){

        }
    }

}
