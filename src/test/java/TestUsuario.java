import Usuario.Usuario;
import org.junit.Before;

public class TestUsuario {

        Usuario unEstandar;
        Usuario unAdmin;

        @Before
        public void setUp() throws Exception {

            unEstandar = new Generador().generaUsuarioEstandar();
            unAdmin= new Generador().generaUsuarioAdmin();

        }


}
