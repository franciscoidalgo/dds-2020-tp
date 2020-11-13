package domain.Entidad;



import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("base")
public class EntidadBase extends Entidad {
    //Atributos
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;
    //Constructor

    public class EntidadBaseDTO{
        private final String tipo = "EB";
        private String nombre;
        private String descripcion;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }

    public EntidadBase(String nombre, String descripcion){
        this.nombre=nombre;
        this.descripcion= descripcion;
    }

    public EntidadBase() {}

    //Getters-Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String nombre() {
        return nombre;
    }

    @Override
    public String descripcion() {
        String template = "Una organizacion base.";
        return template + descripcion;

    }

    public EntidadBaseDTO toDTO() {
        EntidadBaseDTO entidadBaseDTO = new EntidadBaseDTO();
        entidadBaseDTO.setDescripcion(this.descripcion());
        entidadBaseDTO.setNombre(this.nombre());
        return entidadBaseDTO;
    }
}
