package Entidad;

import Entidad.Categorias.CategoriaEmpresa;

public class Empresa extends EntidadJuridica {
    //Atributos
    private String rubro;
    private String actividad;
    private String sector;
    private int cantPersonal;
    private double valorActivos;
    private double promVentaAnual;
    private CategoriaEmpresa categoria;

    //Constructor
    //TODO:AGREGAR CONSTRUCTOR


    //Getters-Setters


    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getCantPersonal() {
        return cantPersonal;
    }

    public void setCantPersonal(int cantPersonal) {
        this.cantPersonal = cantPersonal;
    }

    public double getValorActivos() {
        return valorActivos;
    }

    public void setValorActivos(double valorActivos) {
        this.valorActivos = valorActivos;
    }

    public double getPromVentaAnual() {
        return promVentaAnual;
    }

    public void setPromVentaAnual(double promVentaAnual) {
        this.promVentaAnual = promVentaAnual;
    }

    public CategoriaEmpresa getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEmpresa categoria) {
        this.categoria = categoria;
    }
}
