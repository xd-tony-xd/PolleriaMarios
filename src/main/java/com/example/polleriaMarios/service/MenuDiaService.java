package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.MenuDia;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuDiaService {
    List<MenuDia> listar();
    Optional<MenuDia> buscarPorId(Integer id);
    List<MenuDia> buscarPorHorario(Integer idHorario);
    List<MenuDia> buscarPorFecha(LocalDate fecha);
    MenuDia guardar(MenuDia menu);
    MenuDia editar(MenuDia menu);
    void eliminar(Integer id);
}
