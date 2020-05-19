import Egreso.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestEgreso {

    @Test
    public void testDetalle(){
        List<Item> lista = new ArrayList<Item>();
        lista.add(new Producto(100, 10));
        lista.add(new Servicio(200));

        Detalle detalleTest = new Detalle(lista);
        Assert.assertTrue(detalleTest.total() == 1200);
    }
}
