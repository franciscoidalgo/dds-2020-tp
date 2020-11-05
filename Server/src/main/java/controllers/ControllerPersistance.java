package controllers;

import Persistencia.EntityManagerHelper;
import spark.Request;
import spark.Response;

public class ControllerPersistance {
    public Response abrirEm(Request request, Response response) {
        EntityManagerHelper.getEntityManager();
        return response;
    }


    public Response cerrarEm(Request request, Response response) {
        EntityManagerHelper.closeEntityManager();
        return response;
    }
}
