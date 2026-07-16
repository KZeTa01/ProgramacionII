package Excepciones;

public class ValorExcedidoException extends Exception {
    public ValorExcedidoException(String mensaje) {
        super(mensaje); // Pasa el mensaje a la clase padre Exception
    }
}