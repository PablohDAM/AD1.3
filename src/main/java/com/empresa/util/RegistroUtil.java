package main.java.com.empresa.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroUtil {

    private static final String FILE_REGISTRO = "registro.txt";
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Registra una operación en el archivo registro.txt con fecha y hora y tipo
    public static void registrarOperacion(String tipoOperacion, String descripcion) {
        String fechaHora = LocalDateTime.now().format(FORMATO_FECHA_HORA);
        String lineaRegistro = String.format("[%s] %s: %s", fechaHora, tipoOperacion.toUpperCase(), descripcion);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_REGISTRO, true))) {
            bw.write(lineaRegistro);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al registrar operacion: " + e.getMessage());
        }
    }
}
