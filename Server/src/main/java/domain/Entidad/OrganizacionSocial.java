package domain.Entidad;

import domain.DireccionPostal.DireccionPostal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("organizacion_social")
public class OrganizacionSocial extends EntidadJuridica{

    public class OrganizacionSocialDTo{
        private final String tipo = "OS";
        private String nombre;
        private String razonSocial;
        private String descripcion;
        private long CUIT;
        private DireccionPostal direccionPostal;
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getRazonSocial() {
            return razonSocial;
        }

        public void setRazonSocial(String razonSocial) {
            this.razonSocial = razonSocial;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public long getCUIT() {
            return CUIT;
        }

        public void setCUIT(long CUIT) {
            this.CUIT = CUIT;
        }

        public DireccionPostal getDireccionPostal() {
            return direccionPostal;
        }

        public void setDireccionPostal(DireccionPostal direccionPostal) {
            this.direccionPostal = direccionPostal;
        }
    }

    public OrganizacionSocial(String razonSocial, String nombre, long CUIT, String descripcion, DireccionPostal direccionPostal) {
        super(razonSocial, nombre, CUIT, descripcion, direccionPostal);

    }

    public String nombre() {
        return this.nombre;
    }
    public OrganizacionSocial() {}

    public String descripcion() {
        String template = "Organizacion social. ";

        return template+this.descripcion;
    }


    public OrganizacionSocialDTo toDTO() {
        OrganizacionSocialDTo dto = new OrganizacionSocialDTo();
        dto.setNombre(this.nombre());
        dto.setCUIT(this.getCuit());
        dto.setDescripcion(this.getDescripcion());
        dto.setDireccionPostal(this.getDireccionPostal());
        return dto;
    }
}
