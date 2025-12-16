package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.dto.UserRegistrationDTO;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Role;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.User;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.repositories.RoleRepository;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("userRegistrationDTO") @Valid UserRegistrationDTO userDTO,
                               BindingResult bindingResult,
                               Model model,
                               Locale locale) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "msg.register.error.passwordMismatch", messageSource.getMessage("msg.register.error.passwordMismatch", null, locale));
            return "register";
        }

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "msg.register.error.usernameExists", messageSource.getMessage("msg.register.error.usernameExists", null, locale));
            return "register";
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEnabled(true);
        
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));

        userRepository.save(user);

        return "redirect:/login?registered";
    }
}
