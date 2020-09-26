package repositorios.factories;

import config.ConfiguracionPersistencia;
import middleware.LoginToken;
import repositorios.RepositorioDeTokens;
import repositorios.daos.DAOHibernate;
import repositorios.daos.DAOMemoria;

import java.util.ArrayList;

public class FactoryRepoTokens {
    private static RepositorioDeTokens repositorioDeTokens = null;

    public static RepositorioDeTokens get(){
        if(repositorioDeTokens==null){
            if (ConfiguracionPersistencia.persistirEnBd) {
                repositorioDeTokens = new RepositorioDeTokens(new DAOHibernate<>(LoginToken.class));
            }else{
                repositorioDeTokens = new RepositorioDeTokens(new DAOMemoria<>(new ArrayList<>()));
            }
        }
        return repositorioDeTokens;
    }
}
