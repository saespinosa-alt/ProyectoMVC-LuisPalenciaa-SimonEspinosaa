/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

/**
 *
 * @author simon
 */

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.OrderService;
import models.Table;

/**
 * Representa visualmente una mesa en la vista.
 * Ahora la vista acepta un ViewCommand que se ejecuta cuando se presiona el botón.
 * Y hace que la vista del restaurante no quede muy cargada al dividir funciones
 */
public class TableViewComponent extends ViewComponent {
    private final Table table;
    private final Button button;
    private final Text status;
    private final Text consumption;
    private final OrderService service;
    private ViewCommand command; // nuevo

    public TableViewComponent(Table table, OrderService service) {
        this.table = table;
        this.button = new Button("Mesa " + table.getNumber());
        this.status = new Text(table.isOccupied() ? "Ocupada" : "Libre");
        this.consumption = new Text("Consumo: $" + String.format("%.2f", table.getTotalConsumption()));
        this.service = service;
        VBox card = new VBox(6, button, status, consumption);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("table-card");

        getChildren().add(card);
        updateStyle();
        status.getStyleClass().add("mesa-texto");
        consumption.getStyleClass().add("mesa-texto");
        render();

        // por defecto: no hace nada hasta que se asigne un command
        //Todos los botones utilizados en la vista requiere un comando provocado por el controlador llamando el setter.
        button.setOnAction(e -> {
            if (command != null) command.execute();
        });
    }

    @Override
    public void render() {
        status.setText(table.isOccupied() ? "Ocupada" : "Libre");
        double total = service.computeTotal(table);
        consumption.setText("Consumo: $" + String.format("%.2f", total));
        updateStyle();
    }

    private void updateStyle() {
        // Aplica clase según estado
        if (table.isOccupied()) {
            getStyleClass().removeAll("free");
            if (!getStyleClass().contains("occupied")) getStyleClass().add("occupied");
        } else {
            getStyleClass().removeAll("occupied");
            if (!getStyleClass().contains("free")) getStyleClass().add("free");
        }
    }

    public Button getButton() { return button; }
    public Table getTable() { return table; }

    // nuevo: setter para asignar el comando desde el controlador
    public void setViewCommand(ViewCommand cmd) {
        this.command = cmd;
    }
}

