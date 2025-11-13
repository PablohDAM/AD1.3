package main.java.com.empresa;

import main.java.com.empresa.model.Producto;
import main.java.com.empresa.service.InventarioService;
import main.java.com.empresa.util.BackupUtil;
import main.java.com.empresa.util.RegistroUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static InventarioService inventarioService = new InventarioService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        
        inventarioService.inicializarInventario();
        
        while (!salir) {
            System.out.println("\n=== Menú Inventario ===");
            System.out.println("1. Gestionar inventario");
            System.out.println("2. Consultar historial de operaciones");
            System.out.println("3. Copia de seguridad del inventario");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    gestionarInventario(scanner);
                    break;
                case "2":
                    consultarHistorial();
                    break;
                case "3":
                    realizarBackup();
                    break;
                case "4":
                    salir = true;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }

    private static void gestionarInventario(Scanner scanner) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n-- Gestión de Inventario --");
            System.out.println("1. Listar productos");
            System.out.println("2. Añadir producto");
            System.out.println("3. Modificar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Volver");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            try {
                switch (opcion) {
                    case "1":
                        List<Producto> productos = inventarioService.obtenerTodos();
                        if (productos.isEmpty()) {
                            System.out.println("No hay productos en el inventario.");
                        } else {
                            productos.forEach(p -> System.out.println(p));
                        }
                        break;
                    case "2":
                        Producto nuevo = pedirDatosProducto(scanner);
                        inventarioService.crearProducto(nuevo);
                        System.out.println("Producto añadido.");
                        break;
                    case "3":
                        System.out.print("Ingrese ID del producto a modificar: ");
                        int idModificar = Integer.parseInt(scanner.nextLine());
                        Producto productoMod = inventarioService.obtenerTodos()
                                .stream()
                                .filter(p -> p.getId_producto() == idModificar)
                                .findFirst()
                                .orElse(null);
                        if (productoMod == null) {
                            System.out.println("Producto no encontrado.");
                        } else {
                            Producto modificado = pedirDatosProducto(scanner);
                            modificado.setId_producto(idModificar);
                            inventarioService.actualizarProducto(modificado);
                            System.out.println("Producto modificado.");
                        }
                        break;
                    case "4":
                        System.out.print("Ingrese ID del producto a eliminar: ");
                        int idEliminar = Integer.parseInt(scanner.nextLine());
                        inventarioService.eliminarProducto(idEliminar);
                        System.out.println("Producto eliminado.");
                        break;
                    case "5":
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, ingrese un número válido.");
            }
        }
    }

    private static Producto pedirDatosProducto(Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());
        System.out.print("Stock: ");
        int stock = Integer.parseInt(scanner.nextLine());

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setPrecio(precio);
        producto.setStock(stock);

        return producto;
    }

    private static void consultarHistorial() {
        System.out.println("\n-- Historial de Operaciones --");
        try (BufferedReader br = new BufferedReader(new FileReader("registro.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el historial o está vacío.");
        }
    }

    private static void realizarBackup() {
        try {
            String backupPath = BackupUtil.crearBackup();
            System.out.println("Backup creado: " + backupPath);
            RegistroUtil.registrarOperacion("BACKUP", "Backup generado: " + backupPath);
        } catch (IOException e) {
            System.out.println("Error al crear backup: " + e.getMessage());
        }
    }
   
}
