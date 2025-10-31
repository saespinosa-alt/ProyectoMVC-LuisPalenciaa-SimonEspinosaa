/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author simon
 */
import java.util.function.Consumer;

import services.OrderService;
import models.SimpleTableFactory;
import models.Table;
import views.RestaurantView;
import views.TableViewComponent;

/**
 * Controller encargado de la creación y gestión de mesas y sus componentes en la vista.
 * Notifica vía callback cuando una mesa es seleccionada (click).
 */
public class TableController extends ControllerComponent {
    private final RestaurantView view;
    private final OrderService service;
    private final SimpleTableFactory factory;
    private Consumer<Table> onTableSelected; // callback para decir "abrir pedido/menu" al root
    private Table selectedTable;

    public TableController(RestaurantView view, OrderService service, SimpleTableFactory factory) {
        this.view = view;
        this.service = service;
        this.factory = factory;
    }

    public void setOnTableSelected(Consumer<Table> callback) {
        this.onTableSelected = callback;
    }
public Table getSelectedTable() {
    return selectedTable;
}

    public void initTables(int count) {
        view.clear();
        for (int i = 1; i <= count; i++) {
            Table table = factory.createTable(i);
            TableViewComponent comp = new TableViewComponent(table, service);

            // asignamos el command en la vista para ejecutar la acción (Command en la vista)
            comp.setViewCommand(() -> {
                if (onTableSelected != null) onTableSelected.accept(table);
            });

            view.addComponent(comp);
        }
        view.render();
        
    }

    @Override
    public void init() {
        // nada por defecto
    }
}
