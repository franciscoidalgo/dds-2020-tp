package domain.Entidad;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "organizacion")
public class Organizacion extends EntidadPersistente {

    @Column
    private String nombre;

    @OneToMany(mappedBy = "organizacion", cascade = {CascadeType.ALL})
    private List<Entidad> entidades;

    //Constructors
    public Organizacion(String nombre) {
        this.nombre = nombre;
        this.entidades = new ArrayList<>();
    }

    public Organizacion() {
    }

    //getter y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Entidad> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<Entidad> entidades) {
        this.entidades = entidades;
    }

    public void agregarEntidad(Entidad entidad) {
        this.entidades.add(entidad);
    }

    public void quitarEntidad(Entidad entidad) {
        this.entidades.remove(entidad);
    }

    public List<Empresa> getEmpresas() {
        return this.entidades.stream().filter(entidad -> entidad instanceof Empresa)
                .map(entidad -> (Empresa) entidad).collect(Collectors.toList());
    }
    public List<OrganizacionSocial> getOrganizacionSociales() {
        return this.entidades.stream().filter(entidad -> entidad instanceof OrganizacionSocial)
                .map(entidad -> (OrganizacionSocial) entidad).collect(Collectors.toList());
    }
    public List<EntidadBase> getEntidadesBase() {
        return this.entidades.stream().filter(entidad -> entidad instanceof EntidadBase)
                .map(entidad -> (EntidadBase) entidad).collect(Collectors.toList());
    }
}
