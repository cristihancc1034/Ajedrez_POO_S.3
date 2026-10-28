package modelo;

// Se ejecuta cuando cualquier jugador comete un movimiento invalido
public class MovimientoIlegalException extends Exception {

    public MovimientoIlegalException(String mensaje) {
        super(mensaje);
    }
}
