/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author simon
 */

/**
 * Componente base para el Composite de controladores.
 * Los subcontroladores pueden sobreescribir init() y liberar recursos si es necesario.
 */
public abstract class ControllerComponent {
    public void init() {}
    public void destroy() {}
}
