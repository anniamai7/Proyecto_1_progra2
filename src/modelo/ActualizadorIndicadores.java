/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Dario R
 */
public class ActualizadorIndicadores implements Runnable {
    private final GestorHotel gestor;
    private final JLabel indicador;
    
    public ActualizadorIndicadores( GestorHotel gestor, JLabel indicador) {
        this.gestor = gestor;
        this.indicador = indicador;
    }
    public void run(){
        List<Reserva> reservas = gestor.getReservas();
        int total = reservas.size();
        int hospedados = 0;
        for (Reserva reserva:reservas){
            if (reserva.getEstado()== EstadoReserva.Hospedado){
                hospedados++;
            }
        }
        long ingresos = gestor.calcularIngresosRecursivos(0);
        String texto = "Reservas: " + total + " | Hospedados: " + hospedados
                + " | Ingresos finalizados: "+ ingresos;
        SwingUtilities.invokeLater(() -> {
            indicador.setText(texto);
        });
    }   
}
