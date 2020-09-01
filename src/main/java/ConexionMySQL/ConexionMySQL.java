package ConexionMySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionMySQL {
	//public static ConexionMySQL c = new ConexionMySQL();
	
	public String db = "GeSoc";
    public String url = "jdbc:mysql://localhost/"+db;
    public String user = "root";
    public String pass = "";

	java.sql.Connection cn;
    
    public ConexionMySQL(){
		
    }
    
    public void conectar(){
    	cn = crearConexion();
    }
    
    public Connection crearConexion(){

       Connection link = null;

       try{
           Class.forName("org.gjt.mm.mysql.Driver");
           //Class.forName("mysql-connector-java-5.1.16-bin.jar");
           link = DriverManager.getConnection(this.url, this.user, this.pass);
       }catch(Exception ex){
           //JOptionPane.showMessageDialog(null, ex);
    	   System.out.println("Error al conectar "+ex);
       }

       return link;

    }
    
    public void desconectar(){
    	try{
            cn.close();
        }catch(SQLException ex){
            System.out.println("Error al desconectar "+ex);
        }
    }
    
    public ResultSet consultar(String query){
    	ResultSet rset = null;
    	try{
    		Statement estado = cn.createStatement();
    		rset = estado.executeQuery(query);
    	} catch (SQLException ex){
    		System.out.println("Error MySQL: " + ex.getMessage());
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}
    	return rset;
    }
    
    public void ejecutarUpdate(String query){
    	try{
    		Statement estado = cn.createStatement();
    		estado.executeUpdate(query);
    	} catch (SQLException ex){
    		System.out.println("Error MySQL: " + ex.getMessage());
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}
    }
}











