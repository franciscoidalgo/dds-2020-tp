package Operacion.Egreso;

public abstract class MedioDePago {
    protected long id;

    //Getter Setter
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    //Funcionalidad
    protected abstract void actualizarTiposDePago();
    //Ni idea que hace este metodo
}
