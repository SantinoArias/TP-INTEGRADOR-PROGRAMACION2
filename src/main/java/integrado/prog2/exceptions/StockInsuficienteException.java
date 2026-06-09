package integrado.prog2.exceptions;

public class StockInsuficienteException extends NegocioException {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
