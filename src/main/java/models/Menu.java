/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author simon
 */
public class Menu {
    private List<MenuItem> items;

    public Menu() {
        this.items = new ArrayList<>();
    }

    //  Agregar un producto al menú
    public void addItem(MenuItem item) {
        items.add(item);
    }

    // Eliminar un producto por nombre
    public void removeItem(String name) {
        items.removeIf(item -> item.getName().equalsIgnoreCase(name));
    }

    //  Buscar productos por texto parcial (para el buscador en la vista)
    public List<MenuItem> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return new ArrayList<>(items);
        }
        String lower = keyword.toLowerCase();
        return items.stream()
                .filter(item -> item.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    //  Obtener todos los productos
    public List<MenuItem> getItems() {
        return items;
    }

    //  Reemplazar toda la lista (útil al cargar desde el archivo)
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    //  Obtener un producto por nombre exacto (para añadir a la mesa)
    public MenuItem getItemByName(String name) {
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    //  Verificar si el menú está vacío
    public boolean isEmpty() {
        return items.isEmpty();
    }

    //  Representación tipo texto (para depuración o impresión)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== MENÚ ===\n");
        for (MenuItem item : items) {
            sb.append("- ").append(item.getName())
              .append(" | $").append(item.getPrice()).append("\n");
        }
        return sb.toString();
    }
}