package co.com.kass.facturas_web.dao;


import co.com.kass.facturas_web.bdconexion.Conexion;
import co.com.kass.facturas_web.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDaoJdbcImple implements  IProductoDao{
    
    private static final String SELECT_ALL = "SELECT * FROM productos";
    
    private static final String SELECT_BY_ID = "SELECT * FROM productos WHERE cli_id = ?";
    
    private static final String DELETE_BY_ID = "DELETE FROM productos WHERE cli_id = ?";
    
    private static final String INSERT_INTO = "INSERT INTO productos(cli_nombre, cli_valor)VALUES ( ?, ?)";
    @Override
    public List<Producto> findAll() {
        Connection conn = null;
        PreparedStatement ps =  null;
        ResultSet rs = null;
        List<Producto> productos = new ArrayList<Producto>();
        try{
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SELECT_ALL);
            rs = ps.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("cli_id");
                String nombre = rs.getString("cli_nombre");
                Double valor = rs.getDouble("cli_valor");
                productos.add(new Producto(id, nombre, valor));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally {
            Conexion.close(rs);
            Conexion.close(conn,ps);
        }
        return productos;
    }
    
    @Override
    public Producto getProductoById(int id) {
        Connection conn = null;
        PreparedStatement ps =  null;
        ResultSet rs = null;
        Producto producto = null;
        try{
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SELECT_BY_ID);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                producto = new Producto();
                producto.setId(rs.getInt("cli_id"));
                producto.setNombre(rs.getString("cli_nombre"));
                producto.setValor(rs.getDouble("cli_valor"));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally {
            Conexion.close(rs);
            Conexion.close(conn,ps);
        }
        return producto;
    }
    
    @Override
    public int delete(int id) {
        Connection conn = null;
        PreparedStatement ps =  null;
        int valor = 0;
        try{
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(DELETE_BY_ID);
            ps.setInt(1,id);
            valor = ps.executeUpdate();
            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally {
            Conexion.close(conn,ps);
        }
        return valor;
    }
    
    @Override
    public int save(Producto producto) {
        Connection conn = null;
        PreparedStatement ps =  null;
        int valor = 0;
        try{
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(INSERT_INTO);
            ps.setString(1,producto.getNombre());
            ps.setDouble(2,producto.getValor());
            valor = ps.executeUpdate();
            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally {
            Conexion.close(conn,ps);
        }
        return valor;
    }
}
