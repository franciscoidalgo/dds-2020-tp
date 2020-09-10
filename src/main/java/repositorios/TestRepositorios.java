package repositorios;

import passwordHasher.PasswordHasher;
import repositorios.daos.DAOMemoria;

public class TestRepositorios {
    public static void main(String[] args) {
        RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios(
                new DAOMemoria<>(TestUsuariosEnMemoria.generarUsuariosDePrueba())
        );
        if(repositorioDeUsuarios.verificarExistencia("Francisco Idalgo", PasswordHasher.hash("12345"))){
            System.out.println("Funciona!");
        } else System.out.println("Fuck");
    }
}
