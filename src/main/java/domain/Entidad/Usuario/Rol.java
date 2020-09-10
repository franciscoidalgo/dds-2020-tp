package domain.Entidad.Usuario;

import domain.Entidad.CategorizacionOperacion.Criterio;

public interface Rol {
    //Metodos
    void daleJerarquiaA(Criterio unCriterioPadre, Criterio unCriterioHijo) throws Exception;
}