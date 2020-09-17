package domain.Operacion.Egreso;

import domain.DireccionPostal.DireccionPostal;

public class Proveedor {
    private String nombre;
    private String razonSocial;
    private int DNI;
    private long CUIT;
    private DireccionPostal dirPostal;

    public Proveedor(String nombre, String razonSocial, int DNI, long CUIT, DireccionPostal dirPostal){
        this.nombre = nombre;
        this.razonSocial = razonSocial;
        this.DNI = DNI;
        this.CUIT = CUIT;
        this.dirPostal = dirPostal;
    }

    //Getter and Setter
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getDNI() {
        return DNI;
    }
    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public long getCUIT() {
        return CUIT;
    }
    public void setCUIT(long CUIT) {
        this.CUIT = CUIT;
    }

    public DireccionPostal getDirPostal() {
        return dirPostal;
    }
    public void setDirPostal(DireccionPostal dirPostal) {
        this.dirPostal = dirPostal;
    }




}
