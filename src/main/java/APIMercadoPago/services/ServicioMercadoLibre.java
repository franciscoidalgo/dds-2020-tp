package APIMercadoPago.services;

import APIMercadoPago.modelos.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;

public class ServicioMercadoLibre {

    private static ServicioMercadoLibre instancia = null;
    private Retrofit retrofit;

    private ServicioMercadoLibre(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioMercadoLibre instancia(){
        if (instancia == null) {
            instancia = new ServicioMercadoLibre();
        }
        return instancia;
    }

    public ListaDePaises listaDePaises () throws IOException {

        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Pais[]> requestListaDePaises = mercadoLibreService.paises();
        Response<Pais[]> responseListaDePaises = requestListaDePaises.execute();
        ListaDePaises listaDePaises = new ListaDePaises(responseListaDePaises.body());

        return listaDePaises;

    }

    public PaisExtendido paisDeId (String cualId) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<PaisExtendido> requestPaisDeId = mercadoLibreService.pais(cualId);
        Response<PaisExtendido> responsePaisDeId = requestPaisDeId.execute();
        PaisExtendido paisDeNombre = responsePaisDeId.body();

        return paisDeNombre;
    }

    public PaisExtendido paisDeNombre (String cualNombre) throws Exception {
        String idDelPais = this.listaDePaises().idDePais(cualNombre);
        return paisDeId(idDelPais);
    }

    public ListaDeProvincias listaDeProvincias  (String idDeSuPais) throws IOException {
        return new ListaDeProvincias(this.paisDeId(idDeSuPais).states);
    }

    public ListaDeProvincias listaDeProvincias (PaisExtendido pais) throws IOException {
        return this.listaDeProvincias(pais.id);
    }

    public ProvinciaExtendida provinciaDeId (String cualId) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<ProvinciaExtendida> requestProvinciaDeId = mercadoLibreService.provincia(cualId);
        Response<ProvinciaExtendida> responseProvinciaDeId = requestProvinciaDeId.execute();
        ProvinciaExtendida provinciaDeId = responseProvinciaDeId.body();
        return provinciaDeId;
    }

    public ListaDeMonedas listaDeMonedas () throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Moneda[]> requestListaDeMonedas = mercadoLibreService.monedas();
        Response<Moneda[]> responseListaDeMonedas = requestListaDeMonedas.execute();
        ListaDeMonedas listaDeMonedas = new ListaDeMonedas(responseListaDeMonedas.body());

        return listaDeMonedas;

    }

    public Moneda monedaDeId (String cualId) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Moneda> requestMonedaDeId = mercadoLibreService.moneda();
        Response<Moneda> reponseMonedaDeId = requestMonedaDeId.execute();
        Moneda monedaDeId = reponseMonedaDeId.body();
        return monedaDeId;

    }

    public ConversionDeMonedas conversionDeMonedas (String idFrom, String idTo) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<ConversionDeMonedas> requestConversionDeMonedas = mercadoLibreService.conversionDeMonedas(idFrom, idTo);
        Response<ConversionDeMonedas> responseConversionDeMonedas = requestConversionDeMonedas.execute();
        ConversionDeMonedas conversionDeMonedas = responseConversionDeMonedas.body();
        return conversionDeMonedas;

    }

}
