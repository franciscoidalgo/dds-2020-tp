package APIMercadoLibre;

import APIMercadoLibre.modelos.Moneda;
import APIMercadoLibre.modelos.Pais;
import APIMercadoLibre.services.ServicioMercadoLibre;

import java.io.IOException;
import java.util.List;

public class InfoMercadoLibre {
    private List<Pais> listaDePaises;
    private List<Moneda> listaDeMonedas;
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
        listaDePaises = servicioMercadoLibre.listaDePaisesConProvincias();
        listaDeMonedas = servicioMercadoLibre.listaDeMonedas();
    }

    public void persistirInfo(){
        //TODO
    }


    public List<Pais> getListaDePaises() {
        return listaDePaises;
    }

    public List<Moneda> getListaDeMonedas() {
        return listaDeMonedas;
    }
}
