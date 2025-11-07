# 🍽️ RestaurantApp

## Descripción del proyecto
**RestaurantApp** es una aplicación de escritorio desarrollada en **JavaFX** utilizando arquitectura **MVC (Modelo-Vista-Controlador)**.  
El sistema permite gestionar las operaciones principales de un restaurante, incluyendo la visualización de mesas, la toma de órdenes, el manejo del menú y la generación de facturas.  
El proyecto se estructura de manera modular, favoreciendo la mantenibilidad, escalabilidad y reutilización de código.

---

##  Integrantes
- **Simón Espinosa Arteaga**  
- **Luis Palencia**

---

##  Lenguaje y herramientas
| Categoría | Herramienta / Tecnología |
|------------|--------------------------|
| **Lenguaje principal** | Java 17 |
| **Framework de interfaz gráfica** | JavaFX |
| **Gestor de dependencias** | Maven |
| **Entorno de desarrollo** | NetBeans Studio / Visual Studio Code |
| **Control de versiones** | Git & GitHub |
| **Diagramación UML** | PlantUML |
| **Estilos visuales** | CSS personalizado (`styles/michelin.css`) |

---

##  Patrones usados y su rol en el MVC

###  Modelo (Model)
Representa los datos y la lógica de negocio del restaurante.  
Incluye las clases: `Menu`, `MenuItem`, `Order`, `Table`, y servicios asociados (`OrderService`, `MenuService`, `InvoiceService`).

----> **Patrones de diseño aplicados:**
- **Factory Method (`TableFactory`, `SimpleTableFactory`):**  
  Permite crear objetos `Table` de manera flexible sin acoplar el código a una implementación específica.
- **Service Layer (`OrderService`, `MenuService`, `InvoiceService`):**  
  Encapsula la lógica de negocio y separa las operaciones complejas del modelo principal.

---

###  Vista (View)
Gestiona la interfaz gráfica del usuario mediante **JavaFX**.  
Incluye las clases: `RestaurantView`, `TableViewComponent`, `MenuView`, y `OrderDialog`.

 ---->**Patrones de diseño aplicados:**
- **Composite (`ViewComponent`, `RestaurantView`, `TableViewComponent`):**  
  Permite estructurar vistas compuestas de múltiples componentes visuales.
- **Command (`ViewCommand`):**  
  Encapsula acciones de la interfaz (por ejemplo, el clic en una mesa) en objetos comando reutilizables.

---

###  Controlador (Controller)
Actúa como intermediario entre el modelo y la vista.  
Incluye las clases: `RestaurantController`, `MenuController`, `OrderController`, y `CompositeController`.

----> **Patrones de diseño aplicados:**
- **Composite (`CompositeController`, `ControllerComponent`):**  
  Permite manejar múltiples controladores de forma jerárquica y coordinada.  
- **Observer (implícito en JavaFX):**  
  Facilita la comunicación entre los elementos visuales y los cambios en el modelo a través de eventos.


🧾 **Resumen:**  
RestaurantApp combina los patrones **Factory**, **Service Layer**, **Composite**, y **Command** dentro del marco **MVC**, logrando una aplicación modular, escalable y mantenible, alineada con buenas prácticas de diseño de software.
