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

class Generador {




    Detalle generaDetalle(){
        Item item1;
        Item item2;
        Item item3;

        item1 = new Item(5000,"una maquina");
        item2 = new Item(1000,"pack de hojas A4");
        item3 = new Item(10000,"un item de curiosa procedencia");

        Detalle unDetalle;
        unDetalle=new Detalle();
        unDetalle.agregaItem(item1);
        unDetalle.agregaItem(item2);
        unDetalle.agregaItem(item3);

        return unDetalle;
    }

    OperacionEgreso generaEgreso(int cantMinimaPresupuesto){

        Proveedor unProveedor = new Proveedor(null,null,1231694,12356464,null);
        //MedioDePago unMedio = new MedioDePago(null);
        Detalle unDetalle = this.generaDetalle();
        Criterio unCriterio = new Criterio("Proyectito");
        unCriterio.agregateCategoria(new CategoriaOperacion("Proyectazo"));

        return new OperacionEgreso(unProveedor,null,null,unDetalle,1,unCriterio.getCategorias(),null);
    }

    OperacionEgreso generaEgresoConPresupuestos(int cantMinimaPresupuesto,int cantPresupuestos) throws Exception {
        Detalle unDetalle;
        OperacionEgreso unEgreso;

        unDetalle = this.generaDetalle();
        unEgreso = this.generaEgreso(cantMinimaPresupuesto);
        unEgreso.setDetalle(unDetalle);
        Presupuesto unPresupuesto = this.generaPresupuesto();

        for(int i = 0; i<cantMinimaPresupuesto;i++) unEgreso.agregaPresupuesto(unPresupuesto);

        return unEgreso;
    }

    Presupuesto generaPresupuesto(){
       Detalle unDetallePresupuesto = new Generador().generaDetalle();
        Criterio unCriterio = new Criterio("Proyectito");
        unCriterio.agregateCategoria(new CategoriaOperacion("Super Proyectito"));
       return new Presupuesto(unDetallePresupuesto,unCriterio.getCategorias(),null);
    }

    OperacionIngreso generaIngreso(long valorIngreso){

        return new OperacionIngreso(valorIngreso,"Inversion de dudosa procedencia",null);
    }

    Usuario generaUsuarioEstandar(){
        return new Usuario("User","U23R274ND4R",new RolEstandar(),this.generaEmpresa());
    }

    Usuario generaUsuarioAdmin(){
        return new Usuario("Admin","El4dm1n",new RolAdministrador(),this.generaEmpresa());
    }

    Empresa generaEmpresa(){
        Sector unSector =new Sector("comercio","Algo de comercio");

        unSector.agregateCategoria(new Categoria(132,321,"Categoria"));

        return new Empresa("unaEmpresa S.A","La empresa",302222221
                ,"una simple empresa","Av.Siempre Empresa",1231231232
                ,"Solo generamos empresas",unSector,5000,1000000);
    }

}
