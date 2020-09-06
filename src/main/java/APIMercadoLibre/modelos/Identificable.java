package APIMercadoLibre.modelos;

import domain.DireccionPostal.Estandarizable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class Identificable implements Estandarizable {

    @Id
    public String id;

    @Column
    public String name;

    @Override
    public String mostraNombre() {
        return this.name;
    }
}
