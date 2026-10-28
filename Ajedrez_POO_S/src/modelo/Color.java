package modelo;

// Los dos bandos del juego
public enum Color {
    BLANCO, NEGRO;

    // devuelve el otro bando
    public Color contrario() {
        if (this == BLANCO) {
            return NEGRO;
        }
        return BLANCO;
    }

    // las blancas suben (fila baja) y las negras bajan (fila sube)
    public int direccionPeon() {
        if (this == BLANCO) {
            return -1;
        }
        return 1;
    }

    public int filaInicialPeones() {
        if (this == BLANCO) {
            return 6;
        }
        return 1;
    }

    public int filaBase() {
        if (this == BLANCO) {
            return 7;
        }
        return 0;
    }

    // fila donde el peon se corona
    public int filaCoronacion() {
        if (this == BLANCO) {
            return 0;
        }
        return 7;
    }

    public String getNombre() {
        if (this == BLANCO) {
            return "Blancas";
        }
        return "Negras";
    }
}
