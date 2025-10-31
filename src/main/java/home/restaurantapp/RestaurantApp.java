package home.restaurantapp;



/**
 * Aplicación principal JavaFX del restaurante.
 */

import controllers.RestaurantController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import views.RestaurantView;


public class RestaurantApp extends Application {

    @Override
    public void start(Stage stage) {
        // Crear vista principal
        RestaurantView restaurantView = new RestaurantView();

        // Crear controlador principal y pasarle la vista
        RestaurantController controller = new RestaurantController(restaurantView);
        controller.setAppStage(stage);

        // Delegar al controlador la tarea de construir y mostrar la interfaz
        controller.showMainView();
        //Separa la logica visual del main hacia controladores y vistas. Es decir, la tarea del main solo será iniciar el programa.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
