package domain.Factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Operacion.Egreso.Comprobante;
import domain.Operacion.Egreso.TipoComprobante;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.Request;

public class FactoryComprobante {
    public static Comprobante get(Request request){
        Repositorio<TipoComprobante> tipoComprobanteRepositorio = FactoryRepo.get(TipoComprobante.class);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonObject jComprobante = rootObject.getAsJsonObject("comprobante");
        Comprobante comprobante = new Comprobante();

        TipoComprobante tipoComprobante = tipoComprobanteRepositorio.buscar(jComprobante.get("tipoComprobante").getAsInt());

        comprobante.setTipoComprobante(tipoComprobante);
        comprobante.setPath(jComprobante.get("path").getAsString());//TODO CAMBIAR PARA QUE LLEGUE DESDE EL SERVER Y NOSOTROS LE PONGAMOS LA RUTA QUE QUEREMOs
        return comprobante;
    }
}
