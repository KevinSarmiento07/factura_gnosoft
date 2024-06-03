package co.com.kass.facturas_web.servicio.implement;


import co.com.kass.facturas_web.dao.IProductoDao;
import co.com.kass.facturas_web.dao.ProductoDaoJdbcImple;
import co.com.kass.facturas_web.modelo.Producto;
import co.com.kass.facturas_web.servicio.interfaz.IProductoService;

import java.util.List;

public class ProductoServiceImple implements IProductoService {
    
    private IProductoDao productoDao = new ProductoDaoJdbcImple();
    
    @Override
    public List<Producto> listar() {
        return productoDao.findAll();
    }
    
    @Override
    public Producto buscarPorId(Integer id) {
        return productoDao.getProductoById(id);
    }
    
    @Override
    public int guardarProducto(Producto producto) {
        return productoDao.save(producto);
    }
    
    @Override
    public int eliminarProducto(int id) {
        return productoDao.delete(id);
    }
}
