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
import services.OrderService;
import models.SimpleTableFactory;
import services.InvoiceService;
import services.MenuService;
import views.MainView;
import views.RestaurantView;
/**
 * Controlador principal (root del Composite). Orquesta TableController, OrderController y MenuController.
 */
public class RestaurantController extends CompositeController {
    private final OrderService service = new OrderService();
    private final SimpleTableFactory factory = new SimpleTableFactory();

    private final RestaurantView view;
    private Stage appStage;

    // children
    private final TableController tableController;
    private final OrderController orderController;
    private final MenuController menuController;

    public RestaurantController(RestaurantView view) {
        this.view = view;

        this.tableController = new TableController(view, service, factory);
        this.orderController = new OrderController(service);

        // servicios para menu/invoice
        MenuService menuService = new MenuService();
        InvoiceService invoiceService = new InvoiceService();
        this.menuController = new MenuController(menuService, service, invoiceService);

        // registramos hijos en el composite
        addChild(tableController);
        addChild(orderController);
        addChild(menuController);

        // callback: cuando TableController detecte selección, abrimos el MenuView para esa mesa
        tableController.setOnTableSelected(table -> {
            menuController.openMenuForTable(appStage, table);
            // despues de cerrar menú, re-renderizamos la vista
            view.render();
        });
    }
     public void showMainView() {
        // Crear la vista principal con su barra superior
        MainView mainView = new MainView(view);

        // Conectar eventos a la lógica existente
        mainView.getGenerateBtn().setOnAction(e ->
            initTables(mainView.getTableSpinner().getValue())
        );

        mainView.getMenuBtn().setOnAction(e ->
            openMenuEditor()
        );

        // Mostrar la escena
        appStage.setTitle("Restaurante - Gestión de Mesas y Pedidos");
        appStage.setScene(mainView.buildScene());
        appStage.setMaximized(true);
        appStage.show();

        // Generar mesas por defecto
        initTables(mainView.getTableSpinner().getValue());
    }


    public void setAppStage(Stage stage) {
        this.appStage = stage;
    }

    /**
     * Inicializa las mesas (delegado al TableController).
     */
    public void initTables(int count) {
        if (count < 1 || count > 40) {
            throw new IllegalArgumentException("El número de mesas debe estar entre 1 y 40");
        }
        tableController.initTables(count);
    }

    /**
     * Abre el editor del menú (usado por botón "Menú" en la UI).
     */
    public void openMenuEditor() {
        menuController.openMenuEditor(appStage);
    }

    /**
     * Método auxiliar para atajos desde fuera (ej: agregar ítem rápido).
     */
    public void addQuickItem(models.Table table, String item, double price) {
        service.placeOrder(table, item, price);
        view.render();
    }
}

