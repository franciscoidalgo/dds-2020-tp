import Operacion.Egreso.Detalle;
import Operacion.Egreso.OperacionEgreso;
import Operacion.Egreso.Presupuesto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestEgreso {

    OperacionEgreso unEgreso;

    Presupuesto presupuesto1;
    Presupuesto presupuesto2;

    @Before
    public void setUp() throws Exception {
         //Presupuesto1
        presupuesto1 = new Generador().generaPresupuesto();

        //Presupuesto2
        presupuesto2 = new Generador().generaPresupuesto();

        //Egreso ya viene con un detalle generado
        unEgreso = new Generador().generaEgreso(0);
    }


    @Test
    public void testCalculaMontoTotal_Con2Presupuestos1Detalle() throws Exception {
        unEgreso.agregaPresupuesto(presupuesto1);
        unEgreso.agregaPresupuesto(presupuesto2);
        Assert.assertEquals(unEgreso.montoTotal(),16000,0);
    }

    @Test
    public void testCargaPresupuesto_NoSePuedeCargarPresupuestoSinHaberCargadoEgreso() throws Exception {
        unEgreso.setDetalle(new Detalle());
        try {
            unEgreso.agregaPresupuesto(presupuesto1);
            Assert.fail();
        }catch (Exception ignored){}
    }


}