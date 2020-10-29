package server;

import spark.Spark;
import spark.debug.DebugScreen;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws Exception {
        Spark.port(9000);
        Router.init();
        DebugScreen.enableDebugScreen();
    }
}