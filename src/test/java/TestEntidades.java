import Entidad.CategorizacionOperacion.CategoriaOperacion;
import Entidad.CategorizacionOperacion.Criterio;
import Entidad.Empresa;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestEntidades {
    Empresa empresa1;

    Criterio criterio;
    @Before

    public void setUp(){

        empresa1 = new Generador().generaEmpresa();
        empresa1.creaCriterio("unCriterio");
        criterio = empresa1.getCriterios().get(0);
    }

    @Test
    public void testEntidad_PuedeGenerarCriterios(){
        empresa1.creaCriterio("Segundo Criterio");
        empresa1.creaCriterio("Tercer Criterio");
        Assert.assertEquals(empresa1.getCriterios().size(),3);
    }

    @Test
    public void testEntidad_PuedeGenerarCategorias(){

        empresa1.creaCategoria(criterio,"Hola soy una categoria");
        empresa1.creaCategoria(criterio,"Hola soy otra categoria");
        empresa1.creaCategoria(criterio,"Hola soy una categoria nueva!");

        Assert.assertEquals(criterio.getCategorias().size(),3);
    }

    @Test
    public void testCriteriosEnlazados_puedenMostrarCategorias(){
        /*Empresa Criterio*/
        empresa1.creaCriterio("Continente");
        criterio = empresa1.getCriterios().get(0);
        empresa1.creaCategoria(criterio,"Europa");
        empresa1.creaCategoria(criterio,"Asia");
        empresa1.creaCategoria(criterio,"America");

        /*SubCriterio 1*/
         Criterio subCriterio1 = new Criterio("Alcance");

        CategoriaOperacion categoria1 = new CategoriaOperacion("Nacional");
        CategoriaOperacion categoria2 = new CategoriaOperacion("Internacional");
        CategoriaOperacion categoria3 = new CategoriaOperacion("Barrial");

        subCriterio1.agregateCategoria(categoria1);
        subCriterio1.agregateCategoria(categoria2);
        subCriterio1.agregateCategoria(categoria3);

        criterio.setCriterioHijo(subCriterio1);

        /*SubCriterio 2*/
        Criterio subCriterio2 = new Criterio("Idioma");

        CategoriaOperacion subcategoria4 = new CategoriaOperacion("Frances");
        CategoriaOperacion subcategoria5 = new CategoriaOperacion("Espanol");
        CategoriaOperacion subcategoria6 = new CategoriaOperacion("Ingles");

        subCriterio2.agregateCategoria(subcategoria4);
        subCriterio2.agregateCategoria(subcategoria5);
        subCriterio2.agregateCategoria(subcategoria6);
        subCriterio1.setCriterioHijo(subCriterio2);

        Assert.assertEquals(empresa1.mostraCategoriasAsociadas().size(),9);
    }

    @Test
    public void testEntidad_PuedeMostrarTodasLasCategoriasDeSusCriterios(){
        empresa1.creaCriterio("Continente");
        criterio = empresa1.getCriterios().get(0);
        empresa1.creaCategoria(criterio,"Europa");
        empresa1.creaCategoria(criterio,"Asia");
        empresa1.creaCategoria(criterio,"America");
        empresa1.creaCriterio("Alcance");
        criterio = empresa1.getCriterios().get(1);
        empresa1.creaCategoria(criterio,"Nacional");
        empresa1.creaCategoria(criterio,"Internacional");
        empresa1.creaCategoria(criterio,"Barrial");
        empresa1.creaCriterio("Lenguaje");
        criterio = empresa1.getCriterios().get(2);
        empresa1.creaCategoria(criterio,"Frances");
        empresa1.creaCategoria(criterio,"Espanol");
        empresa1.creaCategoria(criterio,"Ingles");

        Assert.assertEquals(empresa1.mostraCategoriasAsociadas().size(),9);
    }


}
