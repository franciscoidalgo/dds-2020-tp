package repositorios.factories;

import config.ConfiguracionPersistencia;
import domain.Entidad.Usuario.Usuario;
import repositorios.RepositorioDeUsuarios;
import repositorios.TestUsuariosEnMemoria;
import repositorios.daos.DAOHibernate;
import repositorios.daos.DAOMemoria;

public class FactoryRepoUsuario {
    private static RepositorioDeUsuarios repositorioDeUsuarios = null;

    public static RepositorioDeUsuarios get(){
        if(repositorioDeUsuarios == null){
            if (ConfiguracionPersistencia.persistirEnBd){
                repositorioDeUsuarios = new RepositorioDeUsuarios(new DAOHibernate<>(Usuario.class));
            }else{
                repositorioDeUsuarios = new RepositorioDeUsuarios(new DAOMemoria<>(TestUsuariosEnMemoria.generarUsuariosDePrueba()));
            }
        }
        return repositorioDeUsuarios;
    }
}
