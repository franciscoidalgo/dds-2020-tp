package APIMercadoLibre.modelos;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Ciudad extends Identificable {
    public Identificable state;
    public Identificable country;
    public GeoInformation geo_information;
}
