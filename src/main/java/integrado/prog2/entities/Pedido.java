package integrado.prog2.entities;

import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exceptions.StockInsuficienteException;
import integrado.prog2.exceptions.ValidacionException;
import integrado.prog2.interfaces.Calculable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;
    private ComprobantePedido comprobante;

    public Pedido(Long id, Usuario usuario, FormaPago formaPago) {
        super(id);
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public List<DetallePedido> getDetallesActivos() {
        return detalles.stream().filter(DetallePedido::isActivo).toList();
    }

    public ComprobantePedido getComprobante() {
        return comprobante;
    }

    public void asignarComprobante(ComprobantePedido comprobante) {
        this.comprobante = comprobante;
    }

    public void addDetallePedido(int cantidad, Double subtotal, Producto producto)
            throws StockInsuficienteException, ValidacionException {

        if (producto == null || producto.isEliminado()) {
            throw new ValidacionException("El producto seleccionado no existe o fue eliminado.");
        }
        if (!producto.isDisponible()) {
            throw new ValidacionException("El producto no se encuentra disponible: " + producto.getNombre());
        }

        DetallePedido detalleExistente = findeDetallePedidoByProducto(producto);
        int cantidadActual = detalleExistente != null ? detalleExistente.getCantidad() : 0;
        producto.validarStockPara(cantidadActual + cantidad);

        double subtotalCalculado = producto.getPrecio() * cantidad;
        if (subtotal != null && subtotal > 0) {
            subtotalCalculado = subtotal;
        }

        if (detalleExistente == null) {
            DetallePedido nuevoDetalle = new DetallePedido(null, cantidad, subtotalCalculado, producto);
            detalles.add(nuevoDetalle);
        } else {
            detalleExistente.setCantidad(cantidadActual + cantidad);
            detalleExistente.setSubtotal(detalleExistente.getSubtotal() + subtotalCalculado);
        }
        calcularTotal();
    }

    // Se conserva este nombre porque aparece asi en el UML de la consigna.
    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        if (producto == null) {
            return null;
        }
        return detalles.stream()
                .filter(DetallePedido::isActivo)
                .filter(detalle -> producto.equals(detalle.getProducto()))
                .findFirst()
                .orElse(null);
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findeDetallePedidoByProducto(producto);
        if (detalle != null) {
            detalle.eliminar();
            calcularTotal();
        }
    }

    @Override
    public void calcularTotal() {
        this.total = detalles.stream()
                .filter(DetallePedido::isActivo)
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }

    @Override
    public void eliminar() {
        super.eliminar();
        for (DetallePedido detalle : detalles) {
            detalle.eliminar();
        }
        if (comprobante != null) {
            comprobante.eliminar();
        }
    }

    @Override
    public String toString() {
        String nombreUsuario = usuario != null ? usuario.getNombre() + " " + usuario.getApellido() : "Sin usuario";
        return "ID: " + getId()
                + " | Usuario: " + nombreUsuario
                + " | Estado: " + estado
                + " | Pago: " + formaPago
                + " | Total: $" + String.format("%.2f", total)
                + " | Fecha: " + fecha;
    }
}
