package Entidad;

public class OrganizacionSocial extends  EntidadJuridica {

    @Override
    public String nombre() {
        return this.nombre;
    }

    @Override
    public String descripcion() {
        return this.descripcion;
    }
}
