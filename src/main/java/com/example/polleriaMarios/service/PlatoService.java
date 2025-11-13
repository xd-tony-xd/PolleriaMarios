package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.Plato;
import java.util.List;
import java.util.Optional;

public interface PlatoService {
    List<Plato> listar();
    Optional<Plato> buscarPorId(Integer id);
    List<Plato> buscarPorHorario(Integer idHorario);
    List<Plato> buscarPorCategoria(Integer idCategoria);
    Plato guardar(Plato plato);
    Plato editar(Plato plato);
    void eliminar(Integer id);
}
