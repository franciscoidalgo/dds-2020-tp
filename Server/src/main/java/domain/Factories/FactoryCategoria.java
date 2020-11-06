package domain.Factories;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.Request;

import java.util.ArrayList;
import java.util.List;

public class FactoryCategoria {
    public static List<CategoriaOperacion> get(Request request){
        Repositorio<CategoriaOperacion> categoriaRepositorio = FactoryRepo.get(CategoriaOperacion.class);
        List<CategoriaOperacion> categorias = new ArrayList<>();
        JsonParser parser = new JsonParser();

        JsonElement jsonElement = parser.parse(request.body());
        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonArray jCategorias = rootObject.getAsJsonArray("idCategorias");

        for (JsonElement columnElement : jCategorias) {
            JsonObject field = columnElement.getAsJsonObject();
            System.out.println(field.get("idCategoria").getAsInt());
            CategoriaOperacion  categoriaOperacion = categoriaRepositorio.buscar(field.get("idCategoria").getAsInt());
            categorias.add(categoriaOperacion);
        }
        return categorias;
    }
}
