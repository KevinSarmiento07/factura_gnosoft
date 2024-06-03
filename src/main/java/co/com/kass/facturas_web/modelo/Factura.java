package co.com.kass.facturas_web.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Factura {
    
    private Integer id;
    
    private String nombreCliente;
    
    private Date fecha;
    
    private Double subtotal;
    
    private Double total;
    
    private Double iva;
    
    private List<ItemFactura>  items = new ArrayList<>();
    
    public Factura(Integer id, String nombreCliente, Date fecha, Double subtotal, Double total, Double iva, List<ItemFactura> items) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.total = total;
        this.iva = iva;
        this.items = items;
    }
    
    public Factura() {};
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    
    public Double getTotal() {
        return total;
    }
    
    public void setTotal(Double total) {
        this.total = total;
    }
    
    public Double getIva() {
        return iva;
    }
    
    public void setIva(Double iva) {
        this.iva = iva;
    }
    
    public List<ItemFactura> getItems() {
        return items;
    }
    
    public void setItems(List<ItemFactura> items) {
        this.items = items;
    }
    
    @Override
    public String toString() {
        return "Factura{" + "id=" + id + ", nombreCliente='" + nombreCliente + '\'' + ", fecha=" + fecha + ", subtotal=" + subtotal + ", total=" + total + ", iva=" + iva + ", items=" + items + '}';
    }
}
