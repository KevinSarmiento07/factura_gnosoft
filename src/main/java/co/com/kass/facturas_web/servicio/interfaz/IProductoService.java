package co.com.kass.facturas_web.servicio.interfaz;


import co.com.kass.facturas_web.modelo.Producto;

import java.util.List;

public interface IProductoService {
    
    public List<Producto> listar();
    
    public Producto buscarPorId(Integer id);
    
    public int guardarProducto(Producto producto);
    
    public int eliminarProducto(int id);
}
