/*
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


  /*
    @Test
    public void testEgresoTieneVariosRevisores(){
        // Todo: Terminar test con validaciones!
        Usuario revisor1 = new Usuario("Pepe","",new RolEstandar(),new Empresa(50,50000,null));
        revisor1.darseDeAltaEn(unEgreso);
    }
*/
/*
    @Test
    public void testCalculaMontoTotal_Con2Presupuestos1Detalle(){
        unEgreso.agregaPresupuesto(presupuesto1);
        unEgreso.agregaPresupuesto(presupuesto2);
        Assert.assertEquals(unEgreso.montoTotal(),3*presupuesto1.mostrarCosto(),0);
    }


}
*/