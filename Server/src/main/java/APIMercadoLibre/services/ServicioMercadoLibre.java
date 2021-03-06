package APIMercadoLibre.services;

import APIMercadoLibre.modelos.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServicioMercadoLibre {

    private static ServicioMercadoLibre instancia = null;
    private final Retrofit retrofit;

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

    public CodigoPostalAPI informacionCodigoPostal (String idPais, String codigoPostal) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<CodigoPostalAPI> requestCodigoPostal = mercadoLibreService.codigoPostalApi(idPais, codigoPostal);
        Response<CodigoPostalAPI> responesCodigoPostal = requestCodigoPostal.execute();
        CodigoPostalAPI informacionCodigoPostal = responesCodigoPostal.body();

        return informacionCodigoPostal;

    }

    public ListaIdentificables listaDePaises () throws IOException {

        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<PaisSimplificado[]> requestListaDePaises = mercadoLibreService.paises();
        Response<PaisSimplificado[]> responseListaDePaises = requestListaDePaises.execute();
        ListaIdentificables listaDePaises = new ListaIdentificables(responseListaDePaises.body());

        return listaDePaises;

    }

    public List<Pais> listaDePaisesConProvincias() throws IOException {
        ListaIdentificables listaIdentificablesPaises = this.listaDePaises();
        List<Pais> listaDePaises = new ArrayList<>();
        for (Identificable pais : listaIdentificablesPaises.getIdentificadores()){
            listaDePaises.add(paisDeId(pais.id));
        }
        return listaDePaises;
    }

    public ListaIdentificables listaDeProvincias (Pais deQuePais){
        return new ListaIdentificables(deQuePais.states);
    }

    public ListaIdentificables listaDeCiudades (Provincia deQueProvincia){
        return new ListaIdentificables(deQueProvincia.cities);
    }

    public Pais paisDeId (String cualId) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Pais> requestPaisDeId = mercadoLibreService.pais(cualId);
        Response<Pais> responsePaisDeId = requestPaisDeId.execute();
        Pais paisDeNombre = responsePaisDeId.body();

        return paisDeNombre;
    }

    public Pais paisDeNombre (String cualNombre) throws Exception {
        String idDelPais = this.listaDePaises().idDeIdentificador(cualNombre);
        return paisDeId(idDelPais);
    }

    public Provincia provinciaDeId (String cualId) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Provincia> requestProvinciaDeId = mercadoLibreService.provincia(cualId);
        Response<Provincia> responseProvinciaDeId = requestProvinciaDeId.execute();
        Provincia provinciaDeId = responseProvinciaDeId.body();
        return provinciaDeId;
    }

    public Ciudad ciudadDeId (String cualId) throws IOException{
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Ciudad> requestCiudad = mercadoLibreService.ciudad(cualId);
        Response<Ciudad> responseCiudad = requestCiudad.execute();
        Ciudad ciudadDeId = responseCiudad.body();

        return ciudadDeId;

    }

    public List<Moneda> listaDeMonedas () throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Moneda[]> requestListaDeMonedas = mercadoLibreService.monedas();
        Response<Moneda[]> responseListaDeMonedas = requestListaDeMonedas.execute();
        List<Moneda> listaDeMonedas = Arrays.asList(responseListaDeMonedas.body());

        return listaDeMonedas;

    }

    public Moneda monedaDeId (String cualId) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Moneda> requestMonedaDeId = mercadoLibreService.moneda();
        Response<Moneda> reponseMonedaDeId = requestMonedaDeId.execute();
        Moneda monedaDeId = reponseMonedaDeId.body();

        return monedaDeId;

    }


}
