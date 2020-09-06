package domain.DireccionPostal;

import APIMercadoLibre.modelos.Ciudad;
import APIMercadoLibre.modelos.Pais;
import APIMercadoLibre.modelos.Provincia;

public class DireccionPostal {

    private String pais;
    private String provincia;
    private String ciudad;
    private String calle;
    private String altura;
    private String piso;


    //Constructor
    public DireccionPostal(String pais, String ciudad, String provincia, String calle, String altura, String piso) {
        this.pais = pais;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
    }

    //Getters y Setters
    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }


}
