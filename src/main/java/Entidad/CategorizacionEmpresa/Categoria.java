package Entidad.CategorizacionEmpresa;


public class Categoria{

    private Integer maxCantPersonal;
    private Integer maxPromAnual;
    private String nombre;

    //Constructor
    public Categoria(Integer maxCantPersonal, Integer maxPromAnual, String nombre) {
        this.maxCantPersonal = maxCantPersonal;
        this.maxPromAnual = maxPromAnual;
        this.nombre = nombre;
    }
    //Setters and getters
    public Integer getMaxCantPersonal() {
        return maxCantPersonal;
    }

    public void setMaxCantPersonal(Integer maxCantPersonal) {
        this.maxCantPersonal = maxCantPersonal;
    }

    public Integer getMaxPromAnual() {
        return maxPromAnual;
    }

    public void setMaxPromAnual(Integer maxPromAnual) {
        this.maxPromAnual = maxPromAnual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Metodos funcionales
    public Boolean cumpleRequisitos(Categorizable categorizable){
        return this.cumpleConPersonal(categorizable) && this.cumpleConPromAnual(categorizable);
    }

    public  Boolean cumpleConPersonal(Categorizable categorizable){
        return categorizable.cantPersonal() < this.maxCantPersonal;
    }

    public  Boolean cumpleConPromAnual(Categorizable categorizable){
        return categorizable.promVentasAnual()<this.maxPromAnual;
    }

}



