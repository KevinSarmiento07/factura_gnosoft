package co.com.kass.facturas_web.controlador;


import co.com.kass.facturas_web.modelo.Producto;
import co.com.kass.facturas_web.servicio.implement.ProductoServiceImple;
import co.com.kass.facturas_web.servicio.interfaz.IProductoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/productos", "/productos/crear"})
public class ProductoServlet extends HttpServlet {
    
    private IProductoService productoService = new ProductoServiceImple();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contextPath = req.getServletPath();
        switch(contextPath){
            case "/productos":
                listProductos(req,resp);
                break;
            
            case "/productos/crear":
                formCreateProd(req,resp);
                break;
        }
        
       
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String nombre = req.getParameter("nombre");
        double valor = Double.parseDouble(req.getParameter("valor"));
        Producto producto = new Producto(0,nombre, valor);
        productoService.guardarProducto(producto);
        
        resp.sendRedirect("/productos?success=true");
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = 0;
        id = Integer.parseInt(req.getParameter("id"));
        int i = productoService.eliminarProducto(id);
        if(i == 1){
            resp.setStatus(204);
        }else{
            resp.setStatus(404);
        }
    }
    
    
    private void listProductos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productos", productoService.listar());
        req.getRequestDispatcher("/META-INF/producto/listado.jsp").forward(req, resp);
    }
    public void formCreateProd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/META-INF/producto/formProducto.jsp").forward(req, resp);
    }
}
