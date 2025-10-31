/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author simon
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Composite para controladores: contiene otros ControllerComponent y delega init/destroy.
 */
public class CompositeController extends ControllerComponent {
    private final List<ControllerComponent> children = new ArrayList<>();

    public void addChild(ControllerComponent child) {
        children.add(child);
    }

    public void removeChild(ControllerComponent child) {
        children.remove(child);
    }

    @Override
    public void init() {
        for (ControllerComponent c : children) {
            c.init();
        }
    }

    @Override
    public void destroy() {
        for (ControllerComponent c : children) {
            c.destroy();
        }
    }
}