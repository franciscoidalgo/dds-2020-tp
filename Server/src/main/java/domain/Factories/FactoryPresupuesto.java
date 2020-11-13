package domain.Factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Operacion.Egreso.DetalleOperacion;
import domain.Operacion.Egreso.Presupuesto;
import spark.Request;

import javax.servlet.ServletException;
import java.io.IOException;

public class FactoryPresupuesto {
    public static Presupuesto get(Request request) throws IOException, ServletException {
        JsonParser parser = new JsonParser();
        Presupuesto presupuesto = new Presupuesto();
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();

        DetalleOperacion detalleOperacion = FactoryDetalle.get(request);
        presupuesto.setDetalle(detalleOperacion);
        presupuesto.setMontoTotal(presupuesto.montoTotal());



        return  presupuesto;
    }
}
