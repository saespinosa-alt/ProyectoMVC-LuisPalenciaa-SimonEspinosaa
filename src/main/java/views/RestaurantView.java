/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

/**
 *
 * @author simon
 */

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;

/**
 * Vista compuesta del restaurante (Composite principal).
 */

//Vista del restaurante, se busco optimizar
public class RestaurantView extends ViewComponent {

    private final TilePane grid = new TilePane();
    private final ScrollPane scrollPane = new ScrollPane();

    public RestaurantView() {
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPrefColumns(8); // ajustable
        grid.getStyleClass().add("restaurant-grid");

        scrollPane.setContent(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent;");

        getChildren().add(scrollPane);
    }

    public void addComponent(ViewComponent component) {
        grid.getChildren().add(component);
    }

    public void clear() {
        grid.getChildren().clear();
    }

    @Override
    public void render() {
        for (Node node : grid.getChildren()) {
            if (node instanceof ViewComponent) {
                ((ViewComponent) node).render();
            }
        }
    }
}

