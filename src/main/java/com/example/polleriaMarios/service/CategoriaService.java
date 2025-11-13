package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> listar();
    Optional<Categoria> buscarPorId(Integer id);
    Categoria guardar(Categoria categoria);
    Categoria editar(Categoria categoria);
    void eliminar(Integer id);
}
