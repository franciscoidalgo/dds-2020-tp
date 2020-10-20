package domain.Entidad.CategorizacionOperacion;

import domain.Entidad.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categoria_operacion")
public class CategoriaOperacion extends EntidadPersistente {

    @Column(name = "descripcion")
    private String descripcion;

    //Constructor
    public CategoriaOperacion(String descripcion){
        this.descripcion = descripcion;
    }

    public CategoriaOperacion() {}

    //Getter Setter
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

}
