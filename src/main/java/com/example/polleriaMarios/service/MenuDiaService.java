package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.MenuDia;
import org.springframework.web.multipart.MultipartFile; // 🚨 Importación Necesaria
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuDiaService {
    List<MenuDia> listar();
    Optional<MenuDia> buscarPorId(Integer id);
    List<MenuDia> buscarPorHorario(Integer idHorario);
    List<MenuDia> buscarPorFecha(LocalDate fecha);

    // 🚨 Métodos actualizados para manejar imagen/archivo
    MenuDia guardarConImagen(MenuDia menu, MultipartFile imagen);
    MenuDia editarConImagen(MenuDia menu, MultipartFile imagen);

    void eliminar(Integer id);
}