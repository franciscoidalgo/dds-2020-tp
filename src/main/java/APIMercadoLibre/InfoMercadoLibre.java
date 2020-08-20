package APIMercadoLibre;

import APIMercadoLibre.modelos.ListaDeMonedas;
import APIMercadoLibre.modelos.ListaIdentificables;
import APIMercadoLibre.services.ServicioMercadoLibre;

import java.io.IOException;

public class InfoMercadoLibre {
    private ListaIdentificables listaDePaises;
//  private ListaIdentificables listaDeProvincias;
    private ListaDeMonedas listaDeMonedas;
    private static InfoMercadoLibre instancia = null;
    private ServicioMercadoLibre servicioMercadoLibre;

    private InfoMercadoLibre() throws IOException {
        this.actualizarInfo();
    }

    public static InfoMercadoLibre instancia () throws IOException {
        if (instancia == null){
            instancia = new InfoMercadoLibre();
        }
        return instancia;
    }

    public void actualizarInfo() throws IOException {
        listaDePaises = servicioMercadoLibre.listaDePaises();
        listaDeMonedas = servicioMercadoLibre.listaDeMonedas();
    }

    public void persistirInfo(){
        //TODO
    }


}
