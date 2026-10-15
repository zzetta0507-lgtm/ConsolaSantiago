package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estadisticas {
    private static Map<String, List<RegistroPuntaje>> topScores = new HashMap<>();

    public static void registrar(String nombreJuego, String jugador, double puntaje) {
        topScores.putIfAbsent(nombreJuego, new ArrayList<>());
        List<RegistroPuntaje> lista = topScores.get(nombreJuego);
        
        lista.add(new RegistroPuntaje(jugador, puntaje));
        Collections.sort(lista);
        
        if (lista.size() > 3) {
            lista.remove(lista.size() - 1);
        }
    }

    public static void mostrar() {
        System.out.println("\n========= ESTADÍSTICAS Y RÉCORDS (TOP 3) =========");
        if (topScores.isEmpty()) {
            System.out.println("Aún no hay récords registrados.");
        } else {
            for (String juego : topScores.keySet()) {
                System.out.println("[ " + juego.toUpperCase() + " ]");
                List<RegistroPuntaje> lista = topScores.get(juego);
                for (int i = 0; i < lista.size(); i++) {
                    RegistroPuntaje rp = lista.get(i);
                    System.out.println("   " + (i + 1) + ". " + rp.getJugador() + " - Puntos/Saldo: " + rp.getPuntaje());
                }
                System.out.println("--------------------------------------------------");
            }
        }
        System.out.println("==================================================");
    }
}