package model;

// Implementa Comparable para poder ordenar y comparar los puntajes para el Top 3
public class RegistroPuntaje implements Comparable<RegistroPuntaje> {
    private String jugador;
    private double puntaje;

    public RegistroPuntaje(String jugador, double puntaje) {
        this.jugador = jugador;
        this.puntaje = puntaje;
    }

    public String getJugador() { return jugador; }
    public double getPuntaje() { return puntaje; }

    @Override
    public int compareTo(RegistroPuntaje otro) {
        // Ordena de mayor a menor usando >, <, ==
        if (this.puntaje < otro.puntaje) {
            return 1;
        } else if (this.puntaje > otro.puntaje) {
            return -1;
        }
        return 0; // Son iguales
    }
}