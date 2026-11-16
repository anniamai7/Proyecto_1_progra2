/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

/**
 *
 * @author UTN
 */
public abstract class habitacion {
    private int numero;
    protected boolean disponible = true;

    public int getNumero() {
        return numero;
    }

    public habitacion(int numero) {
        this.numero = numero;
    }
    public boolean isDisponible(LocalDate fechaConsulta) {
        return this.disponible;
    }
    
    public void setDisponible(boolean disponible) {
    this.disponible = disponible;
    }
    
    public abstract int getCapacidad();
    public abstract long getTarifaPorNoche();
    public abstract String getTipo();
    
}
