package co.com.kass.facturas_web.dao;

import co.com.kass.facturas_web.modelo.Producto;

import java.util.List;

public interface IProductoDao {
    
    public List<Producto> findAll();
    
    public Producto getProductoById(int id);
    
    public int delete(int id);
    
    public int save(Producto producto);
}
