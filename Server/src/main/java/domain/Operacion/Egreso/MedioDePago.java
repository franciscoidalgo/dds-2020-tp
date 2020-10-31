package domain.Operacion.Egreso;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "medio_de_pago")
public class MedioDePago extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private TipoDePago tipoDePago;

    @Column(name = "moneda")
    private String moneda;

    //Constructors
    public MedioDePago() {}

    public MedioDePago(String nombre,String moneda) {
        this.nombre = nombre;
        this.moneda = moneda;
    }

    //Getter Setter
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public TipoDePago getTipoDePago() {
        return tipoDePago;
    }

    public void setTipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
    }

}
