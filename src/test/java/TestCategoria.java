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
    public void testNyqueMicro(){
        nyque = new Empresa("Trucho Hnos","Nyque S.A" ,302229999,"Zapatilla de calidad","Truchin 1234",1654987, "Vendemos zapatillas", comercial,5,1000);
        Assert.assertEquals(nyque.getCategoria(),microCom);
    }
    @Test
    public void testNyqueMT2_SuperaMedianaT2(){
        nyque = new Empresa("Trucho Hnos","Nyque S.A" ,302229999,"Zapatilla de calidad","Truchin 1234",1654987, "Vendemos zapatillas", comercial,100300,2146910005);
        Assert.assertEquals(nyque.getCategoria(),mediana2Com);
    }
    @Test
    public void testNyquePeq_ConPersonalMicroVentaPequena(){
        nyque = new Empresa("Trucho Hnos","Nyque S.A" ,302229999,"Zapatilla de calidad","Truchin 1234",1654987, "Vendemos zapatillas", comercial,5,150000000);
        Assert.assertEquals(nyque.getCategoria(),pequenaCom);
    }
    @Test
    public void testNyqueMT1_ConPersonalMedianaT1VentasPequena(){
        nyque = new Empresa("Trucho Hnos","Nyque S.A" ,302229999,"Zapatilla de calidad","Truchin 1234",1654987, "Vendemos zapatillas", comercial,100,150000000);
        Assert.assertEquals(nyque.getCategoria(),mediana1Com);
    }
    @Test
    public void testNyqueMT2_PersonalMedianaT1VentasSuperaMedianaT2(){
        nyque = new Empresa("Trucho Hnos","Nyque S.A" ,302229999,"Zapatilla de calidad","Truchin 1234",1654987, "Vendemos zapatillas", comercial,300,2146810005);
        Assert.assertEquals(nyque.getCategoria(),mediana2Com);
    }

    @Test
    public void testNyqueMT1_PersonalMicroVentasMedianaT1(){
        nyque = new Empresa("Trucho Hnos","Nyque S.A" ,302229999,"Zapatilla de calidad","Truchin 1234",1654987, "Vendemos zapatillas", comercial,1,1502740000);
        Assert.assertEquals(nyque.getCategoria(),mediana1Com);

    }

}