import Entidad.CategorizacionEmpresa.Categoria;
import Entidad.CategorizacionEmpresa.Sector;
import Entidad.CategorizacionOperacion.Criterio;
import Entidad.Empresa;
import Operacion.Egreso.*;
import Operacion.Ingreso.OperacionIngreso;
import Usuario.RolEstandar;
import Usuario.RolAdministrador;
import Usuario.Usuario;

import java.time.LocalTime;

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

        Proveedor unProveedor = new Proveedor();
        MedioDePago unMedio = new MedioDePago("MaestroCard","123451234","1235123512354");
        Detalle unDetalle = this.generaDetalle();
        Criterio unCriterio = new Criterio("Proyectito");

        return new OperacionEgreso(1234567,LocalTime.now(),unProveedor,null,unMedio,unDetalle,cantMinimaPresupuesto,unCriterio);
    }

    OperacionEgreso generaEgresoConPresupuestos(int cantMinimaPresupuesto,int cantPresupuestos){

        OperacionEgreso unEgreso;
        unEgreso = this.generaEgreso(cantMinimaPresupuesto);
        Presupuesto unPresupuesto = this.generaPresupuesto();
        for(int i = 0; i<cantMinimaPresupuesto;i++) unEgreso.agregaPresupuesto(unPresupuesto);

        return unEgreso;
    }

    Presupuesto generaPresupuesto(){
       Detalle unDetallePresupuesto = new Generador().generaDetalle();
        Criterio unCriterio = new Criterio("Proyectito");
       return new Presupuesto(unDetallePresupuesto,unCriterio);
    }

    OperacionIngreso generaIngreso(long valorIngreso){

        return new OperacionIngreso(valorIngreso,123456,LocalTime.now(),
                "Inversion de dudosa procedencia");
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
