package domain.Factories;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Operacion.Egreso.Item;
import domain.Operacion.Egreso.Pedido;
import domain.Operacion.Egreso.TipoDeItem;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.Request;

import java.util.ArrayList;
import java.util.List;

public class FactoryPedido {
    public static List<Pedido> get(Request request){
        Repositorio<TipoDeItem> tipoDeItemRepositorio = FactoryRepo.get(TipoDeItem.class);
        List<Pedido> pedidos = new ArrayList<>();
        JsonParser parser = new JsonParser();

        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonArray jPedido = rootObject.getAsJsonArray("pedido");

        for (JsonElement columnElement : jPedido) {
            Pedido pedido = new Pedido();
            JsonObject field = columnElement.getAsJsonObject();
            TipoDeItem tipoDeItem = tipoDeItemRepositorio.buscar(field.get("idTipo").getAsInt());

            Item item = new Item();
            item.setDescripcion(field.get("nombre").getAsString());
            item.setPrecioUnitario(field.get("precioUnitario").getAsFloat());
            item.setTipoDeItem(tipoDeItem);

            pedido.setCantidad(field.get("cantidad").getAsInt());
            pedido.setItem(item);
            pedidos.add(pedido);
        }

        return pedidos;
    }
}
