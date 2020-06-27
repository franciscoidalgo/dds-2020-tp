package Usuario;

public class RolEstandar implements Rol {
    @Override
    public boolean criterioCredenciales() {
        return false;
    }
}
