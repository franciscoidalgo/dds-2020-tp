package domain.Entidad;

import domain.DireccionPostal.DireccionPostal;

public class OrganizacionSocial extends  EntidadJuridica {

    public OrganizacionSocial(String razonSocial, String nombre, long CUIT, String descripcion, DireccionPostal direccionPostal, long codIGJ) {
        super(razonSocial, nombre, CUIT, descripcion, direccionPostal, codIGJ);
    }

    public String nombre() {
        return this.nombre;
    }

    public String descripcion() {
        return this.descripcion;
    }
}
