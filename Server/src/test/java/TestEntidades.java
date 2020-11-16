
import domain.Entidad.Entidad;
import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import domain.Operacion.CategorizacionOperacion.Criterio;
import domain.Entidad.Empresa;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repositorios.factories.FactoryRepo;

import java.util.ArrayList;
import java.util.List;

public class TestEntidades {
    Empresa empresa1;
    Criterio criterio;

    @Before
    public void setUp() {
        criterio = new Criterio("unCriterio");
        empresa1 = new Empresa();
        empresa1.agregaCriterio(criterio);

    }

    @Test
    public void testEntidad_PuedeAgregarCriterios() {
        empresa1.agregaCriterio(new Criterio("dosCriterio"));
        empresa1.agregaCriterio(new Criterio("tresCriterio"));
        Assert.assertEquals(empresa1.getCriterios().size(), 3);
    }

    @Test
    public void testEntidad_PuedeMostrarCategoriasDeUnCriterio() {
        criterio.agregateCategoria(new CategoriaOperacion("unaCategoria"));
        criterio.agregateCategoria(new CategoriaOperacion("dosCategoria"));
        criterio.agregateCategoria(new CategoriaOperacion("tresCategoria"));

        Assert.assertEquals(empresa1.mostraCategoriasDe(criterio).size(), 3);
    }

    @Test
    public void testEntidad_MuestraTodasSusCategorias() {
        Criterio criterio2 = new Criterio("dosCriterio");
        Criterio criterio3 = new Criterio("tresCriterio");
        empresa1.agregaCriterio(criterio2);

        criterio2.agregateCategoria(new CategoriaOperacion("unaCategoria"));
        criterio2.agregateCategoria(new CategoriaOperacion("dosCategoria"));
        criterio2.agregateCategoria(new CategoriaOperacion("tresCategoria"));

        criterio2.setCriterioHijo(criterio3);

        criterio3.agregateCategoria(new CategoriaOperacion("unaCategoria"));
        criterio3.agregateCategoria(new CategoriaOperacion("dosCategoria"));
        criterio3.agregateCategoria(new CategoriaOperacion("tresCategoria"));

        criterio.agregateCategoria(new CategoriaOperacion("unaCategoria"));
        criterio.agregateCategoria(new CategoriaOperacion("dosCategoria"));
        criterio.agregateCategoria(new CategoriaOperacion("tresCategoria"));

        Assert.assertEquals(empresa1.mostrarTodasCategorias().size(), 9);
    }

    @Test
    public void testEntidad_MuestraTodosSusCriterios() {
        Criterio criterio2 = new Criterio("dosCriterio");
        Criterio criterio3 = new Criterio("tresCriterio");
        Criterio criterio4 = new Criterio("dosCriterio");
        Criterio criterio5 = new Criterio("tresCriterio");
        Criterio criterio6 = new Criterio("dosCriterio");
        Criterio criterio7 = new Criterio("tresCriterio");

        empresa1.agregaCriterio(criterio2);
        criterio2.setCriterioHijo(criterio3);
        criterio3.setCriterioHijo(criterio4);
        criterio4.setCriterioHijo(criterio5);

        empresa1.agregaCriterio(criterio6);
        criterio6.setCriterioHijo(criterio7);


        Assert.assertEquals(empresa1.mostrarTodosCriterios().size(), 7);
    }
}