package co.com.kass.facturas_web.bdconexion;

import java.sql.*;

public class Conexion {
    
    public static final String BD_URL = "jdbc:postgresql://localhost:5433/factura";
    
    private static final String BD_USER = "postgres";
    
    private static final String BD_PASSWORD = "admin";
    
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection(){
        
        try{
            //Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(BD_URL,BD_USER,BD_PASSWORD);
        }catch(SQLException  e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static void close(ResultSet rs){
        try{
            rs.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public static void close(Connection connection, PreparedStatement ps){
        try{
            ps.close();
            connection.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    
}
