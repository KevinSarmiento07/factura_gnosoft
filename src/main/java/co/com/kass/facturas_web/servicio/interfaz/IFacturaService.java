package co.com.kass.facturas_web.servicio.interfaz;

import co.com.kass.facturas_web.modelo.Factura;
import co.com.kass.facturas_web.modelo.ItemFactura;

import java.sql.SQLException;
import java.util.List;

public interface IFacturaService {
    
    public List<Factura> listarFacturas();
    public int saveFactura(Factura factura) throws SQLException;
    public int deleteFactura(int idFactura);
    public Factura obtenerFacturaPorId(int id);
    public int actualizarFactura(Factura factura);
    
    
    public List<ItemFactura> listarItemFacturasByIdFactura(int idFactura);
    public int obtenerItemFacturaPorFacturaId(int id);
    public ItemFactura obtenerItemFacturaPorProductoIdYFacturaId(int idProducto, int idFactura);
    public int actualizarItemFactura(ItemFactura itemFactura, int idFactura);
    public int eliminarItemFacturaPorId(int idItem);
    public int guardarItemFacturaPorIdFactura(int idFactura, ItemFactura itemFactura);
}