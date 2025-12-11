package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities;

import jakarta.persistence.*; // Anotaciones de JPA
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Region;

import java.util.List;

/**
 * La clase `Province` representa una entidad que modela una provincia dentro de
 la base de datos.
 * Contiene cuatro campos: `id`, `code`, `name`, y `region`, donde `id` es el
 identificador único de la provincia,
 * `code` es un código asociado a la provincia, `name` es el nombre de la
 provincia, y `region` es la relación
 * con la entidad `Region`, representando la comunidad autónoma a la que
 pertenece la provincia.
 *
 * Las anotaciones de Lombok ayudan a reducir el código repetitivo al generar
 automáticamente
 * métodos comunes como getters, setters, constructores, y otros métodos estándar
 de los objetos.
 */
@Entity // Marca esta clase como una entidad JPA.
@Table(name = "provinces") // Define el nombre de la tabla asociada a esta entidad.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provincia {
    // Campo que almacena el identificador único de la provincia. Es autogeneradoy clave primaria.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Campo que almacena el código de la provincia, normalmente una cadena corta que identifica la provincia.
    // Ejemplo: "23" para Jaén.
    @NotEmpty(message = "{msg.provincia.code.notEmpty}")
    @Size(max = 2, message = "{msg.provincia.code.size}")
    @Column(name = "code", nullable = false, length = 2) // Define la columnacorrespondiente en la tabla.
    private String code;


    // Campo que almacena el nombre completo de la provincia, como "Sevilla" o"Jaén".
    @NotEmpty(message = "{msg.provincia.name.notEmpty}")
    @Size(max = 100, message = "{msg.provincia.name.size}")
    @Column(name = "name", nullable = false, length = 100) // Define la columna correspondiente en la tabla.
    private String name;


    // Relación con la entidad `Region`, representando la comunidad autónoma a la que pertenece la provincia.
    //@NotNull(message = "{msg.province.region.notNull}")
    //@ManyToOne(fetch = FetchType.LAZY) // Relación de muchas provincias a una región.
    //@JoinColumn(name = "region_id", nullable = false) // Clave foránea en la tabla provinces que referencia a la tabla regions.
    //private Region region;


    public Provincia(String code, String name) {
        this.code = code;
        this.name = name;
    }
}