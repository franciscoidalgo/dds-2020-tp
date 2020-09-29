import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;
import domain.Entidad.CategorizacionOperacion.Criterio;
import domain.Operacion.Egreso.*;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Validadores.CriterioValidacionCantidadPresupuesto;
import domain.Validadores.CriterioValidacionDetalle;
import domain.Validadores.CriterioValidacionSeleccion;
import domain.Validadores.ValidadorDeTransparencia;

class Generador {
    private static Generador instancia=null;



    ValidadorDeTransparencia validadorDeTransparencia;
    public Generador(){}

    public static Generador instancia(){
        if (instancia == null) {
            instancia = new Generador();
        }
        return instancia;
    }

    public void inicializaValidadorTransparencia(){
        CriterioValidacionCantidadPresupuesto criterioCantPresupuesto;
        CriterioValidacionDetalle criterioDetalle;
        CriterioValidacionSeleccion criterioSeleccion;
        validadorDeTransparencia = ValidadorDeTransparencia.instancia();
        if(validadorDeTransparencia.getCriteriosValidadores().isEmpty()) {
            criterioCantPresupuesto = new CriterioValidacionCantidadPresupuesto();
            criterioDetalle = new CriterioValidacionDetalle();
            criterioSeleccion = new CriterioValidacionSeleccion();
            validadorDeTransparencia.agregateCriterio(criterioCantPresupuesto);
            validadorDeTransparencia.agregateCriterio(criterioDetalle);
            validadorDeTransparencia.agregateCriterio(criterioSeleccion);
        }
    }

    Solicitud generaSolicitud(){
        Item item1;
        Item item2;
        Item item3;

        item1 = new Item("una maquina");
        item2 = new Item("pack de hojas A4");
        item3 = new Item("un item de curiosa procedencia");

        Solicitud unSolicitud;
        unSolicitud =new Solicitud();
        unSolicitud.agregaItem(item1);
        unSolicitud.agregaItem(item2);
        unSolicitud.agregaItem(item3);

        return unSolicitud;
    }

    OperacionEgreso generaEgreso(double montoTotal){

        Proveedor unProveedor = new Proveedor(null,null,1231694,12356464,null);
        //MedioDePago unMedio = new MedioDePago(null);
        Solicitud unaSolicitud = this.generaSolicitud();
        Criterio unCriterio = new Criterio("Proyectito");
        unCriterio.agregateCategoria(new CategoriaOperacion("Proyectazo"));
        DetalleCompra unDetalle = new DetalleCompra(unaSolicitud,unProveedor);
        unDetalle.setCategoriaOperacion(unCriterio.getCategorias());
        return new OperacionEgreso(null,unDetalle,montoTotal);
    }

    OperacionEgreso generaEgresoConPresupuestos(int cantMinimaPresupuesto,int cantPresupuestos) throws Exception {
        Solicitud unaSolicitud;
        OperacionEgreso unEgreso;

        Proveedor unProveedor = new Proveedor(null,null,1231694,12356464,null);
        unaSolicitud = this.generaSolicitud();
        unEgreso = this.generaEgreso(cantMinimaPresupuesto);

        DetalleCompra unDetalle = new DetalleCompra(unaSolicitud,unProveedor);
        Presupuesto unPresupuesto = this.generaPresupuesto(1000);

        for(int i = 0; i<cantMinimaPresupuesto;i++) unEgreso.agregaPresupuesto(unPresupuesto);

        return unEgreso;
    }

    Presupuesto generaPresupuesto(double montoTotal){
       Solicitud unaSolicitudPresupuesto = new Generador().generaSolicitud();
        Criterio unCriterio = new Criterio("Proyectito");
        unCriterio.agregateCategoria(new CategoriaOperacion("Super Proyectito"));
        DetalleCompra unDetalle = new DetalleCompra(unaSolicitudPresupuesto,null);
        unDetalle.setCategoriaOperacion(unCriterio.getCategorias());
       return new Presupuesto(unDetalle,montoTotal);
    }

    OperacionIngreso generaIngreso(long valorIngreso){

        return new OperacionIngreso(valorIngreso,"Inversion de dudosa procedencia");
    }

    //No deberia ser el servicio de mercado libre el que genere la direccion, a mercadolibre solo le pedimos data de provincias
    //y paises
    /*
    domain.Usuario generaUsuarioEstandar() throws IOException {
        return new domain.Usuario("User","U23R274ND4R",new RolEstandar(),this.generaEmpresa());
    }

    domain.Usuario generaUsuarioAdmin() throws IOException {
        return new domain.Usuario("Admin","El4dm1n",new RolAdministrador(),this.generaEmpresa());
    }

    Empresa generaEmpresa() throws IOException {
        Sector unSector =new Sector("comercio","Algo de comercio");
        domain.DireccionPostal dir = this.generaDireccion("Av.Siempre Empresa");
        unSector.agregateCategoria(new Categoria(132,321,"Categoria"));

        return new Empresa("unaEmpresa S.A","La empresa",302222221
                ,"una simple empresa",dir,1231231232
                ,"Solo generamos empresas",unSector,5000,1000000);
    }

    domain.DireccionPostal generaDireccion(String calle) throws IOException {
        ServicioMercadoLibre servicioMercadoLibre = ServicioMercadoLibre.instancia();
        Direccion direccionEstado = (Direccion) servicioMercadoLibre.generaDireccion("UY","UY-RO","TUxVQ0NBQjY1MmQ1");
        return new domain.DireccionPostal(calle,"","",direccionEstado);
    }
*/
}
