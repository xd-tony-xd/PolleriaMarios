package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.Contacto;
import java.util.List;
import java.util.Optional;

public interface ContactoService {
    List<Contacto> listar();
    Optional<Contacto> buscarPorId(Integer id);
    Contacto guardar(Contacto contacto);
    void eliminar(Integer id);
}
