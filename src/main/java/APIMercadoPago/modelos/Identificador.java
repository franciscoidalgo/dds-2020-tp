package APIMercadoPago.modelos;

import DireccionPostal.Estandarizable;

public class Identificador implements Estandarizable {
    public String id;
    public String name;

    @Override
    public String mostraNombre() {
        return this.name;
    }
}
