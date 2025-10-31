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
 * Fábrica abstracta para la creación de mesas.
 */
public abstract class TableFactory {
    public abstract Table createTable(int number);
}

