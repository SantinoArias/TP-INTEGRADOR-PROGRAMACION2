package integrado.prog2.services;

import integrado.prog2.entities.ComprobantePedido;
import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exceptions.EntidadNoEncontradaException;
import integrado.prog2.exceptions.NegocioException;
import integrado.prog2.exceptions.ValidacionException;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private final List<Pedido> pedidos;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private long siguienteIdPedido;
    private long siguienteIdDetalle;
    private long siguienteIdComprobante;

    public PedidoService(UsuarioService usuarioService, ProductoService productoService) {
        this.pedidos = new ArrayList<>();
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.siguienteIdPedido = 1L;
        this.siguienteIdDetalle = 1L;
        this.siguienteIdComprobante = 1L;
    }

    public Pedido crearPedido(Long usuarioId, FormaPago formaPago, List<ItemPedido> items) throws NegocioException {
        if (formaPago == null) {
            throw new ValidacionException("La forma de pago es obligatoria.");
        }
        if (items == null || items.isEmpty()) {
            throw new ValidacionException("El pedido debe tener al menos un detalle.");
        }

        Usuario usuario = usuarioService.buscarActivoPorId(usuarioId);
        Pedido pedidoTemporal = new Pedido(siguienteIdPedido, usuario, formaPago);

        for (ItemPedido item : items) {
            Producto producto = productoService.buscarActivoPorId(item.productoId());
            pedidoTemporal.addDetallePedido(item.cantidad(), null, producto);
        }

        // Si llego hasta aca, no hubo errores. Recién ahora se confirma stock y memoria.
        for (DetallePedido detalle : pedidoTemporal.getDetallesActivos()) {
            detalle.setId(siguienteIdDetalle++);
            detalle.getProducto().restarStock(detalle.getCantidad());
        }

        pedidoTemporal.calcularTotal();
        ComprobantePedido comprobante = new ComprobantePedido(
                siguienteIdComprobante++,
                "CP-" + String.format("%04d", siguienteIdPedido),
                pedidoTemporal,
                pedidoTemporal.getTotal()
        );
        pedidoTemporal.asignarComprobante(comprobante);

        pedidos.add(pedidoTemporal);
        usuario.agregarPedido(pedidoTemporal);
        siguienteIdPedido++;
        return pedidoTemporal;
    }

    public List<Pedido> listarActivos() {
        return pedidos.stream()
                .filter(Pedido::isActivo)
                .toList();
    }

    public List<Pedido> listarPorUsuario(Long usuarioId) throws EntidadNoEncontradaException {
        Usuario usuario = usuarioService.buscarActivoPorId(usuarioId);
        return pedidos.stream()
                .filter(Pedido::isActivo)
                .filter(pedido -> usuario.equals(pedido.getUsuario()))
                .toList();
    }

    public Pedido buscarActivoPorId(Long id) throws EntidadNoEncontradaException {
        return pedidos.stream()
                .filter(Pedido::isActivo)
                .filter(pedido -> pedido.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe un pedido activo con ID " + id + "."));
    }

    public Pedido actualizarEstadoFormaPago(Long id, Estado estado, FormaPago formaPago)
            throws EntidadNoEncontradaException, ValidacionException {

        Pedido pedido = buscarActivoPorId(id);

        if (estado == null && formaPago == null) {
            throw new ValidacionException("Debe modificar al menos el estado o la forma de pago.");
        }
        if (estado != null) {
            pedido.setEstado(estado);
        }
        if (formaPago != null) {
            pedido.setFormaPago(formaPago);
        }
        return pedido;
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Pedido pedido = buscarActivoPorId(id);
        pedido.eliminar();
    }

    public record ItemPedido(Long productoId, int cantidad) {
    }
}
