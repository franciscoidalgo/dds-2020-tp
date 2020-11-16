package domain.Operacion.CategorizacionOperacion;

import domain.Entidad.CategorizacionEmpresa.Categoria;
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

    public class CriterioDTO{
        private int id;
        private String nombre;
        private List<CategoriaOperacion> categorias;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public List<CategoriaOperacion> getCategorias() {
            return categorias;
        }

        public void setCategorias(List<CategoriaOperacion> categorias) {
            this.categorias = categorias;
        }
    }
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

    public CriterioDTO toDTO(){
        CriterioDTO criterioDTO = new CriterioDTO();
        criterioDTO.setCategorias(this.categorias);
        criterioDTO.setId(this.getId());
        criterioDTO.setNombre(this.nombre);
        return criterioDTO;
    }
}
