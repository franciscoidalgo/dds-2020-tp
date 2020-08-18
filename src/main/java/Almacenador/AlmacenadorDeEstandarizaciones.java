package Almacenador;

import APIMercadoPago.modelos.Moneda;
import APIMercadoPago.modelos.Pais;
import APIMercadoPago.modelos.Provincia;
import APIMercadoPago.services.ServicioEstandarizacion;
import APIMercadoPago.services.ServicioMercadoLibre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlmacenadorDeEstandarizaciones {



    private List<Pais> paises;
    private List<Moneda> monedas;
    private ServicioEstandarizacion servicio = ServicioMercadoLibre.instancia();

    private static AlmacenadorDeEstandarizaciones instancia;


    //Funcionalidad
    static {
        try {
            instancia = new AlmacenadorDeEstandarizaciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Constructor
    private AlmacenadorDeEstandarizaciones() throws IOException {
        this.actualizaPaises();
    }
    //Getters and Setters
    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }
    public List<Moneda> getMonedas() {
        return monedas;
    }

    public void setMonedas(List<Moneda> monedas) {
        this.monedas = monedas;
    }
    //Funcionalidades
    public static AlmacenadorDeEstandarizaciones getInstance() {
        return instancia;
    }

    public List<Provincia> mostraProvincias(Pais unPais) {
        return this.paises.stream().filter(pais -> pais == unPais).findFirst().get().mostrarProvincias();

    }

    public  void actualizaPaises() throws IOException {
        paises = this.servicio.generaPaises();

        monedas =  new ArrayList<>();
        this.getPaises().stream().forEach(pais -> {
            try {
                monedas.add(this.traerMoneda(pais.currency_id));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private Moneda traerMoneda(String id) throws IOException {
        return (Moneda) ServicioMercadoLibre.instancia().monedaDeId(id);
    }

}
