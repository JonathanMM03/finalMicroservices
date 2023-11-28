package ShopAll.com.PersonaController;

import ShopAll.com.Controller.ProductoController;
import ShopAll.com.Entity.Producto;
import ShopAll.com.Exception.ProductErrorRegister;
import ShopAll.com.Service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    private Producto producto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @BeforeEach
    public void configureProducto() {
        producto = new Producto();
    }

    @Test
    public void testGetSuccess() throws Exception {
        Long productoId = 1L;
        producto.setId(productoId);
        given(productoService.obtenerProducto(productoId)).willReturn(producto);
        mockMvc.perform(MockMvcRequestBuilders.get("/productos/{id}", productoId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetIdNotFound() throws Exception {
        Long productoId = 0L;
        given(productoService.obtenerProducto(productoId)).willReturn(producto);

        mockMvc.perform(MockMvcRequestBuilders.get("/productos/{id}", productoId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        // Configura el servicio para que devuelva un producto actualizado
        Producto productoActualizado = new Producto();
        productoActualizado.setId(5L);
        productoActualizado.setNombre("ProductoNuevo");
        productoActualizado.setDescripcion("Descripción nueva");
        productoActualizado.setCosto(75.0);

        when(productoService.actualizarProducto(eq(producto.getId()), any(Producto.class))).thenReturn(productoActualizado);

        // Realiza la solicitud de actualización en el controlador y espera una respuesta exitosa
        mockMvc.perform(MockMvcRequestBuilders.put("/productos/{id}", producto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"ProductoNuevo\", \"descripcion\": \"Descripción nueva\", \"costo\": 75.0 }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Producto actualizado exitosamente."));
    }


    @Test
    public void testDeleteFailed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/productos/{id}/eliminar", 4))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateSuccess() throws Exception {
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("NuevoProducto");
        nuevoProducto.setDescripcion("Descripción del nuevo producto");
        nuevoProducto.setCosto(120.0);

        given(productoService.agregarProducto(any(Producto.class))).willReturn(nuevoProducto);

        mockMvc.perform(MockMvcRequestBuilders.post("/productos/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(nuevoProducto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
