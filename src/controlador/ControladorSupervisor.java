/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.*;
import javax.swing.JLabel;
/**
 *
 * @author Dario R
 */
public class ControladorSupervisor {
    private final GestorHotel gestor = GestorHotel.getInstancia();
    
    public void ejcutar(JLabel actualizarVista){
        
    Thread supervisor = new Thread( new SupervisorReservas(gestor));
    
    Thread indicadores = new Thread (() -> {
        try {
        supervisor.join();
        new ActualizadorIndicadores(gestor, actualizarVista).run();
    } catch (InterruptedException ex){
        Thread.currentThread().interrupt();
    }
    });
    supervisor.start();
    indicadores.start();
    }
}

