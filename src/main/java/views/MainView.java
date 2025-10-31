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
import models.Table;

/**
 * Vista principal que contiene los controles superiores (spinner, botones)
 * y la vista central del restaurante.
 */
public class MainView {
    private final BorderPane root;
    private final Spinner<Integer> tableSpinner;
    private final Button generateBtn;
    private final Button menuBtn;
    private final RestaurantView restaurantView;

    public MainView(RestaurantView restaurantView) {
        this.restaurantView = restaurantView;

        // Spinner de número de mesas
        tableSpinner = new Spinner<>(1, 40, 12);

        // Botones superiores
        generateBtn = new Button("Generar mesas");
        generateBtn.getStyleClass().add("primary-btn");

        menuBtn = new Button("Menú");
        menuBtn.getStyleClass().add("primary-btn");

        // Barra superior
        HBox topBar = new HBox(10, new Label("Mesas:"), tableSpinner, generateBtn, menuBtn);
        topBar.setPadding(new Insets(12));
        topBar.getStyleClass().add("top-bar");

        // Layout principal
        root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(restaurantView);
    }

    public Scene buildScene() {
        Scene scene = new Scene(root, 1300, 1000);
        scene.getStylesheets().add("styles/michelin.css");
        return scene;
    }

    // Getters para que el controlador los use
    public Spinner<Integer> getTableSpinner() { return tableSpinner; }
    public Button getGenerateBtn() { return generateBtn; }
    public Button getMenuBtn() { return menuBtn; }
}