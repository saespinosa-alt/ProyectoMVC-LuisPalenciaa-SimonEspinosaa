/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.MenuItem;
import models.Table;
import controllers.MenuController;
import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import services.MenuService;
/**
 *
 * @author simon
 */

/**
 * Vista para editar el menú y/o seleccionar ítems para una mesa.
 * Si 'tableForSelection' es null => modo editor. Si no es null => modo selección (Agregar a mesa / Pagar).
 */
public class MenuView {
    private final Stage stage = new Stage();


    private final ListView<MenuItem> listView = new ListView<>();
    private final TextField searchField = new TextField();

    public MenuView(Stage owner, List<MenuItem> menuList, MenuController controller, Table tableForSelection) {

        stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(tableForSelection == null ? "Menú (Editor)" : "Menú - Mesa #" + tableForSelection.getNumber());

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        // Campos de texto
TextField nameField = new TextField();
nameField.setPromptText("Nombre del producto");
nameField.setPrefWidth(150);

TextField priceField = new TextField();
priceField.setPromptText("Precio");
priceField.setPrefWidth(100);

// Botones
    Button addBtn = new Button("Agregar producto");
    Button deleteBtn = new Button("Eliminar seleccionado");
    Button saveBtn = new Button("Guardar menú");
        // Top: search + buttons
// Barra superior (formulario + búsqueda)
HBox top = new HBox(8);
top.setPadding(new Insets(8));

//  Campo de búsqueda (siempre visible)
TextField searchField = new TextField();
searchField.setPromptText("Buscar producto...");
searchField.setPrefWidth(200);


// Si NO hay mesa seleccionada → mostrar campos para agregar y guardar
if (tableForSelection == null) {
    
    nameField.setPromptText("Nombre del producto");
    nameField.setPrefWidth(150);

    
    priceField.setPromptText("Precio");
    priceField.setPrefWidth(100);

    top.getChildren().addAll(
        new Label("Nombre:"), nameField,
        new Label("Precio:"), priceField,
        addBtn, deleteBtn, saveBtn,
        new Label("Buscar:"), searchField
    );
} else {
    // Si hay mesa seleccionada, solo mostrar eliminar + buscador
    top.getChildren().addAll(
        new Label("Buscar:"), searchField
    );
}
        // Center: list
    ObservableList<MenuItem> observableMenu = FXCollections.observableArrayList(menuList);

    FilteredList<MenuItem> filtered = new FilteredList<>(observableMenu, p -> true);

    // Asignar al ListView
    listView.setItems(filtered);
    listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Bottom: actions según modo
        HBox bottom = new HBox(8);
        bottom.setPadding(new Insets(8));
        if (tableForSelection == null) {
            Button closeBtn = new Button("Cerrar");
            bottom.getChildren().add(closeBtn);
            closeBtn.setOnAction(e -> stage.close());
        } else {
            Button addToTable = new Button("Agregar a mesa");
            Button payBtn = new Button("Pagar factura");
            Button closeBtn = new Button("Cerrar");
            bottom.getChildren().addAll(addToTable, payBtn, closeBtn);

            addToTable.setOnAction(e -> {
                List<MenuItem> selected = listView.getSelectionModel().getSelectedItems();
                if (selected == null || selected.isEmpty()) {
                    showAlert("Sin selección", "Selecciona al menos un producto para agregar a la mesa.");
                    return;
                }
                controller.addItemsToTable(tableForSelection, selected);
                showAlert("Agregado", "Productos agregados a la mesa #" + tableForSelection.getNumber());
            });

            payBtn.setOnAction(e -> {
                String path = controller.payTable(tableForSelection);
                if (path != null) {
                    showAlert("Pago registrado", "Comprobante generado:\n" + path);
                    stage.close();
                } else {
                    showAlert("Error", "No hay orden para esta mesa.");
                }
            });

            closeBtn.setOnAction(e -> stage.close());
        }
        
// Servicio y lista observable
MenuService menuService = new MenuService();

        addBtn.setOnAction(e -> {
    String name = nameField.getText().trim();
    String priceText = priceField.getText().trim();

    if (name.isEmpty() || priceText.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "Campos vacíos", "Por favor, completa todos los campos.");
        return;
    }

    try {
        double price = Double.parseDouble(priceText);
        MenuItem item = new MenuItem(name, price);
        observableMenu.add(item); // agregar al ObservableList, no al ListView directamente
        nameField.clear();
        priceField.clear();
        showAlert(Alert.AlertType.INFORMATION, "Producto agregado", "El producto se agregó correctamente.");
    } catch (NumberFormatException ex) {
        showAlert(Alert.AlertType.ERROR, "Error en precio", "Introduce un número válido para el precio.");
    }
});
        saveBtn.setOnAction(e -> {
    try {
        models.Menu menu = new models.Menu();
        menu.getItems().addAll(observableMenu);
        boolean success = menuService.saveMenu(menu);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Menú guardado", "El menú se guardó correctamente.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Menú vacío", "No hay productos para guardar.");
        }

    } catch (IOException ex) {
        showAlert(Alert.AlertType.ERROR, "Error al guardar", "No se pudo guardar el menú: " + ex.getMessage());
    }
});
       deleteBtn.setOnAction(e -> {
    List<MenuItem> sel = listView.getSelectionModel()
                                 .getSelectedItems()
                                 .stream()
                                 .collect(Collectors.toList());
    if (sel.isEmpty()) {
        showAlert("Sin selección", "Selecciona productos para eliminar.");
        return;
    }

    observableMenu.removeAll(sel);

    showAlert("Eliminado", "Se eliminaron los productos seleccionados.");
});


        // búsqueda en tiempo real
        searchField.textProperty().addListener((obs, old, nw) -> {
            final String q = nw == null ? "" : nw.toLowerCase();
            filtered.setPredicate(mi -> mi.getName().toLowerCase().contains(q));
        });

        root.setTop(top);
        root.setCenter(listView);
        root.setBottom(bottom);

        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add("styles/michelin.css");
        stage.setScene(scene);
    }

    public void showAndWait() {
        stage.showAndWait();
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

    private void showAlert(String title, String content) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.initOwner(stage);
        a.showAndWait();
    }
}
