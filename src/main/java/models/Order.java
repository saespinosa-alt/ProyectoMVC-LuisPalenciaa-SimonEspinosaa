/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author simon
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un pedido asociado a una mesa.
 */
public class Order {
    private final Table table;
    private final List<String> items = new ArrayList<>();
    private final List<Double> prices = new ArrayList<>(); // nuevo

    public Order(Table table) { this.table = table; }
    public Table getTable() { return table; }
    public List<String> getItems() { return items; }
    public List<Double> getPrices() { return prices; }

    public void addItem(String item, double price) {
        items.add(item);
        prices.add(price);
    }

    @Override
    public String toString() {
        return "Pedido de Mesa #" + table.getNumber() + ": " + items;
    }
}

