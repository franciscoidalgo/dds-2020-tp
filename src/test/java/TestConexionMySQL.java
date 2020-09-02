import ConexionMySQL.BD;

import java.sql.ResultSet;

public class TestConexionMySQL {

    public static void main(String[] args) {
        BD.conectar();

        BD.realizarABM("INSERT INTO usuario(Nombre) VALUES('ASD')");
        //El auto increment no se pone!

        ResultSet r = BD.seleccionar("SELECT * FROM usuario");

        try{
            while(r.next()){
                System.out.println(r.getInt(1));
                System.out.println(r.getString(2));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}