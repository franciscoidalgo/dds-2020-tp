
import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;
import domain.Entidad.CategorizacionOperacion.Criterio;
import domain.Entidad.Empresa;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestEntidades {
    Empresa empresa1;
    Criterio criterio;

    @Before
    public void setUp(){
        criterio = new Criterio("unCriterio");
        empresa1 = new Empresa();
        empresa1.agregaCriterio(criterio);

    }

    @Test
    public void testEntidad_PuedeAgregarCriterios(){
        empresa1.agregaCriterio(new Criterio("dosCriterio"));
        empresa1.agregaCriterio(new Criterio("tresCriterio"));
        Assert.assertEquals(empresa1.getCriterios().size(),3);
    }

    @Test
    public void testEntidad_PuedeMostrarCategoriasDeUnCriterio(){
        criterio.agregateCategoria(new CategoriaOperacion("unaCategoria"));
        criterio.agregateCategoria(new CategoriaOperacion("dosCategoria"));
        criterio.agregateCategoria(new CategoriaOperacion("tresCategoria"));

        Assert.assertEquals(empresa1.mostraCategoriasDe(criterio).size(),3);
    }

}
