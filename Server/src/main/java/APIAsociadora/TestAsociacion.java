package APIAsociadora;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Operacion.Egreso.DetalleOperacion;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Egreso.Proveedor;
import domain.Operacion.Ingreso.OperacionIngreso;

import java.io.IOException;
import java.time.LocalDate;

public class TestAsociacion {
    public static void main(String[] args) throws IOException {
        //Declaracion de egresos e ingresos para probar
        ServicioAsociacion servicioAsociacion = ServicioAsociacion.getInstancia();
        OperacionIngreso operacionIngreso = new OperacionIngreso(LocalDate.now(),2000,null,"Un re ingreso", LocalDate.now());
        OperacionEgreso egreso1 = new OperacionEgreso(LocalDate.now(),1000,null,new DetalleOperacion(new Proveedor("un re proveedor", 1, null)), null, null, 1000);
        OperacionEgreso egreso2 = new OperacionEgreso(LocalDate.now(),1000,null,new DetalleOperacion(new Proveedor("otro re proveedor", 2, null)), null, null, 1000);
        OperacionEgreso egreso3 = new OperacionEgreso(LocalDate.now(),1000,null,new DetalleOperacion(new Proveedor("un re proveedoooooooooooooooooooooooooooooooor", 1, null)),null,null,5);
        egreso1.setFecha(LocalDate.of(2020,9,29));
        egreso2.setFecha(LocalDate.of(2020,8,23));
        egreso3.setFecha(LocalDate.of(2020, 8, 24));
        OperacionEgreso[] arrayEgresos = new OperacionEgreso[]{egreso1, egreso2, egreso3, egreso3};



        //Prueba
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        OperacionIngreso ingresoAsociado = servicioAsociacion
                .getIngresoAsociado(gson.toJson(operacionIngreso), gson.toJson(arrayEgresos), "2020-08-15", "2020-09-15");
        System.out.println(gson.toJson(ingresoAsociado));

    }
}
