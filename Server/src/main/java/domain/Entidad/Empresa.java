package domain.Entidad;


import domain.DireccionPostal.DireccionPostal;
import domain.Entidad.CategorizacionEmpresa.Categoria;
import domain.Entidad.CategorizacionEmpresa.Categorizable;
import domain.Entidad.CategorizacionEmpresa.Sector;
import domain.Entidad.CategorizacionEmpresa.Categorizador;

import javax.persistence.*;

@Entity
@DiscriminatorValue("empresa")
public class Empresa extends EntidadJuridica implements Categorizable {
    //Atributos
    @Column(name = "actividad")
    private String actividad;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Sector sector;

    @Column(name = "cant_personal")
    private int cantPersonal;

    @Column(name = "prom_venta_anual")
    private int promVentasAnual;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_empresa_id")
    private Categoria categoria;

    //Constructor

    public Empresa(String razonSocial, String nombre, long CUIT, String descripcion, DireccionPostal direccionPostal, long codIGJ, String actividad, Sector sector, int cantPersonal, int promVentasAnual) {
        super(razonSocial, nombre, CUIT, descripcion, direccionPostal, codIGJ);
        this.actividad = actividad;
        this.sector = sector;
        this.cantPersonal = cantPersonal;
        this.promVentasAnual = promVentasAnual;
        recategorizate();
    }

    public Empresa() {}


    //Getters-Setters

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    @Override
    public Sector sector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
        recategorizate();
    }

    @Override
    public Integer cantPersonal() {
        return cantPersonal;
    }

    public void setCantPersonal(Integer cantPersonal) {
        this.cantPersonal = cantPersonal;
        recategorizate();
    }

    @Override
    public Integer promVentasAnual() {
        return promVentasAnual;
    }

    public void setPromVentaAnual(Integer promVentasAnual) {
        this.promVentasAnual = promVentasAnual;
        recategorizate();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String nombre() {
        return this.nombre;
    }

    public String descripcion() {
        return this.descripcion;
    }

    private void recategorizate(){
        if(this.sector != null) {
            this.categoria = new Categorizador().categoriza(this);
        }
    }
}
