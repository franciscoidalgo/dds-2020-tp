import APIMercadoLibre.services.ServicioMercadoLibre;
import DireccionPostal.Direccion;
import DireccionPostal.DireccionPostal;
import Entidad.CategorizacionEmpresa.Categoria;
import Entidad.CategorizacionEmpresa.Sector;
import Entidad.CategorizacionOperacion.CategoriaOperacion;
import Entidad.CategorizacionOperacion.Criterio;
import Entidad.Empresa;
import Operacion.Egreso.*;
import Operacion.Ingreso.OperacionIngreso;
import Usuario.RolAdministrador;
import Usuario.RolEstandar;
import Usuario.Usuario;
import Validadores.CriterioValidacionCantidadPresupuesto;
import Validadores.CriterioValidacionDetalle;
import Validadores.CriterioValidacionSeleccion;
import Validadores.ValidadorDeTransparencia;

import java.io.IOException;

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

        item1 = new Item(5000,"una maquina");
        item2 = new Item(1000,"pack de hojas A4");
        item3 = new Item(10000,"un item de curiosa procedencia");

        Solicitud unSolicitud;
        unSolicitud =new Solicitud();
        unSolicitud.agregaItem(item1);
        unSolicitud.agregaItem(item2);
        unSolicitud.agregaItem(item3);

        return unSolicitud;
    }

    OperacionEgreso generaEgreso(int cantMinimaPresupuesto){

        Proveedor unProveedor = new Proveedor(null,null,1231694,12356464,null);
        //MedioDePago unMedio = new MedioDePago(null);
        Solicitud unaSolicitud = this.generaSolicitud();
        Criterio unCriterio = new Criterio("Proyectito");
        unCriterio.agregateCategoria(new CategoriaOperacion("Proyectazo"));
        DetalleCompra unDetalle = new DetalleCompra(unaSolicitud,unProveedor);
        unDetalle.setCategoriaOperacion(unCriterio.getCategorias());
        return new OperacionEgreso(null,unDetalle,null);
    }

    OperacionEgreso generaEgresoConPresupuestos(int cantMinimaPresupuesto,int cantPresupuestos) throws Exception {
        Solicitud unaSolicitud;
        OperacionEgreso unEgreso;

        Proveedor unProveedor = new Proveedor(null,null,1231694,12356464,null);
        unaSolicitud = this.generaSolicitud();
        unEgreso = this.generaEgreso(cantMinimaPresupuesto);

        DetalleCompra unDetalle = new DetalleCompra(unaSolicitud,unProveedor);
        Presupuesto unPresupuesto = this.generaPresupuesto();

        for(int i = 0; i<cantMinimaPresupuesto;i++) unEgreso.agregaPresupuesto(unPresupuesto);

        return unEgreso;
    }

    Presupuesto generaPresupuesto(){
       Solicitud unaSolicitudPresupuesto = new Generador().generaSolicitud();
        Criterio unCriterio = new Criterio("Proyectito");
        unCriterio.agregateCategoria(new CategoriaOperacion("Super Proyectito"));
        DetalleCompra unDetalle = new DetalleCompra(unaSolicitudPresupuesto,null);
        unDetalle.setCategoriaOperacion(unCriterio.getCategorias());
       return new Presupuesto(unDetalle);
    }

    OperacionIngreso generaIngreso(long valorIngreso){

        return new OperacionIngreso(valorIngreso,"Inversion de dudosa procedencia",null);
    }

    //No deberia ser el servicio de mercado libre el que genere la direccion, a mercadolibre solo le pedimos data de provincias
    //y paises
    /*
    Usuario generaUsuarioEstandar() throws IOException {
        return new Usuario("User","U23R274ND4R",new RolEstandar(),this.generaEmpresa());
    }

    Usuario generaUsuarioAdmin() throws IOException {
        return new Usuario("Admin","El4dm1n",new RolAdministrador(),this.generaEmpresa());
    }

    Empresa generaEmpresa() throws IOException {
        Sector unSector =new Sector("comercio","Algo de comercio");
        DireccionPostal dir = this.generaDireccion("Av.Siempre Empresa");
        unSector.agregateCategoria(new Categoria(132,321,"Categoria"));

        return new Empresa("unaEmpresa S.A","La empresa",302222221
                ,"una simple empresa",dir,1231231232
                ,"Solo generamos empresas",unSector,5000,1000000);
    }

    DireccionPostal generaDireccion(String calle) throws IOException {
        ServicioMercadoLibre servicioMercadoLibre = ServicioMercadoLibre.instancia();
        Direccion direccionEstado = (Direccion) servicioMercadoLibre.generaDireccion("UY","UY-RO","TUxVQ0NBQjY1MmQ1");
        return new DireccionPostal(calle,"","",direccionEstado);
    }
*/
}
