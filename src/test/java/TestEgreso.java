import org.junit.Test;
import junit.framework.Assert;
import Egreso.Egreso;

import java.sql.Timestamp;

@SuppressWarnings("static-access")
public class TestEgreso {
    @Test
    public void agregarNumeroDeEgreso() {
        Egreso egreso = new Egreso();
        egreso.setNroEgreso(123456789);
        egreso.setFecha(new Timestamp(System.currentTimeMillis()));
        Assert.assertEquals(123456789,egreso.getNroEgreso());
    }
}