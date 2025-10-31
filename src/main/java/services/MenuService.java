/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import models.MenuItem;

import java.util.ArrayList;
import java.util.List;
import models.Menu;
/**
 *
 * @author simon
 */
/**
 * Servicio que carga/guarda el menu en data/menu.txt
 */
public class MenuService {
    private static final String MENU_FILE = "menu.txt";

    public Menu loadMenu() throws IOException {
        List<String> lines = FileUtils.readLines(FileUtils.dataFile(MENU_FILE));
        Menu menu = new Menu();
        for (String l : lines) {
            if (!l.trim().isEmpty()) {
                menu.addItem(MenuItem.fromLine(l));
            }
        }
        return menu;
    }

    public boolean saveMenu(Menu menu) throws IOException {
        if (menu.getItems().isEmpty()) {
            return false; // indica que no hay nada que guardar
        }

        List<String> lines = new ArrayList<>();
        for (MenuItem item : menu.getItems()) {
            lines.add(item.toLine()); // usa formato con '|'
        }

        FileUtils.writeLines(FileUtils.dataFile(MENU_FILE), lines);
        return true;
    }
}
