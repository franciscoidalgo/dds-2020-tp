package domain.Operacion.Egreso;

import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;
import domain.Entidad.Entidad;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "detalle_compra")
public class DetalleCompra extends Entidad {
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_operacion_id")
    private List<CategoriaOperacion> categorias;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "comprobante_id")
    private Comprobante comprobante;

    //Constructors
    public DetalleCompra(Solicitud solicitud, Proveedor proveedor) {
        this.solicitud = solicitud;
        this.categorias = new ArrayList<>();
        this.proveedor = proveedor;
        this.comprobante = null;
    }

    public DetalleCompra() {}

    //Getter and Setter
    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public List<CategoriaOperacion> getCriterio() {
        return categorias;
    }

    public void setCategoriaOperacion(List<CategoriaOperacion> categorias) {
        this.categorias = categorias;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    //Funcionalidad

    public Boolean solicitudTieneItems(){

        return this.solicitud.tieneItems();
    }
    public Boolean coincidenSolicitud(DetalleCompra unDetalle){
        return this.solicitud.coincidenSolicitud(unDetalle.getSolicitud());
    }
    public Boolean coincidenProveedores(DetalleCompra unDetalle){
        return this.proveedor == unDetalle.getProveedor();
    }
}
