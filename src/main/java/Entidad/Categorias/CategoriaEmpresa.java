package Entidad.Categorias;

import Entidad.Empresa;

public class CategoriaEmpresa { // TODO: Pensado como vimos el patron Stage
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaEmpresa categorizar(Empresa empresa){};
}



