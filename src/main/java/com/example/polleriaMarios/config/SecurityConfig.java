package com.example.polleriaMarios.config;

import com.example.polleriaMarios.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                .csrf(AbstractHttpConfigurer::disable)

                // Configurar la política de sesión a STATELESS para JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        // Rutas de autenticación (Login) - PERMITIDAS PARA TODOS
                        .requestMatchers("/api/auth/**").permitAll()

                        // =========================================================
                        // 🚨 REGLAS DE ADMINISTRACIÓN: REQUIEREN ROL ADMIN
                        // =========================================================

                        // 🟢 AGREGADO: Permisos para la GESTIÓN de USUARIOS (CRUD completo)
                        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")


                        // Permisos de MODIFICACIÓN para Categorías (POST, PUT, DELETE)
                        .requestMatchers(HttpMethod.POST, "/api/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").hasRole("ADMIN")

                        // Permisos de MODIFICACIÓN para Extras (POST, PUT, DELETE)
                        .requestMatchers(HttpMethod.POST, "/api/extras/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/extras/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/extras/**").hasRole("ADMIN")

                        // Permisos de MODIFICACIÓN para Horarios (POST, PUT, DELETE)
                        .requestMatchers(HttpMethod.POST, "/api/horarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/horarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/horarios/**").hasRole("ADMIN")

                        // Permisos de MODIFICACIÓN para Platos (POST, PUT, DELETE)
                        .requestMatchers(HttpMethod.POST, "/api/platos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/platos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/platos/**").hasRole("ADMIN")

                        // Permisos de MODIFICACIÓN para MENUS (POST, PUT, DELETE)
                        .requestMatchers(HttpMethod.POST, "/api/menus/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/menus/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/menus/**").hasRole("ADMIN")

                        // Permisos de MODIFICACIÓN para PUBLICIDAD (POST, PUT, DELETE)
                        .requestMatchers(HttpMethod.POST, "/api/publicidad/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/publicidad/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/publicidad/**").hasRole("ADMIN")


                        // =========================================================
                        // 2. REGLAS PÚBLICAS (Todos pueden ver - GET)
                        // =========================================================
                        .requestMatchers(HttpMethod.GET, "/api/publicidad/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/platos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/horarios/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/menus/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/extras/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/contactos/**").permitAll()

                        // 3. REGLA FINAL: Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )

                // Agregar el filtro de JWT antes del filtro de autenticación por usuario/contraseña
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS global para Angular
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:4200","https://polleriamarios.com",
        "https://www.polleriamarios.com"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
