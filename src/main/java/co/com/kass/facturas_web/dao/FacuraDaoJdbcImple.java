package co.com.kass.facturas_web.dao;

import co.com.kass.facturas_web.bdconexion.Conexion;
import co.com.kass.facturas_web.modelo.Factura;
import co.com.kass.facturas_web.modelo.ItemFactura;
import co.com.kass.facturas_web.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacuraDaoJdbcImple implements IFacturaDao {
    
    
    //TABLA FACTURAS
    private static final String SELECT_ALL = "SELECT * FROM facturas";
    private static final String SELECT_FACTURA_POR_ID = "SELECT * FROM facturas where fact_id = ?";
    private static final String INSERT_FACTURA = "INSERT INTO facturas(fact_nombre_cliente, fact_fecha, fact_subtotal, fact_iva, fact_total)VALUES ( ?, ?, ?, ?, ?)";
    private static final String UPDATE_FACTURA = "UPDATE facturas SET fact_fecha=?, fact_subtotal=?, fact_iva=?, fact_total=? WHERE fact_id = ?";
    private static final String DELETE_FACTURA = "DELETE FROM facturas WHERE fact_id = ?";
    
    //TABLA ITEMS FACTURAS
    private static final String SELECT_FACT_BY_ID = "SELECT * FROM items_facturas WHERE item_fact_id = ?";
    private static final String INSERT_ITEM_FACT = "INSERT INTO items_facturas(item_fact_id, item_cantidad, item_prod_id)VALUES (?, ?, ?)";
    private static final String DELETE_ITEM_FACTURA_POR_ID = "DELETE FROM items_facturas WHERE item_id = ?";
    private static final String UPDATE_ITEM_FACTURA_POR_ID_Y_FACTURA_ID = "UPDATE items_facturas SET item_cantidad=? WHERE item_fact_id = ? and item_id = ?";
    private static final String SELECT_ITEM_FACTURA_BY_ID = "SELECT item_id, item_fact_id, item_cantidad, item_prod_id FROM items_facturas WHERE item_fact_id = ?";
    private static final String INSERT_ITEM_FACTURA = "INSERT INTO items_facturas( item_fact_id, item_cantidad, item_prod_id)VALUES (?, ?, ?)";
    private static final String SELECT_ITEM_FACTURA_BY_PRODUCTO_AND_FACTURA_ID = "SELECT * FROM items_facturas WHERE item_prod_id  = ? and item_fact_id =?";
    private IProductoDao productoDao = new ProductoDaoJdbcImple();
    
    @Override
    public List<Factura> listarFacturas() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Factura> facturas = new ArrayList<>();
        Factura factura = null;
        
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SELECT_ALL);
            rs = ps.executeQuery();
            while(rs.next()) {
                
                int idFactura = rs.getInt("fact_id");
                String nombreCliente = rs.getString("fact_nombre_cliente");
                Date fecha = rs.getDate("fact_fecha");
                double subtotal = rs.getDouble("fact_subtotal");
                double iva = rs.getDouble("fact_iva");
                double total = rs.getDouble("fact_total");
                List<ItemFactura> itemsFact = new ArrayList<>();
                itemsFact = listarItemFacturasByIdFactura(idFactura);
                factura = new Factura(idFactura, nombreCliente, fecha, subtotal, total, iva, itemsFact);
                facturas.add(factura);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(conn, ps);
        }
        
        return facturas;
    }
    
    @Override
    public int saveFactura(Factura factura) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement psItem = null;
        java.sql.Date dateSQL = new java.sql.Date(factura.getFecha().getTime());
        int valor = 0;
        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(INSERT_FACTURA, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, factura.getNombreCliente());
            ps.setDate(2, dateSQL);
            ps.setDouble(3, factura.getSubtotal());
            ps.setDouble(4, factura.getIva());
            ps.setDouble(5, factura.getTotal());
            valor = ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            int idFactura = 0;
            if(rs.next()) {
                idFactura = rs.getInt("fact_id");
            }
            psItem = conn.prepareStatement(INSERT_ITEM_FACT);
            for(ItemFactura item : factura.getItems()) {
                saveItemFactura(item, idFactura, psItem);
                psItem.addBatch();
            }
            psItem.executeBatch();
            conn.commit();
        } catch(SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            Conexion.close(conn, ps);
            psItem.close();
            
        }
        return valor;
    }
    
    @Override
    public int deleteFactura(int idFactura) {
        Connection conn = null;
        PreparedStatement ps = null;
        int valor = 0;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(DELETE_FACTURA);
            ps.setInt(1, idFactura);
            valor = ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(conn, ps);
        }
        return valor;
    }
    
    @Override
    public Factura obtenerFacturaPorId(int id) {
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Factura factura = null;
        
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SELECT_FACTURA_POR_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                factura = new Factura();
                List<ItemFactura> itemFacturas = new ArrayList<>();
                factura.setId(rs.getInt("fact_id"));
                factura.setNombreCliente(rs.getString("fact_nombre_cliente"));
                factura.setFecha(rs.getDate("fact_fecha"));
                factura.setSubtotal(rs.getDouble("fact_subtotal"));
                factura.setTotal(rs.getDouble("fact_total"));
                factura.setIva(rs.getDouble("fact_iva"));
                factura.setItems(listarItemFacturasByIdFactura(factura.getId()));
                
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Conexion.close(rs);
            Conexion.close(conn, ps);
        }
        return factura;
    }
    
    @Override
    public int actualizarFactura(Factura factura) {
        Connection conn = null;
        PreparedStatement ps = null;
        java.sql.Date fecha = new java.sql.Date(factura.getFecha().getTime());
        int rows = 0;
        try{
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(UPDATE_FACTURA);
            ps.setDate(1, fecha);
            ps.setDouble(2, factura.getSubtotal());
            ps.setDouble(3, factura.getIva());
            ps.setDouble(4, factura.getTotal());
            ps.setInt(5, factura.getId());
            
            rows  = ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            Conexion.close(conn, ps);
        }
        return rows;
    }
    
    //ITEMS FACT
    @Override
    public void saveItemFactura(ItemFactura item, int idFactura, PreparedStatement ps) {
        try {
            ps.setInt(1, idFactura);
            ps.setInt(2, item.getCantidad());
            ps.setInt(3, item.getProducto().getId());
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<ItemFactura> listarItemFacturasByIdFactura(int idFactura) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        List<ItemFactura> itemFacturas = new ArrayList<>();
        ItemFactura itemFactura = null;
        Producto producto = null;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SELECT_FACT_BY_ID);
            ps.setInt(1, idFactura);
            rs = ps.executeQuery();
            while(rs.next()) {
                int itemId = rs.getInt("item_id");
                int itemFactId = rs.getInt("item_fact_id");
                int itemCantidad = rs.getInt("item_cantidad");
                int itemProdId = rs.getInt("item_prod_id");
                producto = productoDao.getProductoById(itemProdId);
                itemFactura = new ItemFactura(itemId, producto, itemCantidad);
                itemFacturas.add(itemFactura);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(conn, ps);
        }
        return itemFacturas;
    }
    
    @Override
    public int obtenerItemFacturaPorFacturaId(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SELECT_ITEM_FACTURA_BY_ID);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public ItemFactura obtenerItemFacturaPorProductoIdYFacturaId(int idProducto, int idFactura) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ItemFactura itemFactura = null;
        try{
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SELECT_ITEM_FACTURA_BY_PRODUCTO_AND_FACTURA_ID);
            ps.setInt(1, idProducto);
            ps.setInt(2, idFactura);
            
            rs = ps.executeQuery();
            if(rs.next()){
                int idItem = rs.getInt("item_id");
                Producto producto = productoDao.getProductoById(rs.getInt("item_prod_id"));
                int cantidad = rs.getInt("item_cantidad");
                itemFactura = new ItemFactura(idItem,producto, cantidad);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return itemFactura;
    }
    
    @Override
    public int actualizarItemFactura(ItemFactura itemFactura, int idFactura) {
        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(UPDATE_ITEM_FACTURA_POR_ID_Y_FACTURA_ID);
            ps.setInt(1, itemFactura.getCantidad());
            ps.setInt(2, idFactura);
            ps.setInt(3,itemFactura.getId());
            rows = ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }finally{
            Conexion.close(conn, ps);
        }
        return rows;
    }
    
    @Override
    public int eliminarItemFacturaPorId(int idItem) {
        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(DELETE_ITEM_FACTURA_POR_ID);
            ps.setInt(1, idItem);
            rows = ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }finally{
            Conexion.close(conn, ps);
        }
        return rows;
    }
    
    @Override
    public int guardarItemFacturaPorIdFactura(int idFactura, ItemFactura itemFactura) {
        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(INSERT_ITEM_FACTURA);
            ps.setInt(1, idFactura);
            ps.setInt(2, itemFactura.getCantidad());
            ps.setInt(3, itemFactura.getProducto().getId());
            rows = ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }finally{
            Conexion.close(conn, ps);
        }
        return rows;
    }
}
