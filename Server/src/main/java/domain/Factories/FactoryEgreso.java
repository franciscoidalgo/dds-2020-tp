package domain.Factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Operacion.Egreso.DetalleOperacion;
import domain.Operacion.Egreso.MedioDePago;
import domain.Operacion.Egreso.OperacionEgreso;
import spark.Request;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;

public class FactoryEgreso {
    public static OperacionEgreso get(Request request) throws IOException, ServletException {
        JsonParser parser = new JsonParser();
        OperacionEgreso operacionEgreso = new OperacionEgreso();
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();

        LocalDate fecha = LocalDate.parse(rootObject.get("fecha").getAsString());
        Integer cantPresupuestos = rootObject.get("cantPresupuestos").getAsInt();

        DetalleOperacion detalleOperacion = FactoryDetalle.get(request);
        MedioDePago medioDePago = FactoryMedioDePago.get(request);


        operacionEgreso.setDetalle(detalleOperacion);
        operacionEgreso.setCantPresupuestos(cantPresupuestos);
        operacionEgreso.setMedioDePago(medioDePago);
        operacionEgreso.setFecha(fecha);
        operacionEgreso.setMontoTotal(operacionEgreso.montoTotal());


        return  operacionEgreso;
    }
}
