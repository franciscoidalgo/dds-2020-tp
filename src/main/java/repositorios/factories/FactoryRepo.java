package repositorios.factories;

import config.ConfiguracionPersistencia;
import repositorios.Repositorio;
import repositorios.daos.DAO;
import repositorios.daos.DAOHibernate;
import repositorios.daos.DAOMemoria;

import java.util.HashMap;

public class FactoryRepo {
    private static final HashMap<String, Repositorio> repos = new HashMap<>();

    public static <T> Repositorio<T> get(Class<T> type){
        Repositorio<T> repo;
        if (repos.containsKey(type.getName())){
            repo = repos.get(type.getName());
        }
        else{
            if(ConfiguracionPersistencia.persistirEnBd){
                DAO<T> dao = new DAOHibernate<>(type);
                repo = new Repositorio<>(dao);
            }else{
                repo = new Repositorio<>(new DAOMemoria<>(null));
            }
            repos.put(type.getName(), repo);
        }
        return repo;
    }
}
