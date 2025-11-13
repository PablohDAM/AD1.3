package main.java.com.empresa.service;

import main.java.com.empresa.dao.ProductoDAO;
import main.java.com.empresa.model.Producto;
import main.java.com.empresa.util.LogUtil;
import main.java.com.empresa.util.RegistroUtil; 

import java.io.IOException;
import java.util.List;

public class InventarioService {

    private final ProductoDAO productoDAO;

    public InventarioService() {
        this.productoDAO = new ProductoDAO();
    }

    // Crear producto, guardar en fichero y registrar operación
    public void crearProducto(Producto producto) {
        try {
            productoDAO.insertarProducto(producto);
            LogUtil.info("Producto creado: " + producto.getNombre());
            RegistroUtil.registrarOperacion("ALTA", "Producto creado: " + producto.getNombre());
        } catch (IOException e) {
            LogUtil.error("Error creando producto: " + e.getMessage());
        }
    }

    // Obtener toda la lista del inventario desde fichero
    public List<Producto> obtenerTodos() {
        try {
            List<Producto> productos = productoDAO.obtenerTodos();
            RegistroUtil.registrarOperacion("CONSULTA", "Listado de productos consultado");
            return productos;
        } catch (IOException e) {
            LogUtil.error("Error obteniendo productos: " + e.getMessage());
            return List.of();
        }
    }

    // Actualizar producto y registrar
    public void actualizarProducto(Producto producto) {
        try {
            productoDAO.actualizarProducto(producto);
            LogUtil.info("Producto actualizado: " + producto.getId_producto());
            RegistroUtil.registrarOperacion("MODIFICACION", "Producto actualizado: " + producto.getId_producto());
        } catch (IOException e) {
            LogUtil.error("Error actualizando producto: " + e.getMessage());
        }
    }

    // Eliminar producto y registrar
    public void eliminarProducto(int id) {
        try {
            productoDAO.eliminarProducto(id);
            LogUtil.info("Producto eliminado: " + id);
            RegistroUtil.registrarOperacion("BAJA", "Producto eliminado: " + id);
        } catch (IOException e) {
            LogUtil.error("Error eliminando producto: " + e.getMessage());
        }
    }
    
    public void inicializarInventario() {
        try {
            if (productoDAO.archivoVacio()) {
                productoDAO.cargarDesdeCSV();
            }
        } catch (IOException e) {
            System.out.println("Error inicializando inventario: " + e.getMessage());
        }
    }

}
