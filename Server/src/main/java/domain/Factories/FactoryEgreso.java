package domain.Factories;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;
import domain.Operacion.Egreso.DetalleOperacion;
import domain.Operacion.Egreso.Item;
import domain.Operacion.Egreso.MedioDePago;
import domain.Operacion.Egreso.OperacionEgreso;
import spark.Request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactoryEgreso {
    public static OperacionEgreso get(Request request){
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
