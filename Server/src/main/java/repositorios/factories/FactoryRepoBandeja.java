package repositorios.factories;

import config.ConfiguracionPersistencia;
import domain.Entidad.Usuario.BandejaMensaje;
import repositorios.RepositorioBandejasDeMensajes;
import repositorios.daos.DAOHibernate;
import repositorios.daos.DAOMemoria;

public class FactoryRepoBandeja extends FactoryRepo{
    private static RepositorioBandejasDeMensajes repositorioBandejasDeMensajes = null;

    public static RepositorioBandejasDeMensajes get(){
        if(repositorioBandejasDeMensajes == null) {
            if (ConfiguracionPersistencia.persistirEnBd) {
                repositorioBandejasDeMensajes = new RepositorioBandejasDeMensajes(new DAOHibernate<>(BandejaMensaje.class));
            } else {
                repositorioBandejasDeMensajes = new RepositorioBandejasDeMensajes(new DAOMemoria<>(null));
            }
        }
        return repositorioBandejasDeMensajes;
    }
}
