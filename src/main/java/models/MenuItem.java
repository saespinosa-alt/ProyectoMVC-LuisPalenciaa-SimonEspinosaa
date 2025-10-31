/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author simon
 */
/**
 * Representa un ítem del menú.
 */
public class MenuItem {
    private String name;
    private double price;
    private String category;
    private String description;

    public MenuItem(String name, double price) {
        this(name, price, "", "");
    }

    public MenuItem(String name, double price, String category, String description) {
        this.name = name;
        this.price = price;
        this.category = category == null ? "" : category;
        this.description = description == null ? "" : description;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return name + " — $" + String.format("%.2f", price);
    }

    /**
     * Serializa a una línea para guardar en TXT (pipe-separated).
     */
    public String toLine() {
        // Escapamos pipes (si acaso), reemplazándolos por '-'
        return name.replace("|", "-") + "|" + price + "|" + category.replace("|", "-") + "|" + description.replace("|", "-");
    }

    /**
     * Deserializa desde línea. Devuelve null si la línea es inválida.
     */
    public static MenuItem fromLine(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split("\\|", -1);
        if (parts.length < 2) return null;
        String name = parts[0].trim();
        double price;
        try {
            price = Double.parseDouble(parts[1].trim());
        } catch (NumberFormatException ex) {
            return null;
        }
        String category = parts.length > 2 ? parts[2].trim() : "";
        String desc = parts.length > 3 ? parts[3].trim() : "";
        return new MenuItem(name, price, category, desc);
    }
}