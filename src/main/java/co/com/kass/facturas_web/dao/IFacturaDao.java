package co.com.kass.facturas_web.dao;

import co.com.kass.facturas_web.modelo.Factura;
import co.com.kass.facturas_web.modelo.ItemFactura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface IFacturaDao {
    //FACTS
    public List<Factura> listarFacturas();
    public int saveFactura(Factura factura) throws SQLException;
    public int deleteFactura(int idFactura);
    public Factura obtenerFacturaPorId(int id);
    public int actualizarFactura(Factura factura);
    public void saveItemFactura(ItemFactura itemFactura, int idFactura, PreparedStatement ps);
    public List<ItemFactura> listarItemFacturasByIdFactura(int idFactura);
    
    //ITEMS FACT
    public int obtenerItemFacturaPorFacturaId(int id);
    public ItemFactura obtenerItemFacturaPorProductoIdYFacturaId(int idProducto, int idFactura);
    public int actualizarItemFactura(ItemFactura itemFactura, int idFactura);
    public int eliminarItemFacturaPorId(int idItem);
    public int guardarItemFacturaPorIdFactura(int idFactura, ItemFactura itemFactura);
    
    
    
}
