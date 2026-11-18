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
    
    public Reserva registrar(String codigo, huesped cliente, habitacion cuarto, 
            int personas, LocalDate Entrada, LocalDate Salida){
    if (codigo == null || codigo.trim().isEmpty()){
        throw new IllegalArgumentException("Ingrese un codigo de reserva");
    }
    if (cliente == null || cuarto == null){
        throw new IllegalArgumentException("Seleccione huesped y habitacion");
    }
    if (Entrada == null || Salida == null || !Salida.isAfter(Entrada)){
        throw new IllegalArgumentException("La salida debe de posterior a la entrada");
    }
    if (Entrada.isBefore(LocalDate.now())){
        throw new IllegalArgumentException("No se permite fechas anteriores a la de hoy");
    }
    if (personas <= 0){
        throw new IllegalArgumentException("La cantidad de personas debe de ser mayor a cero");
    }
    long noches = ChronoUnit.DAYS.between(Entrada, Salida);
    
    long importe = Math.multiplyExact(noches, cuarto.getTarifaPorNoche());
    
    Reserva reserva = new Reserva(codigo.trim(), cliente, cuarto, personas, 
            Entrada, Salida, noches, importe, EstadoReserva.Confirmada, 
            gestor.getUsuarioActivo());
    
    gestor.RegistrarReserva(reserva);
    return reserva;
  }
}
