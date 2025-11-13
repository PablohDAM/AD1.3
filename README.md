# Sistema de Gestión de Inventario con Ficheros

Este proyecto es un sistema de gestión de inventario que reemplaza el uso de bases de datos por archivos de texto para almacenar la información de productos y realizar un seguimiento de las operaciones realizadas. Incluye funcionalidades para mantener un registro de operaciones, realizar copias de seguridad del inventario y gestionar el inventario mediante un menú interactivo por consola.

---

## Características principales

- Persistencia de datos con archivos de texto (`inventario.txt` para inventario, `registro.txt` para historial de operaciones).
- Carga inicial de productos desde un archivo CSV (`inventario.csv`) si el inventario está vacío.
- Registro detallado de todas las operaciones (altas, bajas, modificaciones, consultas y backups) con fecha y hora en el archivo de registro.
- Funcionalidad para crear copias de seguridad únicas del archivo de inventario con marca temporal.
- Menú interactivo para:
  - Gestionar productos (listar, añadir, modificar y eliminar).
  - Consultar el historial completo de operaciones.
  - Realizar copias de seguridad del inventario.

---

## Estructura de archivos principales

- `productos.txt` - Archivo principal que almacena la información de los productos.
- `registro.txt` - Archivo donde se almacenan todas las operaciones realizadas con marca temporal.
- `inventario.csv` - Archivo CSV usado para cargar datos iniciales si `productos.txt` está vacío.

---

## Requisitos

- JDK 11 o superior.
- IDE recomendado: IntelliJ IDEA, Eclipse o similar.
- Sistema operativo: multiplataforma (Windows, Linux, macOS).

---

## Cómo ejecutar

1. Clona o descarga el repositorio.
2. Coloca el archivo `inventario.csv` en la raíz del proyecto (igual nivel que `productos.txt`).
3. Compila las clases Java.
4. Ejecuta la clase `Main`.
5. Sigue el menú interactivo para gestionar el inventario, consultar el historial o hacer backups.

---

## Componentes principales

- **ProductoDAO**: Maneja la lectura y escritura de productos en el archivo de inventario. Carga el inventario inicial desde `inventario.csv` si `productos.txt` está vacío.
- **InventarioService**: Implementa la lógica de negocio para operaciones CRUD sobre productos y registra cada operación.
- **RegistroUtil**: Clase utilitaria para registrar las operaciones con fecha y hora en un archivo de texto.
- **BackupUtil**: Permite crear copias de seguridad del archivo de inventario con nombre único.
- **Main**: Interfaz de usuario en consola con menú para selección de acciones.

---

## Cómo funciona la carga inicial

- Al iniciar, si el archivo `productos.txt` está vacío o no existe, el sistema carga automáticamente los productos disponibles desde `inventario.csv`.
- La estructura del CSV debe ser:

```
id_producto;nombre;categoria;precio;stock
1;Auriculares 212;Electrónica;1322.25;423
2;Gorra 629;Ropa;745.71;116
3;Portátil 670;Informática;1082.42;261
4;Zapatos 881;Ropa;1011.9;151
5;Altavoz 997;Electrónica;1103.48;392
```

---

## Registro de operaciones

Todas las acciones importantes quedan reflejadas en `registro.txt` con el siguiente formato:

```
[YYYY-MM-DD HH:mm:ss] TIPO_OPERACION: Descripción de la acción
```

Ejemplo:
```
[2025-11-13 17:00:00] ALTA: Producto creado: Auriculares 212
[2025-11-13 17:05:12] BACKUP: Backup generado: inventario_backup_2025-11-13_17-05-12.txt
```

