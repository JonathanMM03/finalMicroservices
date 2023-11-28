package ShopAll.com.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // Indica que esta clase es una entidad JPA, una tabla en base de datos
@Table(name = "producto") // Define el nombre de la tabla en la base de datos
@Data // Genera automáticamente los métodos getter, setter, toString, equals, y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor
@ToString
public class Producto {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO) // Generación automática del valor de la clave primaria
    //@JsonIgnore
    private long id;

    @Column
    @NotBlank
    private String nombre;

    @Column
    private String descripcion;

    @Column
    @Min(value = 100,message = "El ´precio minimo es de $100")
    private Double costo;
}