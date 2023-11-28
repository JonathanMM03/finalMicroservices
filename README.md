ShopAll.com - Documentación del Proyecto
Descripción
ShopAll.com es una aplicación web diseñada para gestionar productos y personas, permitiendo la creación, actualización, eliminación y visualización de información. La aplicación está construida utilizando el framework Spring Boot para el backend y Thymeleaf como motor de plantillas para el frontend.

Estructura del Proyecto
1. Controladores
a. ProductoController
Este controlador maneja las operaciones relacionadas con los productos en el catálogo. Incluye endpoints para obtener, eliminar, actualizar y agregar productos, así como obtener productos ordenados por precio.

java
Copy code
package ShopAll.com.Controller;

// Importaciones omitidas para mayor claridad

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos")
@CrossOrigin(origins="*")
public class ProductoController {

    // ... Código del controlador
}
b. PersonaController
Este controlador maneja las operaciones relacionadas con las personas. Incluye endpoints para la creación, obtención, actualización, eliminación y listado de personas. También incluye operaciones para gestionar la lista de compra de un usuario.

java
Copy code
package ShopAll.com.Controller;

// Importaciones omitidas para mayor claridad

@RestController
@RequestMapping("/personas")
@Tag(name = "Usuarios")
@CrossOrigin(origins="*")
public class PersonaController {

    // ... Código del controlador
}
c. HomeController
Este controlador gestiona las páginas de inicio, inicio de sesión y páginas relacionadas con la seguridad de la aplicación.

java
Copy code
package ShopAll.com.Controller;

// Importaciones omitidas para mayor claridad

@Controller
public class HomeController {

    // ... Código del controlador
}
2. Configuración
SecurityConfig
Configuración de seguridad para gestionar la autenticación y autorización en la aplicación.

java
Copy code
package ShopAll.com.config;

// Importaciones omitidas para mayor claridad

@Configuration
public class SecurityConfig {

    // ... Configuración de seguridad
}

Tecnologías Utilizadas
Backend: Spring Boot
Frontend: Thymeleaf
Base de Datos: Configurable (ver archivo application.properties o application.yml)
Seguridad: Spring Security
Otras Tecnologías: Jacoco, Lombok, JUnit, Mockito, Swagger
