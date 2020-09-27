package APIMercadoLibre;

import APIMercadoLibre.modelos.Identificable;
import APIMercadoLibre.modelos.Moneda;
import APIMercadoLibre.modelos.Pais;
import APIMercadoLibre.modelos.Provincia;
import APIMercadoLibre.services.ServicioMercadoLibre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InfoMercadoLibre {

    private List<Pais> listaDePaises;
    private List<Provincia> listaDeProvincias;
    private List<Moneda> listaDeMonedas;
    private static InfoMercadoLibre instancia = null;
    private static ServicioMercadoLibre servicioMercadoLibre;

    private InfoMercadoLibre() throws IOException {
        System.out.println("Cargando informacion de Mercado Libre...");
        this.actualizarInfo();
        System.out.println("Informacion cargada con exito!");
    }

    public static InfoMercadoLibre instancia () throws IOException {
        if (instancia == null){
            servicioMercadoLibre = ServicioMercadoLibre.instancia();
            instancia = new InfoMercadoLibre();
        }
        return instancia;
    }

    public void actualizarInfo() throws IOException {
        listaDePaises = servicioMercadoLibre.listaDePaisesConProvincias();
        listaDeMonedas = servicioMercadoLibre.listaDeMonedas();
        listaDeProvincias = new ArrayList<>();
        for (Pais pais : listaDePaises){
            for (Identificable provincia : pais.mostrarProvincias()){
                listaDeProvincias.add(servicioMercadoLibre.provinciaDeId(provincia.id));
            }
        }
    }

    public List<Pais> getListaDePaises() {
        return listaDePaises;
    }

    public Pais paisDeNombre (String nombre) throws Exception {

        Optional<Pais> paisBuscado = listaDePaises.stream().filter(pais->pais.name.equals(nombre)).findFirst();
        if(paisBuscado.isPresent()){
            return paisBuscado.get();
        }else{
            throw new Exception("No se encontro el pais correspondiente a ese nombre.");
        }

    }

    public Provincia provinciaDeNombre (String nombre) throws Exception {

        Optional<Provincia> provinciaBuscada = listaDeProvincias.stream().filter(provincia->provincia.name.equals(nombre)).findFirst();
        if(provinciaBuscada.isPresent()){
            return provinciaBuscada.get();
        }else{
            throw new Exception("No se encontro el pais correspondiente a ese nombre.");
        }

    }

    public List<Provincia> getListaDeProvincias(){
        return listaDeProvincias;
    }

    public List<Moneda> getListaDeMonedas() {
        return listaDeMonedas;
    }

}
