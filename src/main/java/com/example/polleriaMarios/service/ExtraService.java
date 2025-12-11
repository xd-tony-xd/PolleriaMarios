package com.example.polleriaMarios.service;

import com.example.polleriaMarios.entity.Extra;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

public interface ExtraService {
    List<Extra> listar();
    Optional<Extra> buscarPorId(Integer id);

    // 🚨 MODIFICADO: Añadido imagenUrl como String para URLs externas
    Extra guardar(String nombre, String descripcion, Double precio, Boolean disponible, MultipartFile imagen, String imagenUrl);

    // 🚨 MODIFICADO: Añadido imagenUrl como String para URLs externas
    Extra editar(Integer id, String nombre, String descripcion, Double precio, Boolean disponible, MultipartFile nuevaImagen, String imagenUrl);

    void eliminar(Integer id);
}