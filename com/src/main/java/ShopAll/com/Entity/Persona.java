package ShopAll.com.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.engine.internal.Cascade;

import java.util.List;

@Entity // Indica que esta clase es una entidad JPA, una tabla en base de datos
@Table(name = "usuario") // Define el nombre de la tabla en la base de datos
@Data // Genera automáticamente los métodos getter, setter, toString, equals, y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor
@ToString
public class Persona {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO) // Generación automática del valor de la clave primaria
    //@JsonIgnore
    private long id;

    @Column
    @NotBlank
    private String nombre;

    @Column
    private int edad;

    @Column
    @NotBlank
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario")
    @JsonIgnore
    private List<Producto> listaCompra;
}
