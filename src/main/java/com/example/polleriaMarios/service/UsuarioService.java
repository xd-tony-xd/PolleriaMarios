package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<Usuario> buscarPorId(Integer id);
    Optional<Usuario> buscarPorEmail(String email);
    Usuario guardar(Usuario usuario);
    Usuario editar(Usuario usuario);
    void eliminar(Integer id);
}
