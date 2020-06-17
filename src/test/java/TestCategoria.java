import Entidad.Categoria;
import Entidad.Categorizador;
import Entidad.Empresa;
import Entidad.Sector;
import org.junit.Assert;
import org.junit.Test;

public class TestCategoria {

    @Test
    public void testCategorias(){

       Categorizador categorizador = new Categorizador();

        /* A modelo de ejemplo de las 20 instancias */
        Categoria microCom =    new Categoria(7,    29740000,   "Micro");
        Categoria pequenaCom =  new Categoria(35,   178860000,  "Pequena");
        Categoria mediana1Com = new Categoria(125,  1502750000,"Medianta-Tramo 1");
        Categoria mediana2Com = new Categoria(345,  2146810000,"Medianta-Tramo 2");


        /* Sectores */
        //Sector agropecuario = new Sector("Agropecuario","Vacas muchas vacas");
        Sector comercial = new Sector("Comercial","Mucha gente");
        //Sector construccion = new Sector("Construccion","Muchos ladrillos");
        //Sector servicio = new Sector("Servicio","Se corto el wifi de nuevo!");
        //Sector industria = new Sector("Industria","Muchas maquinitas... ah no era eso industrial?");

        comercial.agregateCategoria(microCom);
        comercial.agregateCategoria(pequenaCom);
        comercial.agregateCategoria(mediana1Com);
        comercial.agregateCategoria(mediana2Com);


        Empresa nyque = new Empresa(5,1000,comercial,categorizador);
        Empresa abidaz = new Empresa(5,150000000,comercial,categorizador);
        Empresa pumba = new Empresa(100,150000000,comercial,categorizador);
        Empresa toppre = new Empresa(300,2146810005,comercial,categorizador);
        Empresa piedra = new Empresa(1,2146810005,comercial,categorizador);


        Assert.assertEquals(nyque.getCategoria(),microCom);
        Assert.assertEquals(abidaz.getCategoria(),pequenaCom);
        Assert.assertEquals(pumba.getCategoria(),mediana1Com);
        Assert.assertEquals(toppre.getCategoria(),mediana2Com);
        Assert.assertEquals(piedra.getCategoria(),mediana2Com);


    }
}