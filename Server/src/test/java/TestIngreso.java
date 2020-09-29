import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestIngreso {

    OperacionEgreso unEgreso;
    OperacionIngreso unIngreso;

    @Before
    public void setUp() {
        unIngreso = new OperacionIngreso();
        unIngreso.setMontoTotal(100000);

        unEgreso = new OperacionEgreso();
        unEgreso.setMontoTotal(16000);
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
