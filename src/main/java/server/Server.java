package server;

import spark.Spark;
import spark.debug.DebugScreen;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        //Spark.port(9000);
        Spark.ipAddress("190.105.72.195");
        Router.init();
        DebugScreen.enableDebugScreen();
    }
}
