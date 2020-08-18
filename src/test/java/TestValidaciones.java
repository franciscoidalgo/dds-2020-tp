import Operacion.Egreso.*;
import Validadores.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestValidaciones {

   private OperacionEgreso unEgreso;

    private Presupuesto presupuesto1;
    private Presupuesto presupuesto2;
    private Presupuesto presupuesto3;
    private Solicitud solicitudP1;
    private Solicitud solicitudP2;
    private Solicitud solicitudP3;
    private Solicitud solicitudEgreso;
    private Proveedor   pedro;
    private Proveedor   pablo;
    private Proveedor   simon;


    private CriterioValidacionCantidadPresupuesto criterioCantPresupuesto;
    private CriterioValidacionDetalle criterioDetalle;
    private CriterioValidacionSeleccion criterioSeleccion;


    private ValidadorDeTransparencia validadorDeTransparencia;
    private Generador generador;


    @Before
    public void setUp() throws Exception {
        /* Inicializo Proveedores */
        pedro = new Proveedor("Pedro","Pedro S.A",99999999,999999999,"av Pedro 1234");
        pablo = new Proveedor("Pablo","Pablo Bros",88888888,888888888,"Pablo 9876");
        simon = new Proveedor("Simon","Simon SRL",77777777,777777777,"Simon 4567");

        /* Inicializo Detalles*/
        solicitudP1 = new Solicitud();
        solicitudP2 = new Solicitud();
        solicitudP3 = new Solicitud();
        solicitudEgreso = new Solicitud();


        solicitudP1.agregaItem(new Item(100,"Hojas"));
        solicitudP1.agregaItem(new Item(200,"Lapiceras"));
        solicitudP1.agregaItem(new Item(300,"Carpetas"));


        solicitudP2.agregaItem(new Item(150,"Hojas"));
        solicitudP2.agregaItem(new Item(250,"Lapiceras"));
        solicitudP2.agregaItem(new Item(350,"Carpetas"));

        solicitudP3.agregaItem(new Item(500,"Hojas"));
        solicitudP3.agregaItem(new Item(600,"Lapiceras"));
        solicitudP3.agregaItem(new Item(700,"Carpetas"));

        solicitudEgreso.agregaItem(new Item(100,"Hojas"));
        solicitudEgreso.agregaItem(new Item(200,"Lapiceras"));
        solicitudEgreso.agregaItem(new Item(300,"Carpetas"));

        /*Inicializo Presupuestos*/
        presupuesto1 = new Presupuesto(new DetalleCompra(solicitudP1,pedro));
        presupuesto2 = new Presupuesto(new DetalleCompra(solicitudP2,pablo));
        presupuesto3 = new Presupuesto(new DetalleCompra(solicitudP3,simon));


        /*Inicializo Egreso*/
        unEgreso = new OperacionEgreso(null,null,null);
        unEgreso.setDetalleValidable(new DetalleCompra(solicitudEgreso,pedro));
        unEgreso.agregaPresupuesto(presupuesto1);
        unEgreso.agregaPresupuesto(presupuesto2);
        unEgreso.agregaPresupuesto(presupuesto3);


        /*Inicializo Validador de transparencia*/
        generador = Generador.instancia();
        generador.inicializaValidadorTransparencia();
        validadorDeTransparencia = ValidadorDeTransparencia.instancia();
        criterioCantPresupuesto = new CriterioValidacionCantidadPresupuesto();
        criterioDetalle = new CriterioValidacionDetalle();
        criterioSeleccion = new CriterioValidacionSeleccion();



    }


    @Test
    public void testHayTresPresupuestosCargados(){
        Assert.assertEquals(unEgreso.getPresupuestos().size(),3);
    }

    @Test
    public void testPasaValidacion_cantMinimaPresupuestaValido(){

        Assert.assertTrue(criterioCantPresupuesto.validaEgreso(unEgreso));
    }

    @Test
    public void testPasaValidacion_coincidenDetalles(){

        Assert.assertTrue(criterioDetalle.validaEgreso(unEgreso));
    }

    @Test
    public void testPasaValidacion_seleccionCorrectaDePresupuesto(){

        Assert.assertTrue(criterioSeleccion.validaEgreso(unEgreso));
    }

    @Test
    public void testPasaValidadoTransparencia(){
        Assert.assertTrue(validadorDeTransparencia.validaEgreso(unEgreso));
    }

    @Test
    public void testTransparencia_EgresoNoTieneProveedorDeMenorPresupuesto(){
        unEgreso.getDetalleValidable().setProveedor(pablo);
        solicitudEgreso.agregaItem(new Item(150,"Hojas"));
        solicitudEgreso.agregaItem(new Item(250,"Lapiceras"));
        solicitudEgreso.agregaItem(new Item(350,"Carpetas"));

        Assert.assertFalse(validadorDeTransparencia.validaEgreso(unEgreso));
    }

    @Test
    public void testTransparencia_EgresoNoCumpleConCantMinPresupuestos() throws Exception {
        Presupuesto presupuesto4;
        presupuesto4 = new Presupuesto(new DetalleCompra(solicitudP3,pablo));
        unEgreso.agregaPresupuesto(presupuesto4);

         Assert.assertFalse(validadorDeTransparencia.validaEgreso(unEgreso));
    }

    @Test
    public void testTransparencia_EgresoDebeEstarAsociadoAUnPresupuesto(){
        solicitudEgreso.agregaItem(new Item(600,"Carpetas"));
        Assert.assertFalse(validadorDeTransparencia.validaEgreso(unEgreso));
    }

}