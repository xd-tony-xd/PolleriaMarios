package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.Publicidad;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PublicidadService {
    List<Publicidad> listar();
    Optional<Publicidad> buscarPorId(Integer id);
    List<Publicidad> buscarPorHorario(Integer idHorario);

    // Métodos para guardar/editar con imagen (MultipartFile)
    Publicidad guardar(Publicidad publicidad, MultipartFile imagen) throws IOException;
    Publicidad editar(Publicidad publicidad, MultipartFile imagen) throws IOException;

    void eliminar(Integer id);
}