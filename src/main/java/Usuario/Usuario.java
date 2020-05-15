package Usuario;

import Entidad.EntidadJuridica;

public class Usuario {
    //Atributo
    private String nombre;
    private String contraseña;
    private Rol rol;
    private EntidadJuridica entidadPertenece;//Entidad Juridica a la que pertenece el usuario
    private int entidadSeleccionada;/*TODO: POSIBLE IMPLEMENTACION:
                                     Entidad que selecciona el usuario para los requerimientos: -1=Juridica|0...n Base
                                    (0...n es el Index para buscar en la coleccion de la entidad juridica)*/

    //Getters-Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public EntidadJuridica getEntidadPertenece() {
        return entidadPertenece;
    }

    public void setEntidadPertenece(EntidadJuridica entidadPertenece) {
        this.entidadPertenece = entidadPertenece;
    }

    public int getEntidadSeleccionada() {

        return entidadSeleccionada;
    }

    public void setEntidadSeleccionada(int entidadSeleccionada) {
        this.entidadSeleccionada = entidadSeleccionada;
    }
}
