package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.Plato;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PlatoService {
    List<Plato> listar();
    Optional<Plato> buscarPorId(Integer id);
    List<Plato> buscarPorHorario(Integer idHorario);
    List<Plato> buscarPorCategoria(Integer idCategoria);

    Plato guardar(Plato plato, MultipartFile imagenFile);
    Plato editar(Plato plato, MultipartFile imagenFile);

    // 🚨 Nuevo método para el toggle de disponibilidad
    Plato actualizarDisponibilidad(Integer id, boolean disponible);

    void eliminar(Integer id);
}