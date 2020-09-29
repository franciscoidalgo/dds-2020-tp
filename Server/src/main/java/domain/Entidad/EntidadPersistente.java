package domain.Entidad;

import javax.persistence.*;

@MappedSuperclass
public class EntidadPersistente {

    @Id
    @GeneratedValue
    private int id;

    public void setId (int id){
        this.id = id;
    }

    public int getId (){
        return id;
    }

}
