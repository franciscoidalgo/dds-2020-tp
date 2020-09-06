import APIMercadoLibre.InfoMercadoLibre;
import domain.Operacion.Egreso.DetalleCompra;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Egreso.Presupuesto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestEgreso {

    OperacionEgreso unEgreso;

    Presupuesto presupuesto1;
    Presupuesto presupuesto2;

    Generador generador;

    @Before
    public void setUp() throws Exception {
        generador = Generador.instancia();
        //Presupuesto1
        presupuesto1 = generador.generaPresupuesto();

        //Presupuesto2
        presupuesto2 = generador.generaPresupuesto();

        //Egreso ya viene con un detalle generado
        unEgreso = generador.generaEgreso(0);
    }


    @Test
    public void testCalculaMontoTotal_Con2Presupuestos1Detalle() throws Exception {
        unEgreso.agregaPresupuesto(presupuesto1);
        unEgreso.agregaPresupuesto(presupuesto2);
        Assert.assertEquals(unEgreso.montoTotal(), 16000, 0);
    }

    @Test
    public void testCargaPresupuesto_NoSePuedeCargarPresupuestoSinHaberCargadoEgreso() throws Exception {
        unEgreso.setDetalleValidable(new DetalleCompra(null, null));
        try {
            unEgreso.agregaPresupuesto(presupuesto1);
            Assert.fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testEstandarizacion_SeMuestraQueAlmacenadorTraePaisesDeLaApi() throws Exception {
        InfoMercadoLibre storage = InfoMercadoLibre.instancia();
        //Para mostrar por consola que muestra... descomentar

        /*storage.getPaises().stream().forEach(pais ->{
            System.out.println(pais.mostraNombre());
            pais.mostrarProvincias().forEach(provincia -> {
                System.out.println(provincia.mostraNombre());
            });
        });
        */
        Assert.assertTrue(!storage.getListaDePaises().isEmpty() && !storage.getListaDeMonedas().isEmpty());
    }
}