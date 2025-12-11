package com.example.polleriaMarios.service.impl;

import com.example.polleriaMarios.entity.MenuDia;
import com.example.polleriaMarios.entity.Horario;
import com.example.polleriaMarios.repository.MenuDiaRepository;
import com.example.polleriaMarios.service.MenuDiaService;
import com.example.polleriaMarios.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional; // 🚨 Importación Recomendada si hay lógica de archivo

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MenuDiaServiceImpl implements MenuDiaService {

    @Autowired
    private MenuDiaRepository menuDiaRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public List<MenuDia> listar() {
        return menuDiaRepository.findAll();
    }

    @Override
    public Optional<MenuDia> buscarPorId(Integer id) {
        return menuDiaRepository.findById(id);
    }

    @Override
    public List<MenuDia> buscarPorHorario(Integer idHorario) {
        return menuDiaRepository.findByHorarioId(idHorario);
    }

    @Override
    public List<MenuDia> buscarPorFecha(LocalDate fecha) {
        return menuDiaRepository.findByFecha(fecha);
    }

    @Override
    public void eliminar(Integer id) {
        menuDiaRepository.deleteById(id);
    }

    // =========================================================================
    // Lógica de Soporte: Buscar Horario
    // =========================================================================

    private Horario buscarHorarioParaMenu(MenuDia menu) {
        if (menu.getHorario() != null && menu.getHorario().getId() != null) {
            return horarioRepository.findById(menu.getHorario().getId())
                    .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + menu.getHorario().getId()));
        }
        throw new RuntimeException("ID de Horario es requerido.");
    }

    // =========================================================================
    // IMPLEMENTACIÓN NUEVA PARA MANEJO DE IMAGEN/URL
    // =========================================================================

    @Override
    @Transactional
    public MenuDia guardarConImagen(MenuDia menu, MultipartFile imagen) {

        // 1. Asociar el Horario
        menu.setHorario(buscarHorarioParaMenu(menu));

        // 2. Manejar la Imagen
        if (imagen != null && !imagen.isEmpty()) {
            // SIMULACIÓN DE GUARDADO DE ARCHIVO:
            String path = "/uploads/menu/" + System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
            menu.setImagen(path);
        } else if (menu.getImagen() == null || menu.getImagen().isEmpty()) {
            // Si viene vacío (ni archivo ni URL), se asegura que sea null
            menu.setImagen(null);
        }

        return menuDiaRepository.save(menu);
    }

    @Override
    @Transactional
    public MenuDia editarConImagen(MenuDia menuActualizado, MultipartFile imagen) {

        MenuDia menuExistente = menuDiaRepository.findById(menuActualizado.getId())
                .orElseThrow(() -> new RuntimeException("Menú no encontrado con ID: " + menuActualizado.getId()));

        // 1. Asociar el Horario
        menuExistente.setHorario(buscarHorarioParaMenu(menuActualizado));

        // 2. Manejar la Imagen
        if (imagen != null && !imagen.isEmpty()) {
            // Se subió un nuevo archivo.
            // SIMULACIÓN DE GUARDADO DE ARCHIVO:
            String newPath = "/uploads/menu/" + System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
            menuExistente.setImagen(newPath);

        } else if (menuActualizado.getImagen() != null && !menuActualizado.getImagen().isEmpty()) {
            // No se subió archivo, pero viene una URL (o la URL existente)
            menuExistente.setImagen(menuActualizado.getImagen());

        } else {
            // Si el campo imagen en el formulario vino vacío, se borra la referencia
            menuExistente.setImagen(null);
        }

        // 3. Actualizar otros campos
        menuExistente.setFecha(menuActualizado.getFecha());
        menuExistente.setTitulo(menuActualizado.getTitulo());
        menuExistente.setDescripcion(menuActualizado.getDescripcion());
        menuExistente.setPrecio(menuActualizado.getPrecio());
        menuExistente.setDisponible(menuActualizado.getDisponible());

        return menuDiaRepository.save(menuExistente);
    }
}