<div align="center">

# TECNICATURA UNIVERSITARIA EN PROGRAMACIÓN (A DISTANCIA)
### Programación 2
# TP Integrador — Food Store (Java · App de consola)

**Sistema de gestión de categorías, productos, usuarios y pedidos de comida con POO, colecciones, validaciones y baja lógica.**

[![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)]()
[![Maven](https://img.shields.io/badge/Maven-Proyecto-ef4444?logo=apachemaven&logoColor=white)]()
[![NetBeans](https://img.shields.io/badge/IDE-NetBeans-1B6AC6?logo=apachenetbeanside&logoColor=white)]()
[![CLI](https://img.shields.io/badge/Interface-Consola-222)]()
[![Status](https://img.shields.io/badge/Estado-Listo%20para%20probar-22c55e)]()

</div>

---

## 🧭 Tabla de contenidos
- [Descripción del programa](#-descripción-del-programa)
- [Funcionalidades principales](#-funcionalidades-principales)
- [Instrucciones de uso](#️-instrucciones-de-uso)
- [Ejemplos de entradas y salidas](#-ejemplos-de-entradas-y-salidas)
- [Requisitos](#-requisitos)
- [Ejecución rápida](#️-ejecución-rápida)
- [Flujo](#️-flujo)
- [Estructura del proyecto](#️-estructura-del-proyecto)
- [Documentación y entrega](#-documentación-y-entrega)
- [Participación de los integrantes](#-participación-de-los-integrantes)

---

## 💡 Descripción del programa
Aplicación de **consola** desarrollada en **Java 21** para administrar un negocio de comidas llamado **Food Store**.

El sistema permite gestionar **categorías**, **productos**, **usuarios** y **pedidos**, aplicando los contenidos trabajados en Programación 2: **Programación Orientada a Objetos**, herencia, encapsulamiento, interfaces, colecciones en memoria, excepciones propias, validaciones y separación por capas.

La información se mantiene durante la ejecución del programa mediante **ArrayList**, sin utilizar base de datos. Todas las bajas se realizan de forma lógica usando el atributo `eliminado`, para no perder el historial de los datos cargados.

---

## ✅ Funcionalidades principales

| 🗂️ Categorías | 🍔 Productos | 👤 Usuarios | 🧾 Pedidos | 🛡️ Validaciones |
|---|---|---|---|---|
| Listar · Crear · Editar · Eliminar | Listar · Crear · Editar · Eliminar | Listar · Crear · Editar · Eliminar | Listar · Crear con detalles · Actualizar · Eliminar | Datos no vacíos · IDs existentes · mail único · stock/precio válidos |

### Resumen técnico
- CRUD completo de **Categoría**, **Producto**, **Usuario** y **Pedido**.
- Baja lógica mediante `eliminado = true`.
- Uso de colecciones en memoria con `ArrayList`.
- Entidades organizadas a partir de una clase abstracta `Base`.
- Interfaz `Calculable` implementada en la clase `Pedido`.
- Excepciones propias para errores de negocio y validación.
- Menú de consola con validación de opciones.
- Relación **1:N** entre `Categoria` y `Producto`.
- Relación **N:1** entre `Producto` y `Categoria`.
- Relación **1:N** entre `Usuario` y `Pedido`.
- Relación de composición **1:N** entre `Pedido` y `DetallePedido`.
- Relación **1:1** entre `Pedido` y `ComprobantePedido`.

---

## 🛠️ Instrucciones de uso

### Abrir el proyecto en NetBeans
1. Descargar y descomprimir el archivo `.zip`.
2. Abrir **NetBeans**.
3. Ir a **File > Open Project**.
4. Seleccionar la carpeta `FoodStoreTPI`.
5. Esperar a que NetBeans detecte el proyecto Maven.
6. Ejecutar la clase principal:

```text
integrado.prog2.Main
```

### Menú principal
Al iniciar el programa se muestra un menú similar al siguiente:

```text
=== SISTEMA DE PEDIDOS (FOOD STORE) ===
1. Categorías
2. Productos
3. Usuarios
4. Pedidos
0. Salir
Seleccione:
```

Cada opción abre su propio submenú con las operaciones disponibles.

---

## 🧪 Ejemplos de entradas y salidas

**Ejemplo 1 — Listar categorías**
```text
> Opción: 1
> Categorías > Listar

ID: 1 | Nombre: Hamburguesas | Descripción: Productos con carne, pollo o veggie
ID: 2 | Nombre: Pizzas | Descripción: Pizzas clásicas y especiales
ID: 3 | Nombre: Bebidas | Descripción: Bebidas frías
```

**Ejemplo 2 — Crear producto**
```text
> Opción: Productos > Crear
Nombre: Hamburguesa doble
Descripción: Medallón doble con queso
Precio: 7500
Stock: 15
Imagen: hamburguesa_doble.jpg
Disponible: true
Categoría ID: 1

Producto creado correctamente con ID: 4
```

**Ejemplo 3 — Validación de precio incorrecto**
```text
Precio: -100
[Error] El precio no puede ser negativo.
```

**Ejemplo 4 — Crear usuario con mail repetido**
```text
Mail: usuario@mail.com
[Error] Ya existe un usuario registrado con ese mail.
```

**Ejemplo 5 — Crear pedido con detalle**
```text
Usuario ID: 1
Producto ID: 2
Cantidad: 2
Forma de pago: EFECTIVO

Pedido creado correctamente.
Total calculado: $15000.0
```

**Ejemplo 6 — Baja lógica**
```text
Ingrese ID a eliminar: 3
¿Confirma la eliminación? (S/N): S
Registro eliminado correctamente.
```

> Nota: Los datos iniciales pueden variar según las pruebas realizadas durante la ejecución.

---

## 🧩 Requisitos
- **Java JDK 21**
- **Apache Maven**
- **NetBeans** o cualquier IDE compatible con proyectos Maven
- Sistema operativo: Windows, Linux o macOS

---

## ▶️ Ejecución rápida

**Windows (NetBeans)**
```text
File > Open Project > FoodStoreTPI > Run
```

**Windows (PowerShell)**
```powershell
cd FoodStoreTPI
mvn compile exec:java
```

**Compilación manual en PowerShell**
```powershell
cd FoodStoreTPI
mkdir out
javac -encoding UTF-8 --release 21 -d out (Get-ChildItem -Recurse src/main/java/*.java).FullName
java -cp out integrado.prog2.Main
```

**Linux / macOS**
```bash
cd FoodStoreTPI
mvn compile exec:java
```

---

## 🗺️ Flujo
```text
INICIO
  ↓
Carga de datos iniciales
  ↓
MENÚ PRINCIPAL
  ↓
Categorías / Productos / Usuarios / Pedidos
  ↓
Listar · Crear · Editar · Eliminar
  ↓
Validar datos → Aplicar lógica → Mostrar resultado
  ↓
Volver al menú o salir
```

---

## 🗂️ Estructura del proyecto
```text
FoodStoreTPI/
├─ pom.xml
├─ README.md
├─ docs/
│  ├─ Informe_TPI_FoodStore.pdf
│  ├─ UML_FoodStore.puml
│  └─ Guion_Video_BASE.txt
└─ src/
   └─ main/
      └─ java/
         └─ integrado/
            └─ prog2/
               ├─ Main.java
               ├─ config/        # carga de datos iniciales
               ├─ entities/      # clases del dominio
               ├─ enums/         # Rol, Estado y FormaPago
               ├─ exceptions/    # excepciones propias
               ├─ interfaces/    # interfaz Calculable
               ├─ services/      # lógica de negocio y CRUD
               └─ ui/            # menú e ingreso de datos por consola
```

---

## 📄 Documentación y entrega

El proyecto incluye documentación técnica dentro de la carpeta `docs`:

```text
docs/Informe_TPI_FoodStore.pdf
docs/UML_FoodStore.puml
docs/Guion_Video_BASE.txt
```

Antes de la entrega final, completar en este README:

```text
LINK_VIDEO_PUBLICO: pendiente
LINK_REPOSITORIO_PUBLICO: pendiente
```

---

## 👥 Participación de los integrantes

| Integrante        | Rol / Actividad principal                                      | % de aporte |
|-------------------|----------------------------------------------------------------|------------:|
| **Santino Arias** | Desarrollo, pruebas, documentación, repositorio y video         | **100%**    |

