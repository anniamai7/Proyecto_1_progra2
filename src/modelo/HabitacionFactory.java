/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author UTN
 */
public class HabitacionFactory {
    public static habitacion crearHabitacion(int numero, String tipo){
        switch(tipo.trim().toLowerCase()){
            case "Individual":
                return new HabitacionIndividual(numero);
            case "Doble":
                return new HabitacionDoble(numero);
            case "Familiar":
                return new HabitacionFamiliar(numero);
            default:
                throw new IllegalArgumentException("Tipo de habitación no reconocido: " + tipo);
        }
    }
}
