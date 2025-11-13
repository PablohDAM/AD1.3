package main.java.com.empresa.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupUtil {

    private static final String INVENTARIO_FILE = "inventario.txt";
    private static final String BACKUP_PREFIX = "inventario_backup_";
    private static final String BACKUP_EXTENSION = ".txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    // Crea una copia de seguridad del inventario con nombre único por fecha/hora
    public static String crearBackup() throws IOException {
        File originalFile = new File(INVENTARIO_FILE);
        if (!originalFile.exists()) {
            throw new IOException("Archivo de inventario no existe: " + INVENTARIO_FILE);
        }

        String timestamp = LocalDateTime.now().format(FORMATTER);
        String backupFileName = BACKUP_PREFIX + timestamp + BACKUP_EXTENSION;
        File backupFile = new File(backupFileName);

        Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return backupFileName;
    }
}
