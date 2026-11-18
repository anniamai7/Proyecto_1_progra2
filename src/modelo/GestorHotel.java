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
    
    public synchronized List<huesped> getHuespedes(){
        return new ArrayList<>(huespedes);
    }
    
    public synchronized List<habitacion> gethabitaciones(){
        return new ArrayList<>(habitaciones);
    }
    
    public synchronized String getUsuarioActivo(){
        return usuarioActivo;
    }
    
    public synchronized void setUsuarioActivo(String usuarioActivo){
        this.usuarioActivo = usuarioActivo;
    }
    
    public synchronized boolean agregarHuesped(huesped nuevo){
        if (nuevo == null || nuevo.getIdentificacion() == null || nuevo.getIdentificacion().trim().isEmpty()
                || nuevo.getNombreCompleto() == null || nuevo.getNombreCompleto().trim().isEmpty()
                || nuevo.getTelefono() == null || nuevo.getTelefono().trim().isEmpty()
                || nuevo.getCorreo() == null || nuevo.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos del huésped son obligatorios.");
        }
        String correo = nuevo.getCorreo().trim();
        int arroba = correo.indexOf('@');
        if (arroba <= 0 || arroba != correo.lastIndexOf('@') || arroba == correo.length() - 1
                || correo.indexOf('.', arroba + 1) <= arroba + 1 || correo.endsWith(".") || correo.contains(" ")) {
            throw new IllegalArgumentException("El correo no tiene un formato válido.");
        }
        for(huesped h : huespedes){
            if (h.getIdentificacion().trim().equalsIgnoreCase(nuevo.getIdentificacion().trim())){
                return false;
            }
        }
        huespedes.add(nuevo);
        return true;
    }
    
    public synchronized boolean agregarHabitacion(habitacion  nueva){
        if (nueva == null) throw new IllegalArgumentException("Seleccione una habitación.");
        for (habitacion h : habitaciones){
            if (h.getNumero() == nueva.getNumero()){
                return false;
            }
        }
        habitaciones.add(nueva);
        return true;
    }
    
    public synchronized int contarHabitacionesDisponibles(LocalDate fecha) {
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
        if (!r.getFechaDeEntrada().equals(LocalDate.now())) {
            throw new IllegalArgumentException("El check-in solo se permite en la fecha de entrada.");
        }
        r.setEstado(EstadoReserva.Hospedado);
    }
      public synchronized void realizarCheckOut(String codigo){
        Reserva r = buscarReserva(codigo);
         if (r == null) {
        throw new IllegalArgumentException("La reserva no existe");
    }

    if (r.getEstado() != EstadoReserva.Hospedado) {
        throw new IllegalArgumentException("Solo puede realizar check-out "
                + "una reserva Hospedado");
    }
    r.setFechaSalidaReal(LocalDate.now());
    r.setEstado(EstadoReserva.Finalizada);
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
      public synchronized long calcularIngresosRecursivos(int indice){
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
    public synchronized List<habitacion> obtenerDisponibles(LocalDate fecha) {
        if (fecha == null) throw new IllegalArgumentException("Seleccione una fecha válida");
        List<habitacion> disponibles = new ArrayList<>();
        for (habitacion h : habitaciones) {
            if (!haySuperposicion(h, fecha, fecha.plusDays(1))) disponibles.add(h);
        }
        return disponibles;
    }

    public synchronized String[] obtenerIndicadores() {
        int ocupadas = 0, confirmadas = 0, vencidas = 0;
        for (habitacion h : habitaciones) {
            for (Reserva r : reservas) {
                if (r.getHabitacion().getNumero() == h.getNumero()
                        && r.getEstado() == EstadoReserva.Hospedado) {
                    ocupadas++;
                    break;
                }
            }
        }
        for (Reserva r : reservas) {
            if (r.getEstado() == EstadoReserva.Confirmada) confirmadas++;
            if (r.getEstado() == EstadoReserva.Vencida) vencidas++;
        }
        return new String[]{String.valueOf(habitaciones.size()), String.valueOf(ocupadas),
            String.valueOf(habitaciones.size() - ocupadas), String.valueOf(confirmadas),
            String.valueOf(vencidas), String.valueOf(calcularIngresosRecursivos(0))};
    }

}