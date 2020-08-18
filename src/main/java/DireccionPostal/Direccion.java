package DireccionPostal;

import APIMercadoPago.modelos.Ciudad;
import APIMercadoPago.modelos.Pais;
import APIMercadoPago.modelos.Provincia;

public class Direccion implements Estandarizable{

    private Pais pais;
    private Ciudad ciudad;
    private Provincia provincia;

    //Constructor
    public Direccion(Pais pais, Ciudad ciudad, Provincia provincia) {
        this.pais = pais;
        this.ciudad = ciudad;
        this.provincia = provincia;
    }

    //Getters y Setters
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
    //Funcionalidad

    public String mostraNombre() {
    return String.format("%s, %s, %s", pais.mostraNombre(), provincia.mostraNombre(), ciudad.mostraNombre());
    }



}
