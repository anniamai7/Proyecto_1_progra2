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
public class Reserva {
    private String codigo;
    private huesped huesped;
    private habitacion habitacion;
    private int personas;
    private LocalDate fechaDeEntrada;
    private LocalDate fechaDeSalida;
    private long noches;
    private long importe;
    private EstadoReserva estado;
    private String recepcion;

    public Reserva(String codigo, huesped huesped, habitacion habitacion, 
            int personas, LocalDate fechaDeEntrada, LocalDate fechaDeSalida,
            long noches, long importe, EstadoReserva estado, String recepcion) {
        
        this.codigo = codigo;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.personas = personas;
        this.fechaDeEntrada = fechaDeEntrada;
        this.fechaDeSalida = fechaDeSalida;
        this.noches = noches;
        this.importe = importe;
        this.estado = estado;
        this.recepcion = recepcion;
        
        
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(huesped huesped) {
        this.huesped = huesped;
    }

    public habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public int getPersonas() {
        return personas;
    }

    public void setPersonas(int personas) {
        this.personas = personas;
    }

    public LocalDate getFechaDeEntrada() {
        return fechaDeEntrada;
    }

    public void setFechaDeEntrada(LocalDate fechaDeEntrada) {
        this.fechaDeEntrada = fechaDeEntrada;
    }

    public LocalDate getFechaDeSalida() {
        return fechaDeSalida;
    }

    public void setFechaDeSalida(LocalDate fechaDeSalida) {
        this.fechaDeSalida = fechaDeSalida;
    }

    public long getNoches() {
        return noches;
    }

    public void setNoches(long noches) {
        this.noches = noches;
    }

    public long getImporte() {
        return importe;
    }

    public void setImporte(long importe) {
        this.importe = importe;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public String getRecepcion() {
        return recepcion;
    }

    public void setRecepcion(String recepcion) {
        this.recepcion = recepcion;
    }
    
    
    
    
    
    
}
