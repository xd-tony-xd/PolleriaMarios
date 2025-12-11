package com.example.polleriaMarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // <--- Agregamos NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor // Opcional, pero útil
public class LoginResponse {

    // JWT
    private String token;
    private String tokenType = "Bearer";

    // Datos del Usuario
    private String email;
    private String nombre;
    private String rol; // Solo el nombre del rol (e.g., "ADMIN", "USER")
}