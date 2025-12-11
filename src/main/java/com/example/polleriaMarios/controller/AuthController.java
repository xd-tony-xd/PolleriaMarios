package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.dto.LoginResponse;
import com.example.polleriaMarios.entity.Usuario;
import com.example.polleriaMarios.security.JwtUtil;
import com.example.polleriaMarios.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping("/login")
    // 🚨 El tipo de retorno ahora es LoginResponse
    public ResponseEntity<?> login(@RequestBody Usuario request) {
        try {
            // 1. Autenticación (Valida que el email y password sean correctos)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // 2. Buscar Usuario y obtener el Rol
            Usuario usuario = usuarioService.buscarPorEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String nombreRol = usuario.getRol().getNombre();
            // Agregar el prefijo Spring Security "ROLE_" para la generación del token
            String rolConPrefijo = "ROLE_" + nombreRol;

            // 3. Generar el Token
            String token = jwtUtil.generateToken(usuario.getEmail(), rolConPrefijo);

            // 4. Crear y devolver el DTO de respuesta completo
            LoginResponse response = new LoginResponse(
                    token,
                    "Bearer",
                    usuario.getEmail(),
                    usuario.getNombre(),
                    nombreRol // Solo el nombre del rol sin prefijo
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Manejar errores de autenticación
            System.err.println("Error de login: " + e.getMessage());
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}