package co.com.kass.facturas_web.controlador;

import co.com.kass.facturas_web.modelo.Factura;
import co.com.kass.facturas_web.modelo.ItemFactura;
import co.com.kass.facturas_web.modelo.Producto;
import co.com.kass.facturas_web.servicio.implement.FacturaServiceImple;
import co.com.kass.facturas_web.servicio.implement.ProductoServiceImple;
import co.com.kass.facturas_web.servicio.interfaz.IFacturaService;
import co.com.kass.facturas_web.servicio.interfaz.IProductoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "FacturaServlet", urlPatterns = {"/facturas", "/facturas/crear", "/facturas/editar"})
@MultipartConfig
public class FacturaServlet extends HttpServlet {
    
    private IFacturaService facturaService = new FacturaServiceImple();
    
    private IProductoService productoService = new ProductoServiceImple();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String contextPath = req.getServletPath();
        switch(contextPath) {
            case "/facturas":
                listFacturas(req, resp);
                break;
            
            case "/facturas/crear":
                formCrearFact(req, resp);
                break;
            
            case "/facturas/editar":
                formEditFact(req, resp);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String nombreCliente = req.getParameter("nombreCliente");
        String fechaString = req.getParameter("fecha");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(fechaString);
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }
        double subtotal = Double.parseDouble(req.getParameter("subtotal"));
        double total = Double.parseDouble(req.getParameter("total"));
        double iva = Double.parseDouble(req.getParameter("iva"));
        String productosId[] = req.getParameter("productosId").split(",");
        String productosCantidad[] = req.getParameter("productosCantidad").split(",");
        
        try {
            facturaService.saveFactura(nombreCliente,date,subtotal,total,iva,productosId,productosCantidad);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/facturas?success=true");
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        int id = Integer.parseInt(req.getParameter("id"));
        String fechaString = req.getParameter("fecha");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(fechaString);
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }
        double subtotal = Double.parseDouble(req.getParameter("subtotal"));
        double total = Double.parseDouble(req.getParameter("total"));
        double iva = Double.parseDouble(req.getParameter("iva"));
        String productosId[] = req.getParameter("productosId").split(",");
        String productosCantidad[] = req.getParameter("productosCantidad").split(",");
        
        
        try {
            facturaService.actualizarFactura(id,date,subtotal,total,iva,productosId,productosCantidad);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        resp.setStatus(200);
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        int id = Integer.parseInt(req.getParameter("id"));
        int valor = facturaService.deleteFactura(id);
        if(valor == 1) {
            resp.setStatus(204);
        } else {
            resp.setStatus(404);
        }
    }
    
    private void listFacturas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("facturas", facturaService.listarFacturas());
        req.getRequestDispatcher("/META-INF/facturas/listadoFacturas.jsp").forward(req, resp);
    }
    
    public void formCrearFact(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productos", productoService.listar());
        req.getRequestDispatcher("/META-INF/facturas/formFactura.jsp").forward(req, resp);
    }
    
    public void formEditFact(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productos", productoService.listar());
        String id = req.getParameter("id");
        
        Factura factura = facturaService.obtenerFacturaPorId(Integer.parseInt(id));
        req.setAttribute("factura", factura);
        req.getRequestDispatcher("/META-INF/facturas/formEditFactura.jsp").forward(req, resp);
    }
}
