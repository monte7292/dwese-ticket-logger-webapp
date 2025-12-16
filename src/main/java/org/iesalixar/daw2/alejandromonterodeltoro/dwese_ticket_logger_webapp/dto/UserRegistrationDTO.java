package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {

    @NotEmpty(message = "{msg.user.username.notEmpty}")
    @Size(max = 50, message = "{msg.user.username.size}")
    private String username;

    @NotEmpty(message = "{msg.user.password.notEmpty}")
    @Size(min = 8, message = "{msg.user.password.size}")
    private String password;

    @NotEmpty(message = "{msg.user.password.notEmpty}")
    private String confirmPassword;

    @NotEmpty(message = "{msg.user.firstName.notEmpty}")
    @Size(max = 50, message = "{msg.user.firstName.size}")
    private String firstName;

    @NotEmpty(message = "{msg.user.lastName.notEmpty}")
    @Size(max = 50, message = "{msg.user.lastName.size}")
    private String lastName;

}
