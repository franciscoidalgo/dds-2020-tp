import Operacion.Egreso.*;
import Validadores.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestValidaciones {

    OperacionEgreso unEgreso;

    Presupuesto presupuesto1;
    Presupuesto presupuesto2;
    Presupuesto presupuesto3;
    Detalle     detalleP1;
    Detalle     detalleP2;
    Detalle     detalleP3;
    Detalle     detalleEgreso;
    Proveedor   pedro;
    Proveedor   pablo;
    Proveedor   simon;

    CriterioValidacion criterioValidacion;
    ValidadorDeTransparencia validadorDeTransparencia;



    @Before
    public void setUp() throws Exception {
        /* Inicializo Proveedores */
        pedro = new Proveedor("Pedro","Pedro S.A",99999999,999999999,"av Pedro 1234");
        pablo = new Proveedor("Pablo","Pablo Bros",88888888,888888888,"Pablo 9876");
        simon = new Proveedor("Simon","Simon SRL",77777777,777777777,"Simon 4567");

        /* Inicializo Detalles*/
        detalleP1 = new Detalle();
        detalleP2 = new Detalle();
        detalleP3 = new Detalle();
        detalleEgreso = new Detalle();


        detalleP1.agregaItem(new Item(100,"Hojas"));
        detalleP1.agregaItem(new Item(200,"Lapiceras"));
        detalleP1.agregaItem(new Item(300,"Carpetas"));


        detalleP2.agregaItem(new Item(150,"Hojas"));
        detalleP2.agregaItem(new Item(250,"Lapiceras"));
        detalleP2.agregaItem(new Item(350,"Carpetas"));

        detalleP3.agregaItem(new Item(500,"Hojas"));
        detalleP3.agregaItem(new Item(600,"Lapiceras"));
        detalleP3.agregaItem(new Item(700,"Carpetas"));

        detalleEgreso.agregaItem(new Item(100,"Hojas"));
        detalleEgreso.agregaItem(new Item(200,"Lapiceras"));
        detalleEgreso.agregaItem(new Item(300,"Carpetas"));

        /*Inicializo Presupuestos*/
        presupuesto1 = new Presupuesto(detalleP1,null,pedro);
        presupuesto2 = new Presupuesto(detalleP2,null,pablo);
        presupuesto3 = new Presupuesto(detalleP3,null,simon);


        /*Inicializo Egreso*/
        unEgreso = new OperacionEgreso(pedro,null,null,null,null,null);
        unEgreso.setDetalle(detalleEgreso);
        unEgreso.agregaPresupuesto(presupuesto1);
        unEgreso.agregaPresupuesto(presupuesto2);
        unEgreso.agregaPresupuesto(presupuesto3);


        /*Inicializo Validador de transparencia*/
        validadorDeTransparencia = new ValidadorDeTransparencia();
    }


    @Test
    public void testHayTresPresupuestosCargados(){
        Assert.assertEquals(unEgreso.getPresupuestos().size(),3);
    }

    @Test
    public void testPasaValidacion_cantMinimaPresupuestaValido(){
        criterioValidacion = new ValidarCantidadPresupuesto();

        Assert.assertTrue(criterioValidacion.validaEgreso(unEgreso));
    }

    @Test
    public void testPasaValidacion_coincidenDetalles(){
        criterioValidacion = new ValidarDetalle();

        Assert.assertTrue(criterioValidacion.validaEgreso(unEgreso));
    }

    @Test
    public void testPasaValidacion_seleccionCorrectaDePresupuesto(){
        criterioValidacion = new ValidarCriterioSeleccion();

        Assert.assertTrue(criterioValidacion.validaEgreso(unEgreso));
    }

    @Test
    public void testPasaValidadoTransparencia(){
        Assert.assertTrue(validadorDeTransparencia.validaEgreso(unEgreso));
    }

    @Test
    public void testTransparencia_EgresoNoTieneProveedorDeMenorPresupuesto(){
        unEgreso.setProveedor(pablo);
        detalleEgreso.agregaItem(new Item(150,"Hojas"));
        detalleEgreso.agregaItem(new Item(250,"Lapiceras"));
        detalleEgreso.agregaItem(new Item(350,"Carpetas"));

        Assert.assertFalse(validadorDeTransparencia.validaEgreso(unEgreso));
    }

    @Test
    public void testTransparencia_EgresoNoCumpleConCantMinPresupuestos() throws Exception {
        Presupuesto presupuesto4;
        presupuesto4 = new Presupuesto(detalleP3,null,pablo);
        unEgreso.agregaPresupuesto(presupuesto4);

         Assert.assertFalse(validadorDeTransparencia.validaEgreso(unEgreso));
    }

    @Test
    public void testTransparencia_EgresoDebeEstarAsociadoAUnPresupuesto(){
        detalleEgreso.agregaItem(new Item(600,"Carpetas"));
        Assert.assertFalse(validadorDeTransparencia.validaEgreso(unEgreso));
    }

}