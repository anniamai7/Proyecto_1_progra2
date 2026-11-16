/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.GestorHotel;
import modelo.huesped;

/**
 *
 * @author UTN
 */
public class huespedControlador {
    public static void registrarHuesped(String id, String nombre, String telefono, String correo, JTable tabla) {
        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "Formato de correo electrónico inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        huesped nuevoHuesped = new huesped(id, nombre, telefono, correo);
        boolean registrado = GestorHotel.getInstancia().agregarHuesped(nuevoHuesped);

        if (registrado) {
            JOptionPane.showMessageDialog(null, "Huésped registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTablaHuespedes(tabla);
        } else {
            JOptionPane.showMessageDialog(null, "La identificación ya se encuentra registrada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void actualizarTablaHuespedes(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        for (huesped h : GestorHotel.getInstancia().getHuespedes()) {
            modelo.addRow(new Object[]{h.getIdentificacion(), h.getNombreCompleto(), h.getTelefono(), h.getCorreo()});
        }
    }
}
