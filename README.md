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

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Obtener un producto por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Long id) {
        try {
            Producto producto = productoService.obtenerProducto(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el producto por ID.");
        }
    }

    @Operation(summary = "Eliminar un producto por su ID")
    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarProductoPorId(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.ok("Producto eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto por ID.");
        }
    }

    @Operation(summary = "Actualizar un producto por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProductoPorId(@PathVariable Long id, @RequestBody Producto nuevoProducto) {
        try {
            Producto productoActualizado = productoService.actualizarProducto(id, nuevoProducto);

            if (productoActualizado != null) {
                return ResponseEntity.ok("Producto actualizado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado.");
            }
        } catch (ProductErrorRegister e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el producto por ID.");
        }
    }
    @Operation(summary = "Obtener todos los productos")
    @GetMapping("/getAll")
    public ResponseEntity<?> obtenerTodosLosProductos() {
        try {
            List<Producto> productos = productoService.obtenerTodosLosProductos();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener todos los productos.");
        }
    }

    @Operation(summary = "Crear un nuevo producto en el catálogo")
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarProducto(@Valid @RequestBody Producto nuevoProducto) {
        try {
            productoService.agregarProducto(nuevoProducto);
            return ResponseEntity.ok("Producto agregado al catálogo exitosamente.");
        } catch (ProductErrorRegister e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar el producto al catálogo.");
        }
    }

    @Operation(summary = "Obtener todos los productos ordenados por precio (de mayor a menor)")
    @GetMapping("/getAllSortedByPrice")
    public ResponseEntity<?> obtenerProductosOrdenadosPorPrecio() {
        try {
            List<Producto> productosOrdenados = productoService.obtenerProductosOrdenadosPorPrecio();
            return ResponseEntity.ok(productosOrdenados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener productos ordenados por precio.");
        }
    }

    @Operation(summary = "Obtener los primeros 3 productos ordenados por precio (de mayor a menor)")
    @GetMapping("/getTop3Expensive")
    public ResponseEntity<?> obtenerTop3ProductosOrdenadosPorPrecio() {
        try {
            List<Producto> productosOrdenados = productoService.obtenerProductosOrdenadosPorPrecio();

            // Obtener solo los primeros 3 productos
            List<Producto> top3Productos = productosOrdenados.stream().limit(3).collect(Collectors.toList());

            return ResponseEntity.ok(top3Productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los primeros 3 productos ordenados por precio.");
        }
    }

}

b. PersonaController
Este controlador maneja las operaciones relacionadas con las personas. Incluye endpoints para la creación, obtención, actualización, eliminación y listado de personas. También incluye operaciones para gestionar la lista de compra de un usuario.

java
Copy code
package ShopAll.com.Controller;

// Importaciones omitidas para mayor claridad

@RestController // Marca la clase como un controlador REST
@RequestMapping("/personas") // Define la raíz de URL para todas las rutas manejadas por este controlador
@Tag(name = "Usuarios") // Etiqueta para documentación Swagger
@CrossOrigin(origins="*") //Permite que una API REST sea accesible desde cualquier origen
//@Validated// Valida las restricciones
public class PersonaController {
    @Autowired // Realiza la inyección automática de la dependencia UserService
    private PersonaService personaService;

    // Crea un nuevo usuario
    @Operation(summary = "Creacion de nueva persona") // Descripción de la operación para Swagger
    @PostMapping("/crearPersona") // Mapeo para manejar solicitudes POST en la ruta /users/create
    public Persona crearPersona(@RequestBody Persona persona){
        return personaService.createPersona(persona);
    }

    // Buscar persona por id
    @Operation(summary = "Obtener persona con su ID")
    @GetMapping("/{id}") // Mapeo para manejar solicitudes GET en la ruta /users/{id}
    public Persona obtenerPersona(@PathVariable Long id){
        if (id >= 0){
            return personaService.obtenerPersona(id);
        } else {
            throw new UserNotFoundException("El usuario no se encontró con el ID: " + id);
        }
    }

    @Operation(summary = "Eliminar persona por id")
    @DeleteMapping("/{id}") // Mapeo para manejar solicitudes DELETE en la ruta /users/{id}
    public void eliminarPersona(@PathVariable Long id){personaService.eliminarPersona(id);}
    @Operation(summary = "Actualizar persona por ID") // Descripción de la operación para Swagger
    @PutMapping("/{id}") // Mapeo para manejar solicitudes PUT en la ruta /users/{id}
    public Persona updateUser(@PathVariable Long id, @RequestBody Persona nuevaPersona) {
        return personaService.actualizarPersona(id, nuevaPersona);
    }
    @Operation(summary = "Obtener listado de personas")
    @GetMapping("/listarPersonas")
    public List<Persona> listarPersonas(){return personaService.listarPersonas();}
    @Operation(summary = "Obteneomos lista de compra con el id")
    @GetMapping("/{id}/shop_list")
    public ResponseEntity<?> getUserShopList(@PathVariable Long id){
        Persona user = personaService.getUser(id);
        if(user != null){
            List<Producto> productos = user.getListaCompra();
            return ResponseEntity.ok(productos);
        } else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @Operation(summary = "Agregar producto a la lista de compra del usuario por ID")
    @PostMapping("/{id}/agregarProducto")
    public ResponseEntity<?> agregarProductoAListaCompra(@PathVariable Long id, @RequestBody Producto nuevoProducto) {
        try {
            personaService.agregarProductoAListaCompra(id, nuevoProducto);
            return ResponseEntity.ok("Producto agregado a la lista de compra exitosamente.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar el producto a la lista de compra.");
        }
    }
}
c. HomeController
Este controlador gestiona las páginas de inicio, inicio de sesión y páginas relacionadas con la seguridad de la aplicación.

java
Copy code
package ShopAll.com.Controller;

// Importaciones omitidas para mayor claridad


@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "public";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password) {
        return "redirect:/E-commers";
    }

    @GetMapping("/private")
    public String privatePage() {
        return "private";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/login-success")
    public String loginSuccessPage() {
        return "login-success";
    }

    @GetMapping("/login-error")
    public String loginFailurePage() {
        return "login-error";
    }
    @GetMapping("/crudPersona")
    public String crudPersona() {
        return "personaCRUD";
    }
    @GetMapping("/e-commers")
    public String e_commers() {
        return "E-commers";
    }
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
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()  // Rutas estáticas permitidas para todos
                                .requestMatchers("/login-success").authenticated()
                                .requestMatchers("/login-error").permitAll()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .successHandler(loginSuccessHandler())
                                .failureHandler(loginFailureHandler())
                                .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            // Redirige a la página de éxito después del login
            response.sendRedirect("/login-success");
        };
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            // Redirige a la página de error después del login fallido
            response.sendRedirect("/login-error");
        };
    }
}

Tecnologías Utilizadas
Backend: Spring Boot
Frontend: Thymeleaf
Base de Datos: Configurable (ver archivo application.properties o application.yml)
Seguridad: Spring Security
Otras Tecnologías: Jacoco, Lombok, JUnit, Mockito, Swagger
