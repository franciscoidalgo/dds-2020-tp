package APIMercadoLibre;

import APIMercadoLibre.modelos.Pais;
import APIMercadoLibre.modelos.Provincia;

public class AdapterDireccionMercadoLibre implements AdapterDireccion {

    Pais pais;
    Provincia provincia;
    String ciudad;

    public AdapterDireccionMercadoLibre(Pais pais, Provincia provincia, String ciudad){
        this.pais = pais;
        this.provincia = provincia;
        this.ciudad = ciudad;
    }

    @Override
    public String getPais() {
        return pais.name;
    }

    @Override
    public String getProvincia() {
        return provincia.name;
    }

    @Override
    public String getCiudad() {
        return ciudad;
    }

}
