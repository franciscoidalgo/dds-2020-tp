package APIMercadoLibre;

import APIMercadoLibre.modelos.CodigoPostalAPI;
import APIMercadoLibre.modelos.Pais;

import java.util.Optional;

public class DireccionPostal {
    private String calle;
    private Integer altura;
    private Optional<Integer> piso;
    private Optional<String> depto;

    private AdapterDireccion adapterDireccion;

    public DireccionPostal (String calle, Integer altura, AdapterDireccion adapterDireccion){
        this.calle = calle;
        this.altura = altura;
        this.adapterDireccion = adapterDireccion;
    }

    public DireccionPostal (String calle, Integer altura, Integer piso, String depto, AdapterDireccion adapterDireccion){
        this.calle = calle;
        this.altura = altura;
        this.piso = Optional.of(piso);
        this.depto = Optional.of(depto);
        this.adapterDireccion = adapterDireccion;
    }

    public String getPais(){
        return adapterDireccion.getPais();
    }

    public String getProvincia(){
        return adapterDireccion.getProvincia();
    }

    public String getCiudad(){
        return adapterDireccion.getCiudad();
    }

}
