package co.com.kass.facturas_web.servicio.implement;

import co.com.kass.facturas_web.dao.FacuraDaoJdbcImple;
import co.com.kass.facturas_web.dao.IFacturaDao;
import co.com.kass.facturas_web.dao.IProductoDao;
import co.com.kass.facturas_web.dao.ProductoDaoJdbcImple;
import co.com.kass.facturas_web.modelo.Factura;
import co.com.kass.facturas_web.modelo.ItemFactura;
import co.com.kass.facturas_web.modelo.Producto;
import co.com.kass.facturas_web.servicio.interfaz.IFacturaService;
import co.com.kass.facturas_web.servicio.interfaz.IProductoService;

import java.sql.SQLException;
import java.util.*;

public class FacturaServiceImple implements IFacturaService {
    
    private IFacturaDao facturaDao = new FacuraDaoJdbcImple();
    
    private IProductoService productoService = new ProductoServiceImple();
    
    @Override
    public List<Factura> listarFacturas() {
        return facturaDao.listarFacturas();
    }
    
    @Override
    public int saveFactura(String nombreCliente, Date fecha, Double subtotal, Double total, Double iva, String productosId[], String productosCantidad[]) throws SQLException {
        List<ItemFactura> items = new ArrayList<>();
        ItemFactura item = null;
        
        
        for(int i = 0; i < productosId.length; i++){
            Producto producto = productoService.buscarPorId(Integer.parseInt(productosId[i]));
            item = new ItemFactura(0, producto, Integer.parseInt(productosCantidad[i]));
            items.add(item);
        }
        
        Factura factura = new Factura(0, nombreCliente, fecha,subtotal,total,iva, items);
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
    public int actualizarFactura(int idFactura, Date fecha,Double subtotal, Double total, Double iva,String productosId[],String productosCantidad[]) {
        List<ItemFactura> items = new ArrayList<>();
        List<ItemFactura> itemsEliminar = listarItemFacturasByIdFactura(idFactura);
        Set<Integer> productosIdSet = new HashSet<>();
        ItemFactura item = null;
        
        for (String idProd : productosId) {
            productosIdSet.add(Integer.valueOf(idProd));
        }
        for (ItemFactura itemEl : itemsEliminar) {
            if (!productosIdSet.contains(itemEl.getProducto().getId())) {
                eliminarItemFacturaPorId(itemEl.getId());
            }
        }
        for(int i = 0; i < productosId.length; i++){
            item = obtenerItemFacturaPorProductoIdYFacturaId(Integer.parseInt(productosId[i]),idFactura);
            if(item == null){
                Producto producto = productoService.buscarPorId(Integer.parseInt(productosId[i]));
                item = new ItemFactura(0, producto, Integer.parseInt(productosCantidad[i]));
                guardarItemFacturaPorIdFactura(idFactura, item);
            }else{
                item.setCantidad(Integer.valueOf(productosCantidad[i]));
                actualizarItemFactura(item, idFactura);
            }
            item = null;
        }
        
        Factura factura = obtenerFacturaPorId(idFactura);
        factura.setFecha(fecha);
        factura.setIva(iva);
        factura.setSubtotal(subtotal);
        factura.setTotal(total);
        
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
