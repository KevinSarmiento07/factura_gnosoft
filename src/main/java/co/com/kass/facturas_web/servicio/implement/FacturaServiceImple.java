package co.com.kass.facturas_web.servicio.implement;

import co.com.kass.facturas_web.dao.FacuraDaoJdbcImple;
import co.com.kass.facturas_web.dao.IFacturaDao;
import co.com.kass.facturas_web.modelo.Factura;
import co.com.kass.facturas_web.modelo.ItemFactura;
import co.com.kass.facturas_web.servicio.interfaz.IFacturaService;

import java.sql.SQLException;
import java.util.List;

public class FacturaServiceImple implements IFacturaService {
    
    private IFacturaDao facturaDao = new FacuraDaoJdbcImple();
    
    @Override
    public List<Factura> listarFacturas() {
        return facturaDao.listarFacturas();
    }
    
    @Override
    public int saveFactura(Factura factura) throws SQLException {
        return facturaDao.saveFactura(factura);
    }
    
    @Override
    public int deleteFactura(int idFactura) {
        return facturaDao.deleteFactura(idFactura);
    }
    
    @Override
    public List<ItemFactura> listarItemFacturasByIdFactura(int idFactura) {
        return facturaDao.listarItemFacturasByIdFactura(idFactura);
    }
    
    @Override
    public Factura obtenerFacturaPorId(int id) {
        return facturaDao.obtenerFacturaPorId(id);
    }
    
    @Override
    public int actualizarFactura(Factura factura) {
        return facturaDao.actualizarFactura(factura);
    }
    
    //ITEMS FACT
    @Override
    public int obtenerItemFacturaPorFacturaId(int id) {
        return facturaDao.obtenerItemFacturaPorFacturaId(id);
    }
    
    @Override
    public ItemFactura obtenerItemFacturaPorProductoIdYFacturaId(int idProducto, int idFactura) {
        return facturaDao.obtenerItemFacturaPorProductoIdYFacturaId(idProducto, idFactura);
    }
    
    @Override
    public int actualizarItemFactura(ItemFactura itemFactura, int idFactura) {
        return facturaDao.actualizarItemFactura(itemFactura,idFactura);
    }
    
    @Override
    public int eliminarItemFacturaPorId(int idItem) {
        return facturaDao.eliminarItemFacturaPorId(idItem);
    }
    
    @Override
    public int guardarItemFacturaPorIdFactura(int idFactura, ItemFactura itemFactura) {
        return facturaDao.guardarItemFacturaPorIdFactura(idFactura, itemFactura);
    }
}
