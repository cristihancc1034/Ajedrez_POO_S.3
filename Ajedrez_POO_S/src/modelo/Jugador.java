package modelo;

// Persona que juega con uno de los dos bandos
public class Jugador {

    private String nombre;
    private Color color;

    public Jugador(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
    }

    // revisa que el nombre no este vacio y no pase de 20 letras
    // devuelve el mensaje de error, o null si esta bien
    public static String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre no puede estar vacio.";
        }
        if (nombre.trim().length() > 20) {
            return "El nombre no puede pasar de 20 caracteres.";
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return nombre + " (" + color.getNombre() + ")";
    }
}
