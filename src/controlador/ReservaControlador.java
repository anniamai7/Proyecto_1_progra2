/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
/**
 *
 * @author Dario R
 */
public class ReservaControlador {
    private final GestorHotel gestor = GestorHotel.getInstancia();
    
     public Reserva registrar(huesped cliente,habitacion cuarto,int personas,
             LocalDate entrada,LocalDate salida) {
        return gestor.registrarReserva(cliente,cuarto, personas,entrada,salida);
    }
}