package APIMercadoLibre;


public class DireccionPostal {
    private final String calle;
    private final Integer altura;
    private Integer piso;
    private String depto;

    private final AdapterDireccion adapterDireccion;

    public DireccionPostal (String calle, Integer altura, AdapterDireccion adapterDireccion){
        this.calle = calle;
        this.altura = altura;
        this.adapterDireccion = adapterDireccion;
    }

    public DireccionPostal (String calle, Integer altura, Integer piso, String depto, AdapterDireccion adapterDireccion){
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
        this.depto = depto;
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
