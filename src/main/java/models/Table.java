/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;

/**
 *
 * @author simon
 */

/**
 * Representa una mesa en el restaurante.
 */
public class Table {
    private final int number; //numero
    private boolean occupied; //ocupado verdadero /falso
    private double totalConsumption; // total consumido
      private List<MenuItem> order;

    public Table(int number) {
        this.number = number;
        this.occupied = false;
        this.totalConsumption = 0.0;
    }

    public int getNumber() { return number; }
    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
        public List<MenuItem> getOrder() {
        return order;
    }

    public void setOrder(List<MenuItem> order) {
        this.order = order;
    }
    

    public double getTotalConsumption() { return totalConsumption; }
    public void addConsumption(double amount) { this.totalConsumption += amount; }

    /**
     * Resetea el consumo acumulado (usado al pagar).
     */
    public void resetConsumption() { this.totalConsumption = 0.0; }

    @Override
    public String toString() {
        return "Mesa #" + number + (occupied ? " (Ocupada)" : " (Libre)") +
               " - Consumo: $" + String.format("%.2f", totalConsumption);
    }
}

