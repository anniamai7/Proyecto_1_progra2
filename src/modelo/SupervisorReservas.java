package modelo;

import java.time.LocalDate;

public class SupervisorReservas implements Runnable {
    private final GestorHotel gestor;

    public SupervisorReservas(GestorHotel gestor) {
        this.gestor = gestor;
    }

    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();
        System.out.println(nombre + ": inicio");
        try {
            for (int ciclo = 1; ciclo <= 5; ciclo++) {
                gestor.actualizarVencimientos(LocalDate.now());
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
