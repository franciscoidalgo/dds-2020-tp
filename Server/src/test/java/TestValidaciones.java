import com.google.gson.Gson;
import domain.Entidad.Empresa;
import domain.Entidad.Usuario.Usuario;
import domain.Operacion.Egreso.*;
import domain.Validadores.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestValidaciones {

   private OperacionEgreso unEgreso;

    private Presupuesto presupuesto1;
    private Presupuesto presupuesto2;
    private Presupuesto presupuesto3;
    private DetalleOperacion pedidoP1;
    private DetalleOperacion pedidoP2;
    private DetalleOperacion pedidoP3;
    private DetalleOperacion pedidoEgreso;
    private Proveedor   pedro;
    private Proveedor   pablo;
    private Proveedor   simon;


    private CriterioValidacionCantidadPresupuesto criterioCantPresupuesto;
    private CriterioValidacionDetalle criterioDetalle;
    private CriterioValidacionSeleccion criterioSeleccion;


    private ValidadorDeTransparencia validadorDeTransparencia;

    @Before
    public void setUp() throws Exception {
        /* Inicializo Proveedores */
        pedro = new Proveedor("Pedro","Pedro S.A",99999999,999999999,null);
        pablo = new Proveedor("Pablo","Pablo Bros",88888888,888888888,null);
        simon = new Proveedor("Simon","Simon SRL",77777777,777777777,null);

        /* Inicializo Detalles*/
        pedidoP1 = new DetalleOperacion();
        pedidoP2 = new DetalleOperacion();
        pedidoP3 = new DetalleOperacion();
        pedidoEgreso = new DetalleOperacion();

        pedidoP1.setProveedor(pedro);
        pedidoP1.agregaItem(new Item("Hojas"));
        pedidoP1.agregaItem(new Item("Lapiceras"));
        pedidoP1.agregaItem(new Item("Carpetas"));

        pedidoP2.setProveedor(pablo);
        pedidoP2.agregaItem(new Item("Hojas"));
        pedidoP2.agregaItem(new Item("Lapiceras"));
        pedidoP2.agregaItem(new Item("Carpetas"));

        pedidoP3.setProveedor(simon);
        pedidoP3.agregaItem(new Item("Hojas"));
        pedidoP3.agregaItem(new Item("Lapiceras"));
        pedidoP3.agregaItem(new Item("Carpetas"));

        pedidoEgreso.setProveedor(pedro);
        pedidoEgreso.agregaItem(new Item("Hojas"));
        pedidoEgreso.agregaItem(new Item("Lapiceras"));
        pedidoEgreso.agregaItem(new Item("Carpetas"));

        /*Inicializo Presupuestos*/
        presupuesto1 = new Presupuesto(pedidoP1,1000);
        presupuesto2 = new Presupuesto(pedidoP2,10000);
        presupuesto3 = new Presupuesto(pedidoP3,1000000);


        /*Inicializo Egreso*/
        unEgreso = new OperacionEgreso(null,null,1000);
        unEgreso.setDetalle(pedidoEgreso);
        unEgreso.agregaPresupuesto(presupuesto1);
        unEgreso.agregaPresupuesto(presupuesto2);
        unEgreso.agregaPresupuesto(presupuesto3);


        /*Inicializo Validador de transparencia*/
        validadorDeTransparencia = ValidadorDeTransparencia.instancia();
        criterioCantPresupuesto = new CriterioValidacionCantidadPresupuesto();
        criterioDetalle = new CriterioValidacionDetalle();
        criterioSeleccion = new CriterioValidacionSeleccion();

        validadorDeTransparencia.agregateCriterio(criterioSeleccion);
        validadorDeTransparencia.agregateCriterio(criterioDetalle);
        validadorDeTransparencia.agregateCriterio(criterioCantPresupuesto);

    }


    @Test
    public void testHayTresPresupuestosCargados(){
        System.out.println(new Gson().toJson(unEgreso));
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
        unEgreso.getDetalle().setProveedor(pablo);
        Assert.assertFalse(validadorDeTransparencia.validaEgreso(unEgreso));
    }

    @Test
    public void testTransparencia_EgresoNoCumpleConCantMinPresupuestos() throws Exception {
        Presupuesto presupuesto4;
        presupuesto4 = new Presupuesto(pedidoP3,5);
        pedidoP3.setProveedor(pablo);
        unEgreso.agregaPresupuesto(presupuesto4);

         Assert.assertFalse(validadorDeTransparencia.validaEgreso(unEgreso));
    }

    @Test
    public void testTransparencia_EgresoNoTieneMismosItemsQueSusPresupuestos(){
        pedidoEgreso.agregaItem(new Item("Zapatos"));
        Assert.assertFalse(validadorDeTransparencia.validaEgreso(unEgreso));
    }

    @Test
    public void testUsuarioRecibeMensaje_CuandoSeDaDeAltaEnOperacion() throws InterruptedException {
        Usuario unEstandar = new Usuario();
        unEstandar.darseDeAltaEn(unEgreso);
        unEgreso.validaOperacion();
        Thread.sleep(5000);

        System.out.println(unEstandar.getBandejaDeMensajes().mostraMensaje(0).getMensaje());
        System.out.println(unEstandar.getBandejaDeMensajes().mostraMensaje(1).getAsunto());
        System.out.println(unEstandar.getBandejaDeMensajes().mostraMensaje(1).getMensaje());
        Assert.assertEquals(unEstandar.getBandejaDeMensajes().getMensajes().size(),2);

    }
}