
package Egreso;

import Usuario.Usuario;

import java.sql.Timestamp;


public class Egreso {

    //Atributos
    private long nroEgreso;
    private Timestamp fecha;
    private Proveedor proveedor;
    private Comprobante comprobante;
    private MedioDePago medioDePago;
    private Detalle detalle;
    private Usuario usuario;

    //Constructor
    public Egreso(long nroEgreso, Timestamp fecha, Proveedor proveedor, Comprobante comprobante,
                  MedioDePago medioDePago, Detalle detalle, Usuario usuario){
        this.nroEgreso = nroEgreso;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.comprobante = comprobante;
        this.medioDePago = medioDePago;
        this.detalle = detalle;
        this.usuario = usuario;
    }

    //Getters-Setters
    public long getNroEgreso() {
        return nroEgreso;
    }

    public void setNroEgreso(long nroEgreso) {
        this.nroEgreso = nroEgreso;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
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

    public MedioDePago getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public Detalle getDetalle(){
        return this.detalle;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //Metodos

    public void agregarItem(Item nuevoItem){//TODO: Cambiar por un producto entero y una cantidad
        //Agregar try catch?
        this.detalle.agregarItem(nuevoItem);
    }//El item se agrega sobre el detalle

    public double precioTotal(){
     return  detalle.total();
    }

    public void registrar(){
        //TODO: Agregar logica de registro(No definiada).
    }
}
