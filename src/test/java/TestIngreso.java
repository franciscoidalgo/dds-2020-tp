import Operacion.Egreso.OperacionEgreso;
import Operacion.Ingreso.OperacionIngreso;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestIngreso {

    OperacionEgreso unEgreso;
    OperacionIngreso unIngreso;

    @Before
    public void setUp() throws Exception {


        unIngreso = new Generador().generaIngreso(100000);

        //Egreso con un detalle generico de 16000
        unEgreso = new Generador().generaEgreso(false,0);
    }


    @Test
    public void testSaldoPositivo_CuandoSeAsociaUnEgresoConIngreso(){

        unIngreso.agregateEgreso(unEgreso);

        Assert.assertTrue(unIngreso.saldoOperacion()>0);
    }

    @Test
    public void testSaldoNegativo_CuandoSeAsocia10EgresosConIngreso(){

        for(int i = 0; i<10;i++) unIngreso.agregateEgreso(unEgreso);

        Assert.assertTrue(unIngreso.saldoOperacion()<0);
    }



}
