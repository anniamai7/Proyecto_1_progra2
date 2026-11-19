/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JOptionPane;
import modelo.GestorHotel;
import vista.frmHotel;
import vista.frmlogin;

/**
 *
 * @author UTN
 */
public class LoginControlador {
    public static void validarAcceso(frmlogin vistaLogin, String usuario,String password){
        if((usuario.equals("recepcion1") && password.equals("Hotel123")) ||
                (usuario.equals("recepcion2") && password.equals("Hotel456"))){
        GestorHotel.getInstancia().setUsuarioActivo(usuario);
        GestorHotel.getInstancia().actualizarVencimientos(java.time.LocalDate.now());
        frmHotel frmHotel = new frmHotel();
        frmHotel.setVisible(true);
        vistaLogin.dispose();
    }else{
            vistaLogin.getLblnotificacion_incorrecto().setVisible(true);
        }
    }
    
    public static void cambiarUsuario(frmHotel hotel){
        if (ControladorSupervisor.estaEnEjecucion()) {
            JOptionPane.showMessageDialog(hotel, "Espere a que finalicen los procesos antes de cambiar de usuario.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(
            hotel,
            "¿Desea cerrar la sesión actual para ingresar con otro usuario?",
            "Cambiar Usuario",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            GestorHotel.getInstancia().setUsuarioActivo(null);
            frmlogin login = new frmlogin();
            login.setVisible(true);
            hotel.dispose();
        }
    }
}
