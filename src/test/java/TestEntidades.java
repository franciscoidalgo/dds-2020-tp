/*
import Entidad.CategorizacionOperacion.Criterio;
import Entidad.Empresa;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestEntidades {
    Empresa empresa1;

    Criterio criterio;
    Generador generador;
    @Before

    public void setUp() throws IOException {
        generador = Generador.instancia();;
        empresa1 = generador.generaEmpresa();
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

}
*/