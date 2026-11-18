/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.GestorHotel;
import modelo.Reserva;

/**
 *
 * @author Dario R
 */
public class CheckControlador {
    private final GestorHotel gestor = GestorHotel.getInstancia();
    
    public Reserva buscar(String codigo){
        if (codigo == null || codigo.trim().isEmpty()){
          throw new IllegalArgumentException("Ingrese el código de reserva");   
        }
        Reserva reserva = gestor.buscarReserva(codigo.trim());
        if (reserva == null ){
             throw new IllegalArgumentException("Reserva no registrada");
        }
        return reserva;
    }
    public void hacerCheckIn(String codigo){
        gestor.realizarCheckIn(codigo.trim());
    }
    public void hacerCheckOut(String codigo){
        gestor.realizarCheckOut(codigo.trim());
    }
}
