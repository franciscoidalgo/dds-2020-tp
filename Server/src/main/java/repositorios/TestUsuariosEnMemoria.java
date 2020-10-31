package repositorios;

import domain.DireccionPostal.DireccionPostal;
import domain.Entidad.EntidadPersistente;
import domain.Entidad.OrganizacionSocial;
import domain.Entidad.Usuario.RolAdministrador;
import domain.Entidad.Usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class TestUsuariosEnMemoria {

    public static List<EntidadPersistente> generarUsuariosDePrueba (){
        List<EntidadPersistente> listaDeUsuarios = new ArrayList<>();
        //La contraseña sin Hashear (que es la que debe ser ingresada) es 12345
        Usuario test1 = new Usuario("Francisco Idalgo",
                "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5",
                new RolAdministrador(),
                null);//new OrganizacionSocial("x", "x", 123, "x", new DireccionPostal(), 123));
        test1.setId(1);
        //aguanteelgrupo9
        Usuario test2 = new Usuario("El Admin",
                "828b26c012c8a52b19bddbbb55b1048458031f2a165d4bab72312c22532a19b9",
                new RolAdministrador(),
                null);//new OrganizacionSocial("x", "x", 123, "x", new DireccionPostal(), 123));
        test2.setId(2);

        listaDeUsuarios.add(test1);
        listaDeUsuarios.add(test2);

        return listaDeUsuarios;
    }

}
