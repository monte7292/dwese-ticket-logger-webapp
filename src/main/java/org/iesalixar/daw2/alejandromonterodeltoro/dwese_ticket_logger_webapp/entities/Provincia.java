package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Provincia {

    private Long id;

    private String code;

    private String name;

    public Provincia(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
