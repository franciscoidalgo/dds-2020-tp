package APIMercadoLibre.modelos;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


public class Identificable  {

    public String id;

    public String name;

    public String getNombre(){
        return name;
    }

}
