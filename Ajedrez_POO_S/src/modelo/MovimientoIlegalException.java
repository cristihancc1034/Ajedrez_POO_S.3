package modelo;

// Se lanza cuando el jugador intenta una jugada que no se puede hacer
public class MovimientoIlegalException extends Exception {

    public MovimientoIlegalException(String mensaje) {
        super(mensaje);
    }
}
