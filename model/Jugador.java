package model;

public class Jugador {
    private String nombre;
    private double saldo;
    private int puntos;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.saldo = 100.0; 
        this.puntos = 0;
    }

    public String getNombre() { return nombre; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public boolean descontarSaldo(double cantidad) {
        if (this.saldo >= cantidad) {
            this.saldo -= cantidad;
            return true;
        }
        return false;
    }

    public void sumarSaldo(double cantidad) { this.saldo += cantidad; }
    public void sumarPuntos(int cantidad) { this.puntos += cantidad; }
}