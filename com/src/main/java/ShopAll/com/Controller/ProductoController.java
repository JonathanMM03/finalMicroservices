package ShopAll.com.Controller;

import ShopAll.com.Entity.Producto;
import ShopAll.com.Exception.ProductErrorRegister;
import ShopAll.com.Service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

