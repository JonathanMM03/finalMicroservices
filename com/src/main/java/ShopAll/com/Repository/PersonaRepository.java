package ShopAll.com.Repository;

import ShopAll.com.Entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    //Persona buscarPorEmail(String email);
}

