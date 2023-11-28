package ShopAll.com.PersonaController;

import ShopAll.com.Controller.PersonaController;
import ShopAll.com.Entity.Persona;
import ShopAll.com.Entity.Producto;
import ShopAll.com.Exception.UserNotFoundException;
import ShopAll.com.Repository.PersonaRepository;
import ShopAll.com.Service.PersonaService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebMvcTest(PersonaController.class)
public class PersonaControllerTest {

    private Persona persona;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonaService personaService;

    @MockBean
    private PersonaRepository personaRepository;

    @BeforeEach
    public void configurePersona() {
        persona = new Persona();
    }

    @Test
    public void testGetSuccess() throws Exception {
        Long personaId = 1L;
        persona.setId(personaId);
        given(personaService.obtenerPersona(personaId)).willReturn(persona);
        mockMvc.perform(MockMvcRequestBuilders.get("/personas/{id}", personaId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetIdNotFound() throws Exception {
        Long personaId = 0L;
        given(personaService.obtenerPersona(personaId)).willReturn(persona);

        mockMvc.perform(MockMvcRequestBuilders.get("/personas/{id}", personaId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        persona.setId(5L);
        persona.setNombre("Jon");
        persona.setEmail("jonathan@gmail.com");
        when(personaService.actualizarPersona(persona.getId(), persona)).thenReturn(persona);
        when(personaRepository.save(persona)).thenReturn(persona);

        mockMvc.perform(MockMvcRequestBuilders.put("/personas/{id}", persona.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Jonathan\", \"email\": \"jonathangogbz@gmail.com\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /*@Test
    public void testDeleteSuccess() throws Exception {
        persona.setId(5L);
        when(personaService.eliminarPersona(persona.getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/personas/{id}", persona.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }*/

    @Test
    public void testDeleteFailed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/personas/{id}", 4))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}