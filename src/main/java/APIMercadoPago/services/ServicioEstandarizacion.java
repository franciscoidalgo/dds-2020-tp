package APIMercadoPago.services;

import APIMercadoPago.modelos.ListaDeMonedas;
import APIMercadoPago.modelos.Pais;
import DireccionPostal.Direccion;

import java.io.IOException;
import java.util.List;

public interface ServicioEstandarizacion {

    public List<Pais> generaPaises()throws IOException;
    public ListaDeMonedas listaDeMonedas()throws IOException;
    public Direccion generaDireccion(String idPais, String idProv, String idCiudad)throws IOException;
}
