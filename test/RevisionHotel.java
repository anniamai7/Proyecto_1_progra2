import modelo.*;
import controlador.ControladorSupervisor;
import javax.swing.*;
import java.time.LocalDate;

public class RevisionHotel {
 static void verificar(boolean b, String m) { if (!b) throw new AssertionError(m); System.out.println("OK " + m); }
 static void falla(Runnable r, String m) { try { r.run(); throw new AssertionError(m); } catch (IllegalArgumentException ex) { System.out.println("OK " + m); } }
 public static void main(String[] args) throws Exception {
  GestorHotel g=GestorHotel.getInstancia(); LocalDate hoy=LocalDate.now();
  verificar(g.contarHabitacionesDisponibles(hoy)==0 && g.calcularIngresosRecursivos(0)==0,"recursividad vacía");
  huesped h=new huesped("001","Ana","00012345","ana@hotel.cr"); verificar(g.agregarHuesped(h),"agregar huésped");
  verificar(!g.agregarHuesped(new huesped("001","Ana","1","ana@hotel.cr")),"duplicado");
  falla(()->g.agregarHuesped(new huesped("2"," ","1","a@a.cr")),"campos obligatorios");
  habitacion individual=HabitacionFactory.crearHabitacion(101,"Individual"), doble=HabitacionFactory.crearHabitacion(102,"Doble"), familiar=HabitacionFactory.crearHabitacion(103,"Familiar");
  g.agregarHabitacion(individual);g.agregarHabitacion(doble);g.agregarHabitacion(familiar);
  verificar(individual.getCapacidad()==1 && doble.getTarifaPorNoche()==40000 && familiar.getCapacidad()==4,"factory y polimorfismo");
  g.gethabitaciones().clear(); verificar(g.gethabitaciones().size()==3,"copia de lista");
  g.setUsuarioActivo("recepcion1"); Reserva r=g.registrarReserva(h,doble,2,hoy.plusDays(1),hoy.plusDays(3));
  verificar(r.getNoches()==2 && r.getImporte()==80000 && g.calcularIngresosRecursivos(0)==80000,"noches, importe e ingresos recursivos");
  falla(()->g.registrarReserva(h,doble,1,hoy.plusDays(2),hoy.plusDays(4)),"rechazar superposición");
  falla(()->g.realizarCheckIn(r.getCodigo()),"rechazar check-in anticipado");
  falla(()->g.registrarReserva(h,individual,2,hoy.plusDays(1),hoy.plusDays(2)),"rechazar capacidad excedida");
  falla(()->g.registrarReserva(h,individual,1,hoy.minusDays(1),hoy),"rechazar fecha pasada");
  verificar(g.contarHabitacionesDisponibles(hoy.plusDays(1))==2 && g.obtenerDisponibles(hoy.plusDays(3)).size()==3,"disponibilidad y fecha de salida");
  Reserva consecutiva=g.registrarReserva(h,doble,1,hoy.plusDays(3),hoy.plusDays(4));
  verificar(consecutiva != null,"aceptar reservas consecutivas");
  verificar(g.simularVencimientos(hoy.plusDays(2)).contains(r) && r.getEstado()==EstadoReserva.Confirmada,"simulación sin cambios");
  Reserva actual=g.registrarReserva(h,individual,1,hoy,hoy.plusDays(1)); g.realizarCheckIn(actual.getCodigo());
  verificar(actual.getEstado()==EstadoReserva.Hospedado,"check-in válido"); g.realizarCheckOut(actual.getCodigo());
  verificar(actual.getEstado()==EstadoReserva.Finalizada && hoy.equals(actual.getFechaSalidaReal()),"check-out y fecha real");
  JTextArea area=new JTextArea(); ControladorSupervisor c=new ControladorSupervisor();
  verificar(c.ejecutar(area,()->{}),"iniciar hilos"); verificar(!c.ejecutar(area,()->{}),"impedir segunda pareja");
  long fin=System.currentTimeMillis()+10000; while(ControladorSupervisor.estaEnEjecucion() && System.currentTimeMillis()<fin) Thread.sleep(100);
  SwingUtilities.invokeAndWait(()->{}); verificar(!ControladorSupervisor.estaEnEjecucion() && area.getText().contains("Ingresos esperados"),"finalización e indicadores");
  System.out.println("PRUEBAS COMPLETADAS");
 }
}
