/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author simon
 */
import java.io.IOException;
import java.util.ArrayList;
import javafx.stage.Stage;
import models.MenuItem;
import models.Order;
import services.OrderService;
import models.Table;
import services.InvoiceService;
import services.MenuService;
import views.MenuView;

import java.util.List;
import models.Menu;

/**
 * Controlador que administra la vista del menú (editor y selección para agregar a mesa).
 */
public class MenuController extends ControllerComponent {
    private final MenuService menuService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;

    public MenuController(MenuService menuService, OrderService orderService, InvoiceService invoiceService) {
        this.menuService = menuService;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
    }

public List<models.MenuItem> loadMenu() {
    try {
        models.Menu menu = menuService.loadMenu();
        return menu.getItems();
    } catch (IOException e) {
        e.printStackTrace(); // o maneja el error visualmente
        return new ArrayList<>();
    }
}

    public void saveMenu(List<MenuItem> items) throws IOException {
    Menu menu = new Menu();
    menu.setItems(items);
    menuService.saveMenu(menu);
}

    /**
     * Abre la vista de menú en modo editor (sin mesa).
     */
    public void openMenuEditor(Stage owner) {
        List<MenuItem> menu = loadMenu();
        MenuView view = new MenuView(owner, menu, this, null); // null = no mesa
        view.showAndWait();
    }

    /**
     * Abre la vista de menú en modo selección para una mesa específica.
     */
    public void openMenuForTable(Stage owner, Table table) {
        List<MenuItem> menu = loadMenu();
        MenuView view = new MenuView(owner, menu, this, table);
        view.showAndWait();
    }

    /**
     * Lógica para agregar items seleccionados a la mesa (desde MenuView).
     */
    public void addItemsToTable(Table table, List<MenuItem> items) {
        for (MenuItem m : items) {
            orderService.placeOrder(table, m.getName(), m.getPrice());
        }
        // actualiza la factura provisional
        Order o = orderService.getOrder(table.getNumber());
        if (o != null) invoiceService.updateInvoice(table, o);
    }

    /**
     * Pagar la factura: genera comprobante y limpia la orden y la mesa.
     * Devuelve la ruta del comprobante.
     */
    public String payTable(Table table) {
        Order o = orderService.getOrder(table.getNumber());
        if (o == null) return null;
        String receipt = invoiceService.payAndGenerateReceipt(table, o);
        // limpiar la orden y el estado de la mesa
        o.getItems().clear();
        o.getPrices().clear();
        orderService.getAllOrders().remove(table.getNumber());
        table.setOccupied(false);
        table.resetConsumption();
        return receipt;
    }
}