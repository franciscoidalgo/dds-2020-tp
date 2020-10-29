package domain.DireccionPostal;


import domain.Entidad.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Direccion")
public class DireccionPostal extends EntidadPersistente {

    @Column(name = "pais")
    private String pais;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "calle")
    private String calle;

    @Column(name = "altura")
    private String altura;

    @Column(name = "piso")
    private String piso;

    @Column(name = "dpto")
    private String dpto;


    //Constructor
    public DireccionPostal(String pais, String provincia, String ciudad, String calle, String altura, String piso, String dpto) {
        this.pais = pais;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
        this.dpto = dpto;
    }

    public DireccionPostal(){}

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
