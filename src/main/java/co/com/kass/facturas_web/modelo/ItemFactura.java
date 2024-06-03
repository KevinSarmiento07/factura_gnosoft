package co.com.kass.facturas_web.modelo;

public class ItemFactura {
    
    private Integer id;
    
    private Producto producto;
    
    private Integer cantidad;
    
    private Double valor;
    
    public ItemFactura(Integer id, Producto producto, Integer cantidad) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.valor = valorTotalItem(cantidad, producto.getValor());
    }
    
    public ItemFactura(){};
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public Double getValor() {
        return valor;
    }
    
    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    public double valorTotalItem(int cantidad, double valor){
        return cantidad*valor;
    }
    
    @Override
    public String toString() {
        return "ItemFactura{" + "id=" + id + ", producto=" + producto + ", cantidad=" + cantidad + ", valor=" + valor + '}';
    }
}
