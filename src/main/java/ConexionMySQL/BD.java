package ConexionMySQL;
import java.sql.*;

public class BD {
	
	private static Connection con;
	private static Statement st;
	
	public static Connection conectar()
	{
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + "C:\\Git\\2020-ma-ma-group-09\\src\\main\\resources\\Gesoc.db");
			con = c;
			c.setAutoCommit(false);
			return c;
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return null;
	}
	
	public static void realizarABM(String query) {
		try{
			con.commit();
			con.close();
			st.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		Statement stmt = null;
		Connection c = conectar();
		st=stmt;
		con = c;
		try {
			if(c == null){
				System.out.println("Error en DB");
			}else{
				stmt = c.createStatement();
				stmt.executeUpdate(query);
				stmt.close();
				c.commit();
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet seleccionar(String query) {
		try{
			con.commit();
			con.close();
			st.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		Statement stmt = null;
		Connection c = conectar();
		st=stmt;
		con = c;
		try {
			if(c == null){
				System.out.println("Error en DB");
			}else{
				stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				st=stmt;
				return rs;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
