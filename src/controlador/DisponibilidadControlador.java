/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import modelo.GestorHotel;

/**
 *
 * @author UTN
 */
public class DisponibilidadControlador {
    public static void consultarDisponibilidad(String fechaTexto, JLabel lblResultadoConteo) {
        if (fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una fecha para consultar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            LocalDate fechaConsulta = LocalDate.parse(fechaTexto);
            int habitacionesDisponibles = GestorHotel.getInstancia().contarHabitacionesDisponibles(fechaConsulta);
            
            lblResultadoConteo.setText("Habitaciones disponibles: " + habitacionesDisponibles);
            
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Utilice el formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
