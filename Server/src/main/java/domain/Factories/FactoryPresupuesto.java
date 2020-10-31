package domain.Factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Operacion.Egreso.DetalleOperacion;
import domain.Operacion.Egreso.MedioDePago;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Egreso.Presupuesto;
import domain.Operacion.Operacion;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.Request;

import java.time.LocalDate;

public class FactoryPresupuesto {
    public static Presupuesto get(Request request){
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
