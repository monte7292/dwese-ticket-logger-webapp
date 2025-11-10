package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Region {

    private Long id;

    // Ejemplo: "01" para Andalucía.
    @NotEmpty(message = "{msg.region.code.notEmpty}")
    @Size(max = 2, message = "{msg.region.code.size}")
    private String code;

    // Campo que almacena el nombre completo de la región, como "Andalucía" o "Cataluña".
    @NotEmpty(message = "{msg.region.name.notEmpty}")
    @Size(max = 100, message = "{msg.region.name.notEmpty}")

    private String name;

    public Region(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
