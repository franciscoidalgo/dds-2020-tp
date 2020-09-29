package domain.Entidad.CategorizacionEmpresa;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sector")
public class Sector extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    private List<Categoria> categoria;


    public Sector(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = new ArrayList<>();
    }

    public Sector() {
        this.categoria = new ArrayList<>();
    }


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

    public List<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<Categoria> categoria) {
        this.categoria = categoria;
    }

    public void agregateCategoria(Categoria unaCategoria) {
        categoria.add(unaCategoria);
    }
}





