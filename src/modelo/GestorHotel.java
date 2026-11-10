/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author UTN
 */
public class GestorHotel {
    private static GestorHotel instancia;
    private List<habitacion> habitaciones;
    private List<huesped> huespedes;
    private String usuarioActivo;
    
    private GestorHotel(){
        this.huespedes = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
        this.usuarioActivo = null;
    }
    
    public static synchronized GestorHotel getInstancia(){
        if (instancia == null){
            instancia = new GestorHotel();
        }return instancia;
    }
    
    public List<huesped> getHuespedes(){
        return huespedes;
    }
    
    public List<habitacion> gethabitaciones(){
        return habitaciones;
    }
    
    public String getUsuarioActivo(){
        return usuarioActivo;
    }
    
    public void setUsuarioActivo(String usuarioActivo){
        this.usuarioActivo = usuarioActivo;
    }
    
    public boolean agregarHuesped(huesped nuevo){
        for(huesped h : huespedes){
            if (h.getIdentificacion().equalsIgnoreCase(nuevo.getIdentificacion())){
                return false;
            }
        }
        huespedes.add(nuevo);
        return true;
    }
    
    public boolean agregarHabitacion(habitacion  nueva){
        for (habitacion h : habitaciones){
            if (h.getNumero() == nueva.getNumero()){
                return false;
            }
        }
        habitaciones.add(nueva);
        return true;
    }
}
