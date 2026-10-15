package juegos;

import model.Jugador;

public abstract class Juego implements Jugable {
    protected Jugador jugador;

    public Juego(Jugador jugador) {
        this.jugador = jugador;
    }

    public abstract String getNombreJuego(); 
    public abstract void guardarEstadisticas();
}