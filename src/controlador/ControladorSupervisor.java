/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.ActualizadorIndicadores;
import modelo.GestorHotel;
import modelo.SupervisorReservas;
import javax.swing.JLabel;
/**
 *
 * @author Dario R
 */
public class ControladorSupervisor {
    private final GestorHotel gestor = GestorHotel.getInstancia();
    
    public void ejcutar(JLabel indicador){
        
    Thread supervisor = new Thread( new SupervisorReservas(gestor),"supervisor-reservas");
    
    Thread indicadores = new Thread (() -> {
        try {
        supervisor.join();
        new ActualizadorIndicadores(gestor, indicador).run();
    } catch (InterruptedException ex){
        Thread.currentThread().interrupt();
    }
    },"actualizador-indicaciones");
    supervisor.start();
    indicadores.start();
    }
}

