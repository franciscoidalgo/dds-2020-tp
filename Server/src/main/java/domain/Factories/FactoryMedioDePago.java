package domain.Factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Operacion.Egreso.MedioDePago;
import domain.Operacion.Egreso.TipoDePago;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.Request;

public class FactoryMedioDePago {
    public static MedioDePago get(Request request){
        Repositorio<TipoDePago> tipoDePagoRepositorio = FactoryRepo.get(TipoDePago.class);

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonObject jMedioDePago = rootObject.getAsJsonObject("medioDePago");

        MedioDePago medioDePago = new MedioDePago();
        TipoDePago tipoDePago = tipoDePagoRepositorio.buscar(jMedioDePago.get("idTipoDePago").getAsInt());

        medioDePago.setMoneda(jMedioDePago.get("idTipoDePago").getAsString());
        //medioDePago.setNombre(); //TODO CHEQUEAR ESTO
        medioDePago.setTipoDePago(tipoDePago);

        return  medioDePago;
    }
}
