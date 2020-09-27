package requiredModels.usuario;

import requiredModels.entidad.Entidad;
import requiredModels.entidad.EntidadJuridica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "usuario")
public class Usuario extends Entidad {
    //Atributo
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "password")
    private String password;

    @Transient
    private Rol rol;

    @Transient
    private EntidadJuridica entidadPertenece;//domain.Entidad Juridica a la que pertenece el usuario

    @Transient
    private Entidad entidadSeleccionada;

    @Transient
    private BandejaMensaje bandejaDeMensajes;

}
