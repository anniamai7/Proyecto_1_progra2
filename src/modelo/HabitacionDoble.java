/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author UTN
 */
public class HabitacionDoble extends habitacion {

    public HabitacionDoble(int numero) {
        super(numero);
    }
    
    @Override
    public int getCapacidad(){
        return 2;
    }
    
    @Override
    public long getTarifaPorNoche(){
        return 40000;
    }
    @Override
    public String getTipo(){
        return "Doble";
    }
}
