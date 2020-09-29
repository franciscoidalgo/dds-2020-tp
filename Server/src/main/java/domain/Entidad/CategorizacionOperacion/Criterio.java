package domain.Entidad.CategorizacionOperacion;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "criterio")
public class Criterio extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "criterioHijo_id")
    private Criterio criterioHijo;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "criterio_id")
    private List<CategoriaOperacion> categorias;

    //Constructores
    public Criterio(String nombre, Criterio criterioHijo) {
        this.nombre = nombre;
        this.criterioHijo = criterioHijo;
        this.categorias = new ArrayList<>();
    }

    public Criterio(String nombre) {
        this.nombre = nombre;
        this.criterioHijo = null;
        this.categorias = new ArrayList<>();
    }

    public Criterio() {
        this.categorias = new ArrayList<>();
    }

    //Getter and Setter
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Criterio getCriterioHijo() {
        return criterioHijo;
    }

    public void setCriterioHijo(Criterio criterioHijo) {
        if (this != criterioHijo) this.criterioHijo = criterioHijo;
    }

    public List<CategoriaOperacion> getCategorias() {
        return this.categorias;
    }

    public void setCategorias(List<CategoriaOperacion> categorias) {
        this.categorias = categorias;
    }

    //Funcionalidad
    public void agregateCategoria(CategoriaOperacion categoriaOperacion) {
        this.categorias.add(categoriaOperacion);
    }

    public void borraCategoria(CategoriaOperacion categoriaOperacion) {
        this.categorias.remove(categoriaOperacion);
    }


}
