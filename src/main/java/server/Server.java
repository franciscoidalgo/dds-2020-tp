package server;

import spark.Spark;
import spark.debug.DebugScreen;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        Spark.port(Integer.parseInt(System.getenv("PORT")));
        Router.init();
        DebugScreen.enableDebugScreen();
    }
}
