package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.Extra;
import java.util.List;
import java.util.Optional;

public interface ExtraService {
    List<Extra> listar();
    Optional<Extra> buscarPorId(Integer id);
    Extra guardar(Extra extra);
    Extra editar(Extra extra);
    void eliminar(Integer id);
}
