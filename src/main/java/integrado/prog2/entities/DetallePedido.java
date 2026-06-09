package integrado.prog2.entities;

public class DetallePedido extends Base {

    private Integer cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(Long id, Integer cantidad, Double subtotal, Producto producto) {
        super(id);
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        String nombreProducto = producto != null ? producto.getNombre() : "Sin producto";
        return "Detalle ID: " + getId()
                + " | Producto: " + nombreProducto
                + " | Cantidad: " + cantidad
                + " | Subtotal: $" + String.format("%.2f", subtotal);
    }
}
