package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.Horario;
import java.util.List;
import java.util.Optional;

public interface HorarioService {
    List<Horario> listar();
    Optional<Horario> buscarPorId(Integer id);
    Horario guardar(Horario horario);
    Horario editar(Horario horario);
    void eliminar(Integer id);
}
