package Entidad;


public class Empresa extends EntidadJuridica implements Categorizable {
    //Atributos
    private String actividad;
    private Sector sector;
    private int cantPersonal;
    private int promVentasAnual;
    private Categoria categoria;

    //Constructor
    public Empresa(Integer cantPersonal, Integer promVentasAnual, Sector sector, Categorizador categorizador){
        this.cantPersonal=cantPersonal;
        this.promVentasAnual=promVentasAnual;
        this.sector = sector;
        this.categoria = categorizador.categoriza(this);
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
}
