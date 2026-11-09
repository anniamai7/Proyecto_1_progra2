/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author UTN
 */
public abstract class habitacion {
    private int numero;

    public int getNumero() {
        return numero;
    }

    public habitacion(int numero) {
        this.numero = numero;
    }
    
    public abstract int getCapacidad();
    public abstract long getTarifaPorNoche();
    public abstract String getTipo();
    
}
