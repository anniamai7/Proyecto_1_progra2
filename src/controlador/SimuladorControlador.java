/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.time.LocalDate;
import java.util.List;
import modelo.GestorHotel;
import modelo.Reserva;

/**
 *
 * @author Dario R
 */
public class SimuladorControlador {
    public List<Reserva> simular(LocalDate fecha){
        if (fecha == null){
             throw new IllegalArgumentException("Selecione una fecha");
        }
        return GestorHotel.getInstancia().simularVencimientos(fecha);
    }
}
