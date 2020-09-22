package controllers;

import APIMercadoLibre.InfoMercadoLibre;
import config.ConfiguracionMercadoLibre;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerEgresos {

    public ModelAndView mostrarEgresos(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        if(ConfiguracionMercadoLibre.usarApi){
            InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
            parametros.put("paises", infoMercadoLibre.getListaDePaises());
            parametros.put("provincias", infoMercadoLibre.getListaDeProvincias());
            parametros.put("monedas", infoMercadoLibre.getListaDeMonedas());
        }

        parametros.put("egreso", true);
        return new ModelAndView(parametros, "egreso.hbs");
    }
}
