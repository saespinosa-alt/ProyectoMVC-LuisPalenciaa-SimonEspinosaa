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
 * Implementa el Factory Method para crear mesas.
 */
public class SimpleTableFactory extends TableFactory {

    @Override
    public Table createTable(int number) {
        return new Table(number);
    }
}

