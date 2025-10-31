/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author simon
 */
import javafx.stage.Stage;
import models.Order;
import services.OrderService;
import models.Table;
import views.OrderDialog;

/**
 * Controller que se encarga de todo lo relativo a pedidos, abre dialogos.
 */
public class OrderController extends ControllerComponent {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Abre el diálogo para la mesa (se asegura que exista el Order).
     */
    public void openOrder(Stage owner, Table table) {
        Order order = service.getOrder(table.getNumber());
        if (order == null) {
            order = new Order(table);
            service.getAllOrders().put(table.getNumber(), order);
        }
        OrderDialog dialog = new OrderDialog(owner, table, order);
        dialog.showAndWait();
    }
}
