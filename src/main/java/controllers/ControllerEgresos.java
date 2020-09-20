package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerEgresos {

    public ModelAndView mostrarEgresos(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
        parametros.put("egreso", true);
        parametros.put("paises", infoMercadoLibre.getListaDePaises());
        parametros.put("provincias", infoMercadoLibre.getListaDeProvincias());
        parametros.put("monedas", infoMercadoLibre.getListaDeMonedas());
        return new ModelAndView(parametros, "egreso.hbs");
    }
}
