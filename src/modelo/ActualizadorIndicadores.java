package modelo;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ActualizadorIndicadores implements Runnable {
    private final GestorHotel gestor;
    private final JTextArea indicador;

    public ActualizadorIndicadores(GestorHotel gestor, JTextArea indicador) {
        this.gestor = gestor;
        this.indicador = indicador;
    }

    public static String textoIndicadores(String[] datos) {
        return "Total de habitaciones: " + datos[0]
                + "\nOcupadas: " + datos[1]
                + "\nNo ocupadas: " + datos[2]
                + "\nReservas confirmadas: " + datos[3]
                + "\nReservas vencidas: " + datos[4]
                + "\nIngresos esperados: ₡" + datos[5];
    }

    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();
        System.out.println(nombre + ": inicio");
        try {
            for (int ciclo = 1; ciclo <= 5; ciclo++) {
                String texto = textoIndicadores(gestor.obtenerIndicadores());
                SwingUtilities.invokeLater(() -> indicador.setText(texto));
                System.out.println(nombre + ": ciclo " + ciclo);
                if (ciclo < 5) Thread.sleep(1000);
            }
            System.out.println(nombre + ": finalizó");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.out.println(nombre + ": interrumpido");
        }
    }
}
