package Entidad;


import Entidad.CategorizacionEmpresa.Categoria;
import Entidad.CategorizacionEmpresa.Categorizable;
import Entidad.CategorizacionEmpresa.Sector;
import Entidad.CategorizacionEmpresa.Categorizador;

public class Empresa extends EntidadJuridica implements Categorizable {
    //Atributos
    private String actividad;
    private Sector sector;
    private int cantPersonal;
    private int promVentasAnual;
    private Categoria categoria;

    //Constructor

    public Empresa(String razonSocial, String nombre, long CUIT, String descripcion, String direccionPostal, long codIGJ, String actividad, Sector sector, int cantPersonal, int promVentasAnual) {
        super(razonSocial, nombre, CUIT, descripcion, direccionPostal, codIGJ);
        this.actividad = actividad;
        this.sector = sector;
        this.cantPersonal = cantPersonal;
        this.promVentasAnual = promVentasAnual;
        this.categoria = new Categorizador().categoriza(this);
    }


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

    public void sector(Sector sector) {
        this.sector = sector;
    }

    @Override
    public Integer cantPersonal() {
        return cantPersonal;
    }

    public void setCantPersonal(Integer cantPersonal) {
        this.cantPersonal = cantPersonal;
    }

    @Override
    public Integer promVentasAnual() {
        return promVentasAnual;
    }

    public void setPromVentaAnual(Integer promVentasAnual) {
        this.promVentasAnual = promVentasAnual;
    }


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String nombre() {
        return this.nombre;
    }

    @Override
    public String descripcion() {
        return this.descripcion;
    }
}
