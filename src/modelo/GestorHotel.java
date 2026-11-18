/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author UTN
 */
public class GestorHotel {
    private static GestorHotel instancia;
    private List<habitacion> habitaciones;
    private List<huesped> huespedes;
    private String usuarioActivo;
    private int siguienteReserva =1;
    
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
        if (fecha == null ){
            throw new IllegalArgumentException("Seleccione una fecha válida");
        }
        return contarRecursivo(habitaciones, fecha, 0);
    }
    
    private int contarRecursivo(List<habitacion> lista, LocalDate fecha, int indice) {
    if (indice >= lista.size()) {
        return 0;
    }
    
    habitacion h = lista.get(indice);
    int cuenta=0;
     boolean ocupada = haySuperposicion(h,fecha,fecha.plusDays(1));
    if (!ocupada) {
        cuenta = 1;
    }
    
    return cuenta + contarRecursivo(lista, fecha, indice + 1);
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
    public synchronized Reserva registrarReserva(huesped cliente,habitacion cuarto,
            int personas,LocalDate entrada,LocalDate salida) {
    if (cliente == null || cuarto == null) {
        throw new IllegalArgumentException("Seleccione huésped y habitación");
    }
    if (!huespedes.contains(cliente) ||!habitaciones.contains(cuarto)) {
        throw new IllegalArgumentException("El huésped o la habitación no están registrados");
    }
    if (usuarioActivo == null) {
        throw new IllegalArgumentException("No existe una sesión activa");
    }
    if (entrada == null || salida == null) {
        throw new IllegalArgumentException("Seleccione ambas fechas");
    }
    if (entrada.isBefore(LocalDate.now())) {
        throw new IllegalArgumentException("La entrada no puede ser anterior a hoy");
    }
    if (!salida.isAfter(entrada)) {
        throw new IllegalArgumentException("La salida debe ser posterior a la entrada");
    }
    if (personas <= 0 || personas > cuarto.getCapacidad()) {
        throw new IllegalArgumentException("Cantidad de personas no permitida");
    }
    if (haySuperposicion(cuarto, entrada, salida)) {
        throw new IllegalArgumentException("La habitación está reservada en ese período");
    }
    long noches = ChronoUnit.DAYS.between(entrada, salida);

    long importe = Math.multiplyExact(noches, cuarto.getTarifaPorNoche());

    String codigo = String.format("R%03d", siguienteReserva);

    Reserva nueva = new Reserva(codigo,cliente,cuarto,personas,entrada,salida,
            noches,importe,EstadoReserva.Confirmada,usuarioActivo);

    reservas.add(nueva);
    siguienteReserva++;

    return nueva;
}
        public synchronized void realizarCheckIn(String codigo){
        Reserva r = buscarReserva(codigo);
        if (r == null || r.getEstado() != EstadoReserva.Confirmada){
            throw new IllegalArgumentException("No se puede realizar el check-in.");
        }
        r.setEstado(EstadoReserva.Hospedado);
    }
      public synchronized void realizarCheckOut(String codigo){
        Reserva r = buscarReserva(codigo);
        if (r == null) {
        throw new IllegalArgumentException("La reserva no existe");
    }

    if (r.getEstado() != EstadoReserva.Confirmada) {
        throw new IllegalArgumentException("La reserva no está confirmada");
    }

    if (!r.getFechaDeEntrada().equals(LocalDate.now())) {
        throw new IllegalArgumentException("El check-in solo puede realizarse "
                + "en la fecha de entrada programada");
    }
    r.setEstado(EstadoReserva.Hospedado);
   }
      //-----------------------PARTE DE DARIO--------------------------------//
      public synchronized List<Reserva> simularVencimientos(
      LocalDate fechaReferencia ){
          List<Reserva> resultado = new ArrayList<>();
          for (Reserva r : reservas){
              if (r.getEstado() == EstadoReserva.Confirmada && r.getFechaDeEntrada()
                      .isBefore(fechaReferencia)){
                  resultado.add(r);
              }
          }
          return resultado;
      }
      public long calcularIngresosRecursivos(int indice){
       List<Reserva> copia = getReservas();
       return sumarIngresos(copia, indice);
      }
      public long sumarIngresos(List<Reserva> lista, int indice){
          if (indice >= lista.size()){
              return 0;
          }
          Reserva r = lista.get(indice);
          long monto =0;
          if(r.getEstado()== EstadoReserva.Confirmada){
              monto = r.getImporte();
          }
          return monto + sumarIngresos(lista,indice +1 );
    }
      //-----------------------PARTE DE DARIO--------------------------------//
      public synchronized void actualizarVencimientos( LocalDate fechaActual){
          for(Reserva r : reservas){
              if (r.getEstado() == EstadoReserva.Confirmada &&
                      r.getFechaDeEntrada().isBefore(fechaActual)){
              r.setEstado(EstadoReserva.Vencida);
          }
        }
    }
}