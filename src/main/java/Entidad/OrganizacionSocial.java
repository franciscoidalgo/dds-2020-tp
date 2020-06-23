package Entidad;

public class OrganizacionSocial extends  EntidadJuridica {

    public OrganizacionSocial(String razonSocial, String nombre, long CUIT, String descripcion, String direccionPostal, long codIGJ) {
        super(razonSocial, nombre, CUIT, descripcion, direccionPostal, codIGJ);
    }

    @Override
    public String nombre() {
        return this.nombre;
    }

    @Override
    public String descripcion() {
        return this.descripcion;
    }
}
