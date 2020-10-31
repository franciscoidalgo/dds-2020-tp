/*
import domain.Entidad.CategorizacionOperacion.Criterio;
import domain.Entidad.Empresa;
import domain.Entidad.EntidadJuridica;
import domain.Entidad.Usuario.RolAdministrador;
import domain.Entidad.Usuario.RolEstandar;
import domain.Entidad.Usuario.Usuario;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Validadores.CriterioValidacionCantidadPresupuesto;
import domain.Validadores.CriterioValidacionDetalle;
import domain.Validadores.CriterioValidacionSeleccion;
import domain.Validadores.ValidadorDeTransparencia;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestUsuario {

        private Usuario unEstandar;
        private Usuario unAdmin;
        private Criterio unCriterio;
        private Criterio unCriterioHijo;

    private OperacionEgreso unEgreso;
    private OperacionIngreso unIngreso;
    private  Empresa empresaStandard;
    @Before
        public void setUp() throws IOException {

            //Inicializo 2 tipos de usuarios genericos.
            unEstandar = new Usuario();
            unEstandar.setRol(new RolEstandar());

            unAdmin = new Usuario();
            unAdmin.setRol(new RolAdministrador());

            //Inicializo ValidadorTransparencia
            ValidadorDeTransparencia validadorDeTransparencia = ValidadorDeTransparencia.instancia();
            validadorDeTransparencia.agregateCriterio(new CriterioValidacionCantidadPresupuesto());
            validadorDeTransparencia.agregateCriterio(new CriterioValidacionDetalle());
            validadorDeTransparencia.agregateCriterio(new CriterioValidacionSeleccion());

            //Se crea 1 un ingreso y 1 egreso
            unEgreso = new OperacionEgreso();
            unEgreso.setMontoTotal(0);

            unIngreso= new OperacionIngreso();
            unIngreso.setMontoTotal(5000);

            //Inicializo
            empresaStandard = new Empresa();

            unEstandar.setEntidadPertenece(empresaStandard);

            empresaStandard.agregaCriterio(new Criterio("Proyecto Nike"));
            empresaStandard.agregaCriterio(new Criterio("Proyecto Expansion"));

            unCriterio = empresaStandard.getCriterios().get(0);
            unCriterioHijo = empresaStandard.getCriterios().get(1);

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
 */