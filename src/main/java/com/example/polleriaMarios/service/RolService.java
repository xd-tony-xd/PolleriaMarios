package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.Rol;
import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> listar();
    Optional<Rol> buscarPorId(Integer id);
    Rol guardar(Rol rol);
    Rol editar(Rol rol);
    void eliminar(Integer id);
}
