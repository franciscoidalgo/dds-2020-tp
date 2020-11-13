package server;


import spark.Spark;


public class Server {
    public static void main(String[] args) throws Exception {

        Spark.port(9000);
        Router.init();
        //DebugScreen.enableDebugScreen();
    }

}
