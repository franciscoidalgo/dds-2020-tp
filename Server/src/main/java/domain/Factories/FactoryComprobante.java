package domain.Factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Operacion.Egreso.Comprobante;
import domain.Operacion.Egreso.TipoComprobante;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.Request;

import javax.servlet.ServletException;
import java.io.IOException;

public class FactoryComprobante {
    public static Comprobante get(Request request) throws IOException, ServletException {
        Repositorio<TipoComprobante> tipoComprobanteRepositorio = FactoryRepo.get(TipoComprobante.class);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonObject jComprobante = rootObject.getAsJsonObject("comprobante");
        Comprobante comprobante = new Comprobante();

        TipoComprobante tipoComprobante = tipoComprobanteRepositorio.buscar(jComprobante.get("tipoComprobante").getAsInt());

        comprobante.setTipoComprobante(tipoComprobante);
        comprobante.setPath(jComprobante.get("path").getAsString());

        return comprobante;
    }


}
