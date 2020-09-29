package domain.Entidad;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public interface Entidad {
    String nombre();
    String descripcion();
}
