/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDate;
/**
 *
 * @author Dario R
 */
public class SupervisorReservas implements Runnable {
    private final GestorHotel gestor;
 
    public SupervisorReservas(GestorHotel gestor) {
        this.gestor = gestor;
    }
    @Override
    public void run(){
        gestor.actualizarVencimientos(LocalDate.now());
    }
}
