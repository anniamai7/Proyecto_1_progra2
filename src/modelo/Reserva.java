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
    private LocalDate fechaSalidaReal;

        public Reserva(String codigo, huesped huesped, habitacion habitacion, int personas, LocalDate fechaDeEntrada, LocalDate fechaDeSalida, long noches, long importe, EstadoReserva estado, String recepcion) {
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
            this.fechaSalidaReal = null;
        }

    public LocalDate getFechaSalidaReal() {
        return fechaSalidaReal;
    }

    void setFechaSalidaReal(LocalDate fechaSalidaReal) {
        this.fechaSalidaReal = fechaSalidaReal;
    }
        
        
    public String getCodigo() {
        return codigo;
    }
    
    public huesped getHuesped() {
        return huesped;
    }


    public habitacion getHabitacion() {
        return habitacion;
    }


    public int getPersonas() {
        return personas;
    }


    public LocalDate getFechaDeEntrada() {
        return fechaDeEntrada;
    }


    public LocalDate getFechaDeSalida() {
        return fechaDeSalida;
    }


    public long getNoches() {
        return noches;
    }


    public long getImporte() {
        return importe;
    }


    public EstadoReserva getEstado() {
        return estado;
    }

    void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public String getRecepcion() {
        return recepcion;
    }

    
    
    
    
    
    
}
