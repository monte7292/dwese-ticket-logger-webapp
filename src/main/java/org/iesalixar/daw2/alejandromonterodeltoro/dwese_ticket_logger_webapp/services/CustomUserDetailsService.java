package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.services;

import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.User;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import
        org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        // Convierte los roles de usuario en GrantedAuthority
        //Esto es crear un usuario con el username
        return
                org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        //Esto es lo que hace como roles, configuracion ect..
                        .authorities(user.getRoles().stream()
                                .map(role -> role.getName())
                                .collect(Collectors.toList())
                                .toArray(new String[0]))
                        //Si la cuenta expira, configuracion de cuentas
                        .accountExpired(false)
                        //Si la cuenta si está inabilitada
                        .accountLocked(false)
                        .credentialsExpired(false)
                        .disabled(!user.isEnabled())
                        //Y con esto creamos el usuario que vamos a crear
                        .build();
    }
}