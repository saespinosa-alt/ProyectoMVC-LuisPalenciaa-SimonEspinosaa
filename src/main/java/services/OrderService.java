/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author simon
 */

import java.util.HashMap;
import java.util.Map;
import models.Order;
import models.Table;

/**
 * Servicio que gestiona pedidos de mesas.
 */
//Maneja y guarda las ordenes pedidas en cada mesa. 
public class OrderService {
   private final Map<Integer, Order> orders = new HashMap<>();

    public void placeOrder(Table table, String item, double price) {
        Order order = orders.computeIfAbsent(table.getNumber(), n -> new Order(table));
        order.addItem(item, price);
        table.setOccupied(true);
        table.addConsumption(price);
    }

    public Order getOrder(int tableNumber) { return orders.get(tableNumber); }
    public Map<Integer, Order> getAllOrders() { return orders; }
    public double computeTotal(Table table) {
    Order order = orders.get(table.getNumber());
    if (order == null) return 0.0;
    return order.getPrices().stream().mapToDouble(Double::doubleValue).sum();
}
}



