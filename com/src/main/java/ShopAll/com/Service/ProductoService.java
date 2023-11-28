package ShopAll.com.Service;

import ShopAll.com.Entity.Persona;
import ShopAll.com.Entity.Producto;
import ShopAll.com.Exception.ProductErrorRegister;
import ShopAll.com.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto agregarProducto(Producto nuevoProducto) {
        if (nuevoProducto.getCosto() >= 100) {
            // Puedes realizar otras validaciones aquí según tus necesidades
            return productoRepository.save(nuevoProducto);
        } else {
            // Lanzar la excepción personalizada si el precio no cumple con el mínimo
            throw new ProductErrorRegister("El precio mínimo debe ser de 100");
        }
    }

    public Producto actualizarProducto(Long id, Producto nuevoProducto) {
        Producto productoExistente = productoRepository.findById(id).orElse(null);

        if (productoExistente != null) {
            productoExistente.setNombre(nuevoProducto.getNombre());
            productoExistente.setDescripcion(nuevoProducto.getDescripcion());
            productoExistente.setCosto(nuevoProducto.getCosto());

            return productoRepository.save(productoExistente);
        }

        return null;
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProducto(Long id){
        return productoRepository.findById(id).orElse(null);
    }

    public void eliminarProducto(Long id){
        productoRepository.deleteById(id);
    }

    public List<Producto> obtenerProductosOrdenadosPorPrecio() {
        List<Producto> productos = productoRepository.findAll();
        // Ordena la lista de productos por precio de manera descendente
        return productos.stream()
                .sorted((p1, p2) -> Double.compare(p2.getCosto(), p1.getCosto()))
                .collect(Collectors.toList());
    }
}