package ShopAll.com.Controller;

import ShopAll.com.Entity.Persona;
import ShopAll.com.Entity.Producto;
import ShopAll.com.Exception.ProductErrorRegister;
import ShopAll.com.Exception.UserNotFoundException;
import ShopAll.com.Service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marca la clase como un controlador REST
@RequestMapping("/personas") // Define la raíz de URL para todas las rutas manejadas por este controlador
@Tag(name = "Usuarios") // Etiqueta para documentación Swagger
@CrossOrigin(origins="*") //Permite que una API REST sea accesible desde cualquier origen
//@Validated// Valida las restricciones
public class PersonaController {
    @GetMapping("/")
    public String welcome() {
        return "index"; // Devuelve el nombre de la plantilla sin la extensión (Thymeleaf asume ".html" por defecto)
    }
    @GetMapping("/crud")
    public String personaCrud() {
        return "personaCRUD"; // Devuelve el nombre de la plantilla sin la extensión (Thymeleaf asume ".html" por defecto)
    }
    @GetMapping("/error")
    public String errorPage() {
        return "error"; // Devuelve el nombre de la plantilla sin la extensión (Thymeleaf asume ".html" por defecto)
    }
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

    // Buscar persona por email
    /*@Operation(summary = "Obtener persona por email")
    @GetMapping("/buscarEmail")
    public Persona buscarPorEmail(@RequestParam String email){
        return personaService.buscarPorEmail(email);
    }*/
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
