/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import models.Order;
import models.Table;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 *
 * @author simon
 */
/**
 * Servicio para generar/actualizar facturas (.txt) por mesa.
 * Las facturas se guardan en data/facturas/factura_mesa_N.txt
 */
public class InvoiceService {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private FileUtils FileUtils = new FileUtils();
    /**
     * Re-escribe la factura de la mesa (se llama cada vez que se agrega o elimina items).
     */
    public void updateInvoice(Table table, Order order) {
        FileUtils.ensureDataDirs();
        String filename = FileUtils.invoicesDir() + File.separator + "factura_mesa_" + table.getNumber() + ".txt";
        List<String> out = new ArrayList<>();
        out.add("Restaurante - Factura (Provisional)");
        out.add("------------------------------------");
        out.add("Mesa: " + table.getNumber());
        out.add("Fecha: " + sdf.format(new Date()));
        out.add("");
        out.add("Productos:");
        double total = 0.0;
        List<String> items = order.getItems();
        List<Double> prices = order.getPrices();
        for (int i = 0; i < items.size(); i++) {
            String line = "- " + items.get(i) + " -> $" + String.format("%.2f", prices.get(i));
            out.add(line);
            total += prices.get(i);
        }
        out.add("------------------------------------");
        out.add("Total: $" + String.format("%.2f", total));
        out.add("");
        out.add("Nota: Esta es una factura provisional. Pulse 'Pagar factura' para generar comprobante final.");
        // escribir (sobrescribe)
        FileUtils.writeLines(filename, out);
    }

    /**
     * Genera el comprobante final y borra/limpia la orden.
     * Devuelve la ruta del comprobante generado.
     */
    public String payAndGenerateReceipt(Table table, Order order) {
        FileUtils.ensureDataDirs();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String receiptFile = FileUtils.invoicesDir() + File.separator + "factura_mesa_" + table.getNumber() + "_paid_" + timestamp + ".txt";
        List<String> out = new ArrayList<>();
        out.add("Restaurante - Comprobante de Pago");
        out.add("------------------------------------");
        out.add("Mesa: " + table.getNumber());
        out.add("Fecha: " + sdf.format(new Date()));
        out.add("");
        out.add("Productos:");
        double total = 0.0;
        List<String> items = new ArrayList<>(order.getItems());
        List<Double> prices = new ArrayList<>(order.getPrices());
        for (int i = 0; i < items.size(); i++) {
            out.add("- " + items.get(i) + " -> $" + String.format("%.2f", prices.get(i)));
            total += prices.get(i);
        }
        out.add("------------------------------------");
        out.add("TOTAL PAGADO: $" + String.format("%.2f", total));
        out.add("");
        out.add("Gracias por su preferencia.");

        // escribir recibo final
        FileUtils.writeLines(receiptFile, out);

        // también podemos borrar la factura provisional si existe
        String provisional = FileUtils.invoicesDir() + File.separator + "factura_mesa_" + table.getNumber() + ".txt";
        File p = new File(provisional);
        if (p.exists()) p.delete();

        return receiptFile;
    }
}