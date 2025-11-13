package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.Publicidad;
import java.util.List;
import java.util.Optional;

public interface PublicidadService {
    List<Publicidad> listar();
    Optional<Publicidad> buscarPorId(Integer id);
    List<Publicidad> buscarPorHorario(Integer idHorario);
    Publicidad guardar(Publicidad publicidad);
    Publicidad editar(Publicidad publicidad);
    void eliminar(Integer id);
}
