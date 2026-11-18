package controlador;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import modelo.ActualizadorIndicadores;
import modelo.GestorHotel;
import modelo.SupervisorReservas;

public class ControladorSupervisor {
    private final GestorHotel gestor = GestorHotel.getInstancia();
    private static boolean enEjecucion = false;

    public static synchronized boolean estaEnEjecucion() {
        return enEjecucion;
    }

    private static synchronized boolean iniciar() {
        if (enEjecucion) return false;
        enEjecucion = true;
        return true;
    }

    private static synchronized void terminar() {
        enEjecucion = false;
    }

    public boolean ejecutar(JTextArea indicador, Runnable actualizarVista) {
        if (!iniciar()) return false;
        Thread supervisor = new Thread(new SupervisorReservas(gestor), "SupervisorReservas");
        Thread indicadores = new Thread(new ActualizadorIndicadores(gestor, indicador), "ActualizadorIndicadores");
        supervisor.start();
        indicadores.start();
        new Thread(() -> {
            boolean interrumpido = false;
            while (supervisor.isAlive() || indicadores.isAlive()) {
                try {
                    supervisor.join();
                    indicadores.join();
                } catch (InterruptedException ex) {
                    interrumpido = true;
                    supervisor.interrupt();
                    indicadores.interrupt();
                }
            }
            String texto = ActualizadorIndicadores.textoIndicadores(gestor.obtenerIndicadores());
            SwingUtilities.invokeLater(() -> {
                try {
                    indicador.setText(texto);
                    actualizarVista.run();
                } finally {
                    terminar();
                }
            });
            if (interrumpido) Thread.currentThread().interrupt();
        }, "Coordinador").start();
        return true;
    }
}
