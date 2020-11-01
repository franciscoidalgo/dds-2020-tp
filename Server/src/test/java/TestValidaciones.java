import com.google.gson.Gson;
import domain.DireccionPostal.DireccionPostal;
import domain.Entidad.Entidad;
import domain.Entidad.EntidadJuridica;
import domain.Entidad.Organizacion;
import domain.Entidad.OrganizacionSocial;
import domain.Entidad.Usuario.Usuario;
import domain.Operacion.Egreso.*;
import domain.Validadores.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repositorios.Repositorio;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepo;
import repositorios.factories.FactoryRepoUsuario;


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
    private DireccionPostal dir;

    private CriterioValidacionCantidadPresupuesto criterioCantPresupuesto;
    private CriterioValidacionDetalle criterioDetalle;
    private CriterioValidacionSeleccion criterioSeleccion;


    private ValidadorDeTransparencia validadorDeTransparencia;

    @Before
    public void setUp() throws Exception {
        /* Inicializo Proveedores */
        dir = new DireccionPostal("pais", "ciudad", "provincia", "calle",2000, "piso","dpto");
        pedro = new Proveedor("Pedro S.A",999999999,dir);
        pablo = new Proveedor("Pablo Bros",888888888,dir);
        simon = new Proveedor("Simon SRL",777777777,dir);

        /* Inicializo Detalles*/
        TipoDeItem tipo = new TipoDeItem("test");
        pedidoP1 = new DetalleOperacion();
        pedidoP2 = new DetalleOperacion();
        pedidoP3 = new DetalleOperacion();
        pedidoEgreso = new DetalleOperacion();

        pedidoP1.setProveedor(pedro);
        pedidoP1.agregaPedido(new Pedido(new Item("Hojas",tipo, (float)200),300));
        pedidoP1.agregaPedido(new Pedido(new Item("Lapiceras",tipo, (float) 25),200));
        pedidoP1.agregaPedido(new Pedido(new Item("Carpetas",tipo, (float) 25),25));

        pedidoP2.setProveedor(pablo);
        pedidoP2.agregaPedido(new Pedido(new Item("Hojas",tipo, (float)500),300));
        pedidoP2.agregaPedido(new Pedido(new Item("Lapiceras",tipo, (float) 200),200));
        pedidoP2.agregaPedido(new Pedido(new Item("Carpetas",tipo, (float) 200),25));

        pedidoP3.setProveedor(simon);
        pedidoP1.agregaPedido(new Pedido(new Item("Hojas",tipo, (float)400),300));
        pedidoP1.agregaPedido(new Pedido(new Item("Lapiceras",tipo, (float) 25),200));
        pedidoP1.agregaPedido(new Pedido(new Item("Carpetas",tipo, (float) 25),25));

        pedidoEgreso.setProveedor(pedro);
        pedidoEgreso.agregaPedido(new Pedido(new Item("Hojas",tipo, (float)200),300));
        pedidoEgreso.agregaPedido(new Pedido(new Item("Lapiceras",tipo, (float) 25),200));
        pedidoEgreso.agregaPedido(new Pedido(new Item("Carpetas",tipo, (float) 25),25));

        /*Inicializo Presupuestos*/
        presupuesto1 = new Presupuesto(pedidoP1,1000);
        presupuesto2 = new Presupuesto(pedidoP2,10000);
        presupuesto3 = new Presupuesto(pedidoP3,1000000);


        /*Inicializo Egreso*/
        unEgreso = new OperacionEgreso(null, new MedioDePago("Pagares","Pesos"), null, 5);
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
    public void testPasaValidadoTransparencia() throws Exception {

        Assert.assertTrue(validadorDeTransparencia.validaEgreso(unEgreso));
    }

    @Test
    public void testTransparencia_EgresoNoTieneProveedorDeMenorPresupuesto() throws Exception {
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
    public void testTransparencia_EgresoNoTieneMismosItemsQueSusPresupuestos() throws Exception {
        pedidoEgreso.agregaPedido(new Pedido(new Item("zapatos",new TipoDeItem("Producto"), (float)200),300));

        Assert.assertFalse(validadorDeTransparencia.validaEgreso(unEgreso));
    }

    @Test
    public void testUsuarioRecibeMensaje_CuandoSeDaDeAltaEnOperacion() throws InterruptedException {
        RepositorioDeUsuarios repoUsers = FactoryRepoUsuario.get();
        Repositorio<Organizacion> organizacionRepositorio = FactoryRepo.get(Organizacion.class);
        Repositorio<OrganizacionSocial> organizacionSocialRepositorio = FactoryRepo.get(OrganizacionSocial.class);
        Usuario unEstandar = repoUsers.buscar(1);

        OrganizacionSocial organizacionSocial = new OrganizacionSocial("Organizacion Social S.A","nombre ficticio",999317799,"una descri",new DireccionPostal("Argentina","CABA","Provincia","Av Independencia 9999",1234,"2","b"));
        Organizacion organizacion = new Organizacion("Organizacion");

        organizacion.agregarEntidad(organizacionSocial);
        unEstandar.setEntidadPertenece(organizacionSocial);

        organizacionRepositorio.agregar(organizacion);
        organizacionSocialRepositorio.agregar(organizacionSocial);

        organizacionSocial.setOrganizacion(organizacion);
        repoUsers.modificar(unEstandar);

        Repositorio<OperacionEgreso> repoEgreso = FactoryRepo.get(OperacionEgreso.class);


        unEstandar.darseDeAltaEn(unEgreso);
        repoEgreso.agregar(unEgreso);
        unEgreso.validaOperacion();


        Thread.sleep(10000);

        System.out.println(unEstandar.getBandejaDeMensajes().mostraMensaje(0).getMensaje());
        System.out.println(unEstandar.getBandejaDeMensajes().mostraMensaje(1).getAsunto());
        System.out.println(unEstandar.getBandejaDeMensajes().mostraMensaje(1).getMensaje());
        Assert.assertEquals(unEstandar.getBandejaDeMensajes().getMensajes().size(),2);

    }



}