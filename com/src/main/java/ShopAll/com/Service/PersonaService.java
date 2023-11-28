package ShopAll.com.Service;

import ShopAll.com.Entity.Persona;
import ShopAll.com.Entity.Producto;
import ShopAll.com.Exception.UserNotFoundException;
import ShopAll.com.Repository.PersonaRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Indica que esta clase es un componente de servicio gestionado por Spring, lógica de negocio
public class PersonaService {

    @Autowired // Inyecta automáticamente una instancia de UserRepository en esta clase
    private PersonaRepository personaRepository;

    public Persona createPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    public Persona obtenerPersona(Long id){
        return personaRepository.findById(id).orElse(null);
    }
    //public Persona buscarPorEmail(String email){return personaRepository.buscarPorEmail(email);}
    public void eliminarPersona(Long id){personaRepository.deleteById(id);}
    public List<Persona> listarPersonas(){return personaRepository.findAll();}
    public Persona actualizarPersona(Long id, Persona nuevaPersona){
        // Busca un usuario existente por su ID
        Persona personaPrevia =personaRepository.findById(id).orElse(null);
        if (personaPrevia != null) {
            // Actualiza los campos del usuario existente con los valores del usuario actualizado
            personaPrevia = nuevaPersona;
            // Guarda y devuelve el usuario actualizado
            return personaRepository.save(personaPrevia);
        }
        // Devuelve null si no se encuentra el usuario con el ID proporcionado
        return null;
    }
    public Persona actualizarListaCompra(Long id, List<Producto> nuevaListaCompra) {
        // Busca una persona existente por su ID
        Persona personaExistente = personaRepository.findById(id).orElse(null);

        if (personaExistente != null) {
            // Actualiza solo la lista de compra con los valores de la nueva lista
            personaExistente.setListaCompra(nuevaListaCompra);

            // Guarda y devuelve la persona actualizada
            return personaRepository.save(personaExistente);
        }

        // Devuelve null si no se encuentra la persona con el ID proporcionado
        return null;
    }


    public Persona getUser(Long id) {
        return personaRepository.findById(id).orElse(null);
    }

    public void agregarProductoAListaCompra(Long personaId, Producto nuevoProducto) {
        Persona persona = personaRepository.findById(personaId).orElse(null);

        if (persona != null) {
            List<Producto> listaCompra = persona.getListaCompra();
            listaCompra.add(nuevoProducto);

            // Puedes realizar validaciones adicionales antes de agregar el producto a la lista
            // ...

            personaRepository.save(persona);
        } else {
            // Manejar el caso cuando la persona no se encuentra
            throw new UserNotFoundException("Persona no encontrada con ID: " + personaId);
        }
    }

    /*public Persona buscarPorEmail(String email){
        Persona persona = personaRepository.buscarPorEmail(email);

        if (persona != null){
            return persona;
        } else {
            throw new UserNotFoundException("No hay registro de ese email");
        }
    }*/
}
