package domain.Operacion.Egreso;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class Item extends EntidadPersistente {

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private TipoDeItem tipoDeItem;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    //Constructor
     public Item(String descripcion, TipoDeItem tipoDeItem, Double precioUnitario) {
        this.descripcion = descripcion;
        this.tipoDeItem = tipoDeItem;
        this.precioUnitario = precioUnitario;
    }
    public Item() {}

    //Setters+Getters
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDescripcion() { return descripcion; }

    public Double getPrecioUnitario() {return precioUnitario;}

    public void setPrecioUnitario(Double precioUnitario){
         this.precioUnitario = precioUnitario;
     }

    public TipoDeItem getTipoDeItem() {return tipoDeItem;}

    public void setTipoDeItem(TipoDeItem tipoDeItem) {this.tipoDeItem = tipoDeItem;}

    //Funcionalidad
    public Boolean coincidenItems(Item otroItem) {
        return this.tipoDeItem.getNombre().equalsIgnoreCase(otroItem.getTipoDeItem().getNombre()) &&
                this.descripcion.equalsIgnoreCase(otroItem.getDescripcion()) &&
                this.precioUnitario.equals(otroItem.getPrecioUnitario());
    }


}
