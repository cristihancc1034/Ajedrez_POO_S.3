package modelo;

// Jugador ya sea de las fichas blancas o negras
public class Jugador {

    private String nombre;
    private Color color;

    public Jugador(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
    }

    // Verifica que el nombre no este en vacio y que no sobrepase las 20 letras
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
