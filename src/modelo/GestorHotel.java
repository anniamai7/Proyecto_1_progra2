/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDate;
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
    
    //-----------PARTE DE DARIO----------------------//
    private List<Reserva> reservas;
    
    private GestorHotel(){
        this.huespedes = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
        this.usuarioActivo = null;
        
        //--------PARTE DE DARIO------------------//
        this.reservas = new ArrayList<>();
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
    
    public int contarHabitacionesDisponibles(LocalDate fecha) {
        return contarRecursivo(habitaciones, fecha, 0);
    }
    
    private int contarRecursivo(List<habitacion> lista, LocalDate fecha, int index) {
    if (index >= lista.size()) {
        return 0;
    }
    
    int cuenta = 0;
    habitacion h = lista.get(index);
    
    if (h.isDisponible(fecha)) { 
        cuenta = 1;
    }
    
    return cuenta + contarRecursivo(lista, fecha, index + 1);
}
    //--------------------------PARTE DE DARIO------------------------------//
    public synchronized List<Reserva> getReservas(){
        return new ArrayList<>(reservas);
        
    }
    public synchronized Reserva buscarReserva(String codigo){
        for (Reserva r : reservas ){
            if (r.getCodigo().equalsIgnoreCase(codigo)){
                return r;
            }
        }
        return null;
    }
    public synchronized boolean haySuperposicion( habitacion habitacion,
            LocalDate entrada, LocalDate salida){
        for (Reserva r : reservas ){
            if (r.getHabitacion().getNumero() != habitacion.getNumero()){
                continue;
            }
            if (r.getEstado()==EstadoReserva.Vencida || 
                    r.getEstado()== EstadoReserva.Finalizada){
                continue;
            }
            boolean seCruzan = entrada.isBefore(r.getFechaDeSalida()) &&
                    salida.isAfter(r.getFechaDeEntrada());
            if (seCruzan) {
                return true;
            }
        }
        return false;
    }
    //--------------------------PARTE DE DARIO------------------------------//
    public synchronized void RegistrarReserva(Reserva r){
        if (buscarReserva(r.getCodigo())!= null){
            throw new IllegalArgumentException("Ya existe una reserve con este codigo");
        }
        if(r.getFechaDeEntrada()== null || r.getFechaDeSalida() == null ||
                !r.getFechaDeSalida().isAfter(r.getFechaDeEntrada())){
            throw new IllegalArgumentException("Las fechas selecionas no son validas");
        } 
        if (r.getPersonas()<1 || r.getPersonas() > r.getHabitacion().getCapacidad() ){
            throw new IllegalArgumentException("Capacidad de la habitacion excedida");
        }
        if (haySuperposicion(r.getHabitacion(),r.getFechaDeEntrada(),r.getFechaDeSalida())){
            throw new IllegalArgumentException("Lo sentimos :( ,la habitacion esta reservada" + "en esas fechas.");
        }
        reservas.add(r);
    }
        public synchronized void realizarCheckIn(String codigo){
        Reserva r = buscarReserva(codigo);
        if (r == null || r.getEstado() != EstadoReserva.Confirmada){
            throw new IllegalArgumentException("No se puede realizar el check-in.");
        }
        r.setEstado(EstadoReserva.Hospedadoo);
    }
      public synchronized void realizarCheckOut(String codigo){
        Reserva r = buscarReserva(codigo);
        if (r == null || r.getEstado() != EstadoReserva.Hospedadoo){
            throw new IllegalArgumentException("No se puede realizar el check-out.");
        }
        r.setEstado(EstadoReserva.Finalizada);
    }
      
}
