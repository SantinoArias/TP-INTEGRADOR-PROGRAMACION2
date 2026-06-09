package integrado.prog2.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ComprobantePedido extends Base {

    private String codigo;
    private LocalDateTime fechaEmision;
    private Double importe;
    private Pedido pedido;

    public ComprobantePedido(Long id, String codigo, Pedido pedido, Double importe) {
        super(id);
        this.codigo = codigo;
        this.pedido = pedido;
        this.importe = importe;
        this.fechaEmision = LocalDateTime.now();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String toString() {
        return "Comprobante: " + codigo
                + " | Fecha: " + fechaEmision.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                + " | Importe: $" + String.format("%.2f", importe);
    }
}
