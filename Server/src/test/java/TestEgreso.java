import domain.Operacion.Egreso.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestEgreso {

    OperacionEgreso unEgreso;
    DetalleOperacion detalleOperacion;
    Presupuesto presupuesto1;
    Presupuesto presupuesto2;



    @Before
    public void setUp() throws Exception {

        //Presupuesto1
        presupuesto1 = new Presupuesto();
        presupuesto1.setMontoTotal(1000);

        //Presupuesto2
        presupuesto2 = new Presupuesto();
        presupuesto2.setMontoTotal(1500);

        //Egreso ya viene con un detalle generado
        unEgreso = new OperacionEgreso();
        unEgreso.setMontoTotal(1500);
        detalleOperacion = new DetalleOperacion();
        detalleOperacion.agregaPedido(new Pedido(new Item("lapicera",new TipoDeItem("Producto"), 20.50),100));
        unEgreso.setDetalle(detalleOperacion);
    }


    @Test
    public void testMontoTotal() throws Exception {
        unEgreso.agregaPresupuesto(presupuesto1);
        unEgreso.agregaPresupuesto(presupuesto2);
        Assert.assertEquals(unEgreso.montoTotal(), 1500, 0);
    }

    @Test
    public void testCargaPresupuesto_NoSePuedeCargarPresupuestoSinHaberCargadoEgreso() {
        unEgreso.setDetalle(null);
        try {
            unEgreso.agregaPresupuesto(presupuesto1);
            Assert.fail();
        } catch (Exception ignored) {
        }
    }

  /*  @Test
    public void testEstandarizacion_SeMuestraQueAlmacenadorTraePaisesDeLaApi() throws Exception {
        InfoMercadoLibre storage = InfoMercadoLibre.instancia();
        //Para mostrar por consola que muestra... descomentar

        /*storage.getPaises().stream().forEach(pais ->{
            System.out.println(pais.mostraNombre());
            pais.mostrarProvincias().forEach(provincia -> {
                System.out.println(provincia.mostraNombre());
            });
        });
        *//*
        Assert.assertTrue(!storage.getListaDePaises().isEmpty() && !storage.getListaDeMonedas().isEmpty());
    }*/
}