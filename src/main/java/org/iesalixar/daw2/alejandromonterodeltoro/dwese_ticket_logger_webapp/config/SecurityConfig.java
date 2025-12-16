package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.config;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity //Esto es habilitar la seguridad HTTP
//La anotación preautorize permite establecer a nivel de métod que
//roles pueden ejecutar ese métod.
@EnableMethodSecurity(prePostEnabled = true) // Activa la seguridad basada en métodos
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;


    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Entrando en el método securityFilterChain");// Configuración de seguridad
        http
                .authorizeHttpRequests(auth -> {
                    logger.debug("Configurando autorización de solicitudes HTTP");
                            auth
                                    .requestMatchers("/", "/hello").permitAll()
                                    .requestMatchers("/admin", "/provinces/**","/regions").hasRole("ADMIN")// Solo ADMIN
                                    .requestMatchers("/products").hasRole("MANAGER") // Solo MANAGER
                                    .requestMatchers("/tickets").hasRole("USER")
                                    .anyRequest().authenticated(); //Cualquier otra solicitud requiere autenticación
                })
                .formLogin(form -> {
                    logger.debug("Configurando formulario de inicio de sesión");
                    form
                            .loginPage("/login")              // Página personalizada de login
                            .defaultSuccessUrl("/")           // Redirige al inicio después del login
                            .failureUrl("/login?error=true")  // Si falla al iniciar sesion
                            .permitAll();                     // Permite acceso a la página de login a todos
                })
                .oauth2Login(oauth2 -> {
                    logger.debug("Configurando login con OAuth2");
                    oauth2
                            .loginPage("/login") // Reutiliza la página de inicio de sesión personalizada
                            .defaultSuccessUrl("/", true) // Redirige al inicio después del login exitoso con OAuth2
                            .permitAll();
                })
                .sessionManagement(session -> {
                    logger.debug("Configurando política de gestión de sesiones");
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); // Usa sesiones cuando sea necesario
                });
        logger.info("Saliendo del método securityFilterChain");
        return http.build();
    }

    //El @Bean que el meotod va aser invocado cuando el framework vea necesario,
    //Que no necesito crearlo ni mencionarlo yo
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        //Cargamos la clase del Autowired y le digo que será mi nuevos servicio de autentificacion
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //Y aquí le digo que me use la clase que hemos creaod
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Entrando en el método passwordEncoder");
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        logger.info("Saliendo del método passwordEncoder");
        return encoder;
    }
}