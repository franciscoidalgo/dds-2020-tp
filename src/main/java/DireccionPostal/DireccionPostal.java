package DireccionPostal;

public class DireccionPostal {



    private String calle;
    private String altura;
    private String piso;
    private Estandarizable direccion;

    public DireccionPostal(String calle, String altura, String piso, Estandarizable direccion) {
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
        this.direccion = direccion;
    }


    public String mostraDireccion() {
        return String.format("%s %s %s %s", direccion.mostraNombre(), this.calle, this.altura, this.piso);
    }
}
