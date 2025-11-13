package main.java.com.empresa.dao;

import main.java.com.empresa.model.Producto;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private static final String FILE_PRODUCTOS = "inventario.txt";
    private static final String FILE_CSV = "docs/inventario.csv";

    // Lee todos los productos del fichero, carga desde CSV si está vacío
    public List<Producto> obtenerTodos() throws IOException {
        if (archivoVacio()) {
            cargarDesdeCSV();
        }

        List<Producto> productos = new ArrayList<>();
        if (!Files.exists(Paths.get(FILE_PRODUCTOS))) {
            return productos;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PRODUCTOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");
                if (campos.length >= 5) {
                    int id = Integer.parseInt(campos[0]);
                    String nombre = campos[1];
                    String categoria = campos[2];
                    double precio = Double.parseDouble(campos[3]);
                    int stock = Integer.parseInt(campos[4]);
                    productos.add(new Producto(id, nombre, categoria, precio, stock));
                }
            }
        }
        return productos;
    }

    public boolean archivoVacio() throws IOException {
        File file = new File(FILE_PRODUCTOS);
        return !file.exists() || file.length() == 0;
    }

    // Carga inicial desde CSV al archivo de inventario.txt
    public void cargarDesdeCSV() throws IOException {
        List<Producto> productos = new ArrayList<>();
        if (!Files.exists(Paths.get(FILE_CSV))) {
            System.out.println("Archivo CSV no encontrado: " + FILE_CSV);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_CSV))) {
            String linea = br.readLine(); // Saltar cabecera
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");
                if (campos.length == 5) {
                    int id = Integer.parseInt(campos[0]);
                    String nombre = campos[1];
                    String categoria = campos[2];
                    double precio = Double.parseDouble(campos[3]);
                    int stock = Integer.parseInt(campos[4]);

                    productos.add(new Producto(id, nombre, categoria, precio, stock));
                }
            }
        }
        guardarProductos(productos);
        System.out.println("Datos cargados desde CSV al archivo inventario.txt");
    }

    // Inserta un producto con id auto incremental
    public void insertarProducto(Producto producto) throws IOException {
        List<Producto> productos = obtenerTodos();
        int maxId = productos.stream().mapToInt(Producto::getId_producto).max().orElse(0);
        producto.setId_producto(maxId + 1);
        productos.add(producto);
        guardarProductos(productos);
    }

    // Actualiza un producto existente
    public void actualizarProducto(Producto producto) throws IOException {
        List<Producto> productos = obtenerTodos();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId_producto() == producto.getId_producto()) {
                productos.set(i, producto);
                break;
            }
        }
        guardarProductos(productos);
    }

    // Elimina un producto por id
    public void eliminarProducto(int id) throws IOException {
        List<Producto> productos = obtenerTodos();
        productos.removeIf(p -> p.getId_producto() == id);
        guardarProductos(productos);
    }

    // Guarda todos los productos en el fichero
    private void guardarProductos(List<Producto> productos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PRODUCTOS))) {
            for (Producto p : productos) {
                bw.write(p.getId_producto() + ";" + p.getNombre() + ";" + p.getCategoria() + ";" + p.getPrecio() + ";" + p.getStock());
                bw.newLine();
            }
        }
    }
}
