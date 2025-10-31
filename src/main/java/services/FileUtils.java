/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author simon
 */
/**
 * Utilidades sencillas de lectura/escritura de texto.
 * Crea carpeta "data" en el working dir y subcarpetas necesarias.
 */
public class FileUtils {
    private static final String DATA_DIR = "data";
    private static final String FACTURAS_DIR = DATA_DIR + File.separator + "facturas";

    public static void ensureDataDirs() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            Files.createDirectories(Paths.get(FACTURAS_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 public static String readFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) return "";

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString().trim();
    }


    public static List<String> readLines(String relativePath) {
        ensureDataDirs();
        Path p = Paths.get(relativePath);
        if (!Files.exists(p)) return new ArrayList<>();
        try {
            return Files.readAllLines(p);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
           public static void writeFile(String fileName, String content) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) file.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }


    public static void writeLines(String relativePath, List<String> lines) {
        ensureDataDirs();
        Path p = Paths.get(relativePath);
        try {
            Files.createDirectories(p.getParent() == null ? p : p.getParent());
            Files.write(p, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendLines(String relativePath, List<String> lines) {
        ensureDataDirs();
        Path p = Paths.get(relativePath);
        try (BufferedWriter bw = Files.newBufferedWriter(p, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String invoicesDir() {
        ensureDataDirs();
        return FACTURAS_DIR;
    }

    public static String dataFile(String filename) {
        ensureDataDirs();
        return DATA_DIR + File.separator + filename;
    }
}