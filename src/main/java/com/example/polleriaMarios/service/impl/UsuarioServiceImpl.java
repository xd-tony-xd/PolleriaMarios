package com.example.polleriaMarios.service.impl;

import com.example.polleriaMarios.entity.Usuario;
import com.example.polleriaMarios.repository.UsuarioRepository;
import com.example.polleriaMarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Asegúrate de que este Bean esté disponible

    // =========================================================
    // IMPLEMENTACIONES CORREGIDAS
    // =========================================================

    // Método 'guardar' (Asume que aquí hasheas la contraseña al crear)
    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        // Nota: También debes manejar aquí la asignación de Rol si es necesario
        return usuarioRepository.save(usuario);
    }

    // 🟢 CLAVE DE LA CORRECCIÓN: Método 'editar'
    @Override
    @Transactional
    public Usuario editar(Usuario usuario) {
        // 1. Buscar el usuario existente en la DB
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado para edición: " + usuario.getId()));

        // 2. Actualizar campos
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setActivo(usuario.getActivo());

        // 3. Manejo de la contraseña (Soluciona el error 'password cannot be null')
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            // Se proporcionó una nueva contraseña, hashearla
            usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        // Si usuario.getPassword() es nulo o vacío, la contraseña existente
        // (usuarioExistente.getPassword()) se mantiene sin cambios.

        // 4. Manejo del Rol
        if (usuario.getRol() != null) {
            // Asumimos que el Rol tiene al menos el ID para poder actualizar la relación.
            usuarioExistente.setRol(usuario.getRol());
        }

        // 5. Guardar el usuario actualizado
        return usuarioRepository.save(usuarioExistente);
    }

    // =========================================================
    // MÉTODOS EXISTENTES
    // =========================================================

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }
}