package requiredModels.entidad;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Entidad {

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
