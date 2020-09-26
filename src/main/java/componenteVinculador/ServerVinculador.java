package componenteVinculador;

import spark.Spark;
import spark.debug.DebugScreen;

import java.io.IOException;

public class ServerVinculador {
    public static void main(String[] args) throws IOException {
        Spark.port(9001);
        RouterVinculacion.init();
        DebugScreen.enableDebugScreen();
    }
}
