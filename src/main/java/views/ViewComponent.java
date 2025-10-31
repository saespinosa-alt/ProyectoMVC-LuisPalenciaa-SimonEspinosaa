/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

/**
 *
 * @author simon
 */

import javafx.scene.layout.Pane;

/**
 * Componente base de la vista (patrón Composite).
 */
public abstract class ViewComponent extends Pane {
    public abstract void render();
}
