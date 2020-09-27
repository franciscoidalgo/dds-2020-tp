package requiredModels.entidad;

import requiredModels.operacion.Operacion;

import java.util.List;

public abstract class EntidadJuridica extends Entidad {
    protected String razonSocial;
    protected String nombre;
    protected long CUIT;
    protected String descripcion;
    protected DireccionPostal direccionPostal;
    protected long codIGJ;
    protected List<EntidadBase> entidadesBases;
    protected List<Operacion> operaciones;
    protected List<Criterio> criterios;

}
