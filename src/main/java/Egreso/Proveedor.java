package Egreso;

public class Proveedor {
    private String nombre;
    private String razonSocial;
    private int DNI;
    private long CUIT;
    private String dirPostal;

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

    public String getDirPostal() {
        return dirPostal;
    }

    public void setDirPostal(String dirPostal) {
        this.dirPostal = dirPostal;
    }


}