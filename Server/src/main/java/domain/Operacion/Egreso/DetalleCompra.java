package domain.Operacion.Egreso;

import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;

import java.util.ArrayList;
import java.util.List;

public class DetalleCompra {
    private Solicitud solicitud;
    private List<CategoriaOperacion> categorias;
    private Proveedor proveedor;
    private Comprobante comprobante;

    public DetalleCompra(Solicitud solicitud, Proveedor proveedor) {
        this.solicitud = solicitud;
        this.categorias = new ArrayList<>();
        this.proveedor = proveedor;
        this.comprobante = null;
    }


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
    public double montoTotal() {
        return this.solicitud.calcularSubtotal();
    }

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
