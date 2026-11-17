/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.GestorHotel;
import modelo.HabitacionFactory;
import modelo.habitacion;

/**
 *
 * @author UTN
 */
public class HabitacionControlador {
    public static void registrarHabitacion(String numeroStr, String tipoSeleccionado, JTable tabla) {
        if (numeroStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El número de habitación es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int numero = Integer.parseInt(numeroStr);
            habitacion nuevaHabitacion = HabitacionFactory.crearHabitacion(numero, tipoSeleccionado);
            
            boolean registrada = GestorHotel.getInstancia().agregarHabitacion(nuevaHabitacion);
            if (registrada) {
                JOptionPane.showMessageDialog(null, "Habitación registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTablaHabitaciones(tabla);
            } else {
                JOptionPane.showMessageDialog(null, "El número de habitación ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El número de habitación debe ser un valor numérico.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void actualizarTablaHabitaciones(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        for (habitacion h : GestorHotel.getInstancia().gethabitaciones()) {
            modelo.addRow(new Object[]{h.getNumero(), h.getTipo(), h.getCapacidad(), "₡" + h.getTarifaPorNoche()});
        }
    }
}
