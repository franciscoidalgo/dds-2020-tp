import Entidad.CategorizacionEmpresa.Categoria;
import Entidad.CategorizacionEmpresa.Sector;
import Entidad.Empresa;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCategoria {

    Categoria microCom ;
    Categoria pequenaCom;
    Categoria mediana1Com;
    Categoria mediana2Com;

    Sector comercial;

    Empresa nyque;

    @Before
    public void setUp() throws Exception {
        /* A modelo de ejemplo de las 20 instancias */
        microCom = new Categoria(7, 29740000, "Micro");
        pequenaCom = new Categoria(35, 178860000, "Pequena");
        mediana1Com = new Categoria(125, 1502750000, "Medianta-Tramo 1");
        mediana2Com = new Categoria(345, 2146810000, "Medianta-Tramo 2");

        /* Sectores */
        comercial = new Sector("Comercial", "Mucha gente");

        comercial.agregateCategoria(microCom);
        comercial.agregateCategoria(pequenaCom);
        comercial.agregateCategoria(mediana1Com);
        comercial.agregateCategoria(mediana2Com);
    }

    @Test
    public void testNyqueComoMicro(){
        nyque = new Empresa(5, 1000, comercial);
        Assert.assertEquals(nyque.getCategoria(),microCom);
    }
    @Test
    public void testNyqueSuperaMedianaT2(){
        nyque = new Empresa(100300, 2146910005, comercial);
        Assert.assertEquals(nyque.getCategoria(),mediana2Com);
    }
    @Test
    public void testNyqueConPersonalMicroVentaPequena(){
        nyque = new Empresa(5, 150000000, comercial);
        Assert.assertEquals(nyque.getCategoria(),pequenaCom);
    }
    @Test
    public void testNyqueConPersonalMedianaT1VentasPequena(){
        nyque = new Empresa(100, 150000000, comercial);
        Assert.assertEquals(nyque.getCategoria(),mediana1Com);
    }
    @Test
    public void testNyquePersonalMedianaT1VentasSuperaMedianaT2(){
        nyque = new Empresa(300, 2146810005, comercial);
        Assert.assertEquals(nyque.getCategoria(),mediana2Com);
    }

    @Test
    public void testNyquePersonalMicroVentasMedianaT1(){
        nyque = new Empresa(1, 1502740000, comercial);
        Assert.assertEquals(nyque.getCategoria(),mediana1Com);

    }

}