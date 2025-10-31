/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

/**
 *
 * @author simon
 */
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Order;
import models.Table;

import java.util.stream.IntStream;

public class OrderDialog {
    private final Stage stage = new Stage();
    private final Table table;
    private final Order order;

    private final ListView<String> itemsView = new ListView<>();
    private final TextField itemField = new TextField();
    private final TextField priceField = new TextField();
    private final Label totalLabel = new Label();

    public OrderDialog(Stage owner, Table table, Order order) {
        this.table = table;
        this.order = order;

        stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Mesa #" + table.getNumber() + " - Pedido");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));

        // Top: estado de mesa y total
        HBox header = new HBox(15);
        Label stateLabel = new Label(table.isOccupied() ? "Estado: Ocupada" : "Estado: Libre");
        totalLabel.setText("Total: $" + String.format("%.2f", table.getTotalConsumption()));
        header.getChildren().addAll(stateLabel, totalLabel);

        // Center: lista de ítems
        itemsView.setPrefHeight(300);
        refreshList();

        // Right: acciones sobre selección
        VBox rightPanel = new VBox(8);
        Button editBtn = new Button("Editar precio");
        Button removeBtn = new Button("Eliminar ítem");
        editBtn.setOnAction(e -> editSelected());
        removeBtn.setOnAction(e -> removeSelected());
        rightPanel.getChildren().addAll(editBtn, removeBtn);

        // Bottom: agregar nuevo ítem
        GridPane addForm = new GridPane();
        addForm.setHgap(8);
        addForm.setVgap(8);

        Label itemLbl = new Label("Ítem:");
        Label priceLbl = new Label("Precio:");
        itemField.setPromptText("Ej: Risotto de trufa");
        priceField.setPromptText("Ej: 65.00");

        Button addBtn = new Button("Agregar");
        addBtn.getStyleClass().add("primary-btn");
        addBtn.setOnAction(e -> addItem());

        addForm.addRow(0, itemLbl, itemField, priceLbl, priceField, addBtn);

        root.setTop(header);
        root.setCenter(itemsView);
        root.setRight(rightPanel);
        root.setBottom(addForm);

        Scene scene = new Scene(root, 700, 450);
        scene.getStylesheets().add("styles/michelin.css");
        stage.setScene(scene);
    }

    public void showAndWait() {
        stage.showAndWait();
    }

    private void refreshList() {
        itemsView.getItems().clear();
        IntStream.range(0, order.getItems().size()).forEach(i -> {
            String name = order.getItems().get(i);
            double price = order.getPrices().get(i);
            itemsView.getItems().add(name + " — $" + String.format("%.2f", price));
        });
    }

    private void updateTotal() {
        double sum = order.getPrices().stream().mapToDouble(Double::doubleValue).sum();
        // sincroniza con la mesa
        table.setOccupied(sum > 0);
        // recalcula consumo acumulado desde cero para evitar drift:
        // primero resetea y vuelve a sumar (en este patrón simple)
        // Aquí asumimos coherencia con la lista de precios del Order.
        // Reset "virtual": calculamos y mostramos sum, y mantenemos totalConsumption solo vía servicio.
        totalLabel.setText("Total: $" + String.format("%.2f", sum));
    }

    private void addItem() {
        String name = itemField.getText().trim();
        String priceText = priceField.getText().trim();
        if (name.isEmpty() || priceText.isEmpty()) {
            showAlert("Campos incompletos", "Debes ingresar el nombre del ítem y el precio.");
            return;
        }
        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) throw new NumberFormatException("Precio negativo");
        } catch (NumberFormatException ex) {
            showAlert("Precio inválido", "Ingresa un precio numérico válido (ej. 45.00).");
            return;
        }

        order.addItem(name, price);
        refreshList();
        updateTotal();

        itemField.clear();
        priceField.clear();
    }

    private void editSelected() {
        int index = itemsView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            showAlert("Sin selección", "Selecciona un ítem para editar su precio.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(String.format("%.2f", order.getPrices().get(index)));
        dialog.setTitle("Editar precio");
        dialog.setHeaderText(null);
        dialog.setContentText("Nuevo precio:");

        dialog.showAndWait().ifPresent(value -> {
            try {
                double price = Double.parseDouble(value);
                if (price < 0) throw new NumberFormatException("Precio negativo");
                order.getPrices().set(index, price);
                refreshList();
                updateTotal();
            } catch (NumberFormatException ex) {
                showAlert("Precio inválido", "Ingresa un precio numérico válido.");
            }
        });
    }

    private void removeSelected() {
        int index = itemsView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            showAlert("Sin selección", "Selecciona un ítem para eliminar.");
            return;
        }
        order.getItems().remove(index);
        order.getPrices().remove(index);
        refreshList();
        updateTotal();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.initOwner(stage);
        alert.showAndWait();
    }
}
