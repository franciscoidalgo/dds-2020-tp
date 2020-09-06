package APIMercadoLibre.modelos;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class Identificable  {

    @Id
    public String id;

    @Column
    public String name;

}
