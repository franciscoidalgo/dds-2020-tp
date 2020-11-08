package domain.Usuario;

import domain.Operacion.CategorizacionOperacion.Criterio;

public interface Rol {
    //Metodos
    void daleJerarquiaA(Criterio unCriterioPadre, Criterio unCriterioHijo) throws Exception;
}