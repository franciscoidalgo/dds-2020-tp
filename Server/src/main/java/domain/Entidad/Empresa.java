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
    private double promVentasAnual;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "cod_igj")
    private long codIGJ;
    //Constructor

    public class EmpresaDTO{
        private final String tipo = "Em";
        private String nombre;
        private String razonSocial;
        private String descripcion;
        private long CUIT;
        private DireccionPostal direccionPostal;
        private String actividad;
        private Sector sector;
        private int cantPersonal;
        private double promVentasAnual;
        private Categoria categoria;
        private long codIGJ;
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

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public long getCUIT() {
            return CUIT;
        }

        public void setCUIT(long CUIT) {
            this.CUIT = CUIT;
        }

        public DireccionPostal getDireccionPostal() {
            return direccionPostal;
        }

        public void setDireccionPostal(DireccionPostal direccionPostal) {
            this.direccionPostal = direccionPostal;
        }

        public String getActividad() {
            return actividad;
        }

        public void setActividad(String actividad) {
            this.actividad = actividad;
        }

        public Sector getSector() {
            return sector;
        }

        public void setSector(Sector sector) {
            this.sector = sector;
        }

        public int getCantPersonal() {
            return cantPersonal;
        }

        public void setCantPersonal(int cantPersonal) {
            this.cantPersonal = cantPersonal;
        }

        public double getPromVentasAnual() {
            return promVentasAnual;
        }

        public void setPromVentasAnual(double promVentasAnual) {
            this.promVentasAnual = promVentasAnual;
        }

        public Categoria getCategoria() {
            return categoria;
        }

        public void setCategoria(Categoria categoria) {
            this.categoria = categoria;
        }

        public long getCodIGJ() {
            return codIGJ;
        }

        public void setCodIGJ(long codIGJ) {
            this.codIGJ = codIGJ;
        }
    }

    public Empresa(String razonSocial, String nombre, long CUIT, String descripcion, DireccionPostal direccionPostal, long codIGJ, String actividad, Sector sector, int cantPersonal, double promVentasAnual) {
        super(razonSocial, nombre, CUIT, descripcion, direccionPostal);
        this.actividad = actividad;
        this.sector = sector;
        this.codIGJ = codIGJ;
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
    public Sector getSector() {
        return sector;
    }

    public int getCantPersonal() {
        return cantPersonal;
    }

    public void setCantPersonal(int cantPersonal) {
        this.cantPersonal = cantPersonal;
    }

    public double getPromVentasAnual() {
        return promVentasAnual;
    }

    public void setPromVentasAnual(int promVentasAnual) {
        this.promVentasAnual = promVentasAnual;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    @Override
    public Sector sector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
        recategorizate();
    }
    public long getCodIGJ() {
        return codIGJ;
    }

    public void setCodIGJ(long codIGJ) {
        this.codIGJ = codIGJ;
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
    public double promVentasAnual() {
        return promVentasAnual;
    }

    public void setPromVentaAnual(double promVentasAnual) {
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
        String template = "Una empresa "+this.actividad+
                           "("+this.codIGJ+")"+
                            "En la que trabaja "+this.cantPersonal+
                            ", obteniendo un promedio de ventas de $"+this.promVentasAnual+"."+
                           "Categorizada en la AFIP como "+this.sector.getNombre()+"-"+this.categoria.getNombre()+".";


        return this.descripcion+ template;
    }

    private void recategorizate(){
        if(this.sector != null) {
            this.categoria = new Categorizador().categoriza(this);
        }
    }


    public EmpresaDTO toDTO(){
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setNombre(this.nombre());
        empresaDTO.setCUIT(this.getCuit());
        empresaDTO.setDescripcion(this.getDescripcion());
        empresaDTO.setDireccionPostal(this.getDireccionPostal());

        empresaDTO.setActividad(this.getActividad());
        empresaDTO.setSector(this.getSector());
        empresaDTO.setCantPersonal(this.getCantPersonal());
        empresaDTO.setPromVentasAnual(this.getPromVentasAnual());
        empresaDTO.setCategoria(this.getCategoria());
        empresaDTO.setCodIGJ(this.getCodIGJ());

        return  empresaDTO;
    }
}
