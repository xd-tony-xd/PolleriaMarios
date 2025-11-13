package com.example.polleriaMarios;

import com.example.polleriaMarios.entity.Rol;
import com.example.polleriaMarios.entity.Usuario;
import com.example.polleriaMarios.repository.RolRepository;
import com.example.polleriaMarios.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class PolleriaMariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolleriaMariosApplication.class, args);
    }

    @Bean
    CommandLineRunner crearUsuarioInicial(UsuarioRepository usuarioRepository,
                                          RolRepository rolRepository,
                                          PasswordEncoder passwordEncoder) {
        return args -> {
            // Revisar si el rol ADMIN existe, si no crearlo
            Rol rolAdmin = rolRepository.findByNombre("ADMIN")
                    .orElseGet(() -> rolRepository.save(new Rol(null, "ADMIN")));

            // Revisar si el usuario ya existe
            if (usuarioRepository.findByEmail("admin@polleria.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setEmail("admin@polleria.com");
                admin.setPassword(passwordEncoder.encode("admin123")); // 🔒 Contraseña encriptada
                admin.setRol(rolAdmin);
                admin.setActivo(true);
                admin.setFechaCreacion(LocalDateTime.now());

                usuarioRepository.save(admin);

                System.out.println("Usuario ADMIN creado con email: admin@polleria.com y password: admin123");
            }
        };
    }
}
