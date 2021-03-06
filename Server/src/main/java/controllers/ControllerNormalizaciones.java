package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import APIMercadoLibre.modelos.Ciudad;
import APIMercadoLibre.modelos.Provincia;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerNormalizaciones {
    public String pasarProvincias(Request request, Response response) throws IOException {
        List<Provincia> listaProvincias = InfoMercadoLibre.instancia().getListaDeProvincias().stream().filter(
                provincia -> provincia.getCountry().name.equals(request.params("nombrePais"))).collect(Collectors.toList());
        Gson gson = new Gson();
        String jProvincias = gson.toJson(listaProvincias);
        response.type("application/json");
        return jProvincias;
    }

    public String pasarCiudades(Request request, Response response) throws IOException {
        List<Ciudad> listaCiudades = Arrays.asList(InfoMercadoLibre.instancia().getListaDeProvincias().stream().filter(
                provincia -> provincia.getNombre().equals(request.params("nombreProvincia"))).findFirst().get().cities);
        Gson gson = new Gson();
        String jCiudades = gson.toJson(listaCiudades);
        response.type("application/json");
        return jCiudades;
    }

}
