package co.com.kass.facturas_web.modelo;

public class Producto {
    
    private Integer id;
    
    private String nombre;
    
    private Double valor;
    
    public Producto(Integer id, String nombre, Double valor) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }
    public Producto(){};
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Double getValor() {
        return valor;
    }
    
    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre='" + nombre + '\'' + ", valor=" + valor + '}';
    }
}
