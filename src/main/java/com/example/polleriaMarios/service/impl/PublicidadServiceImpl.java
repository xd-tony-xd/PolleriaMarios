package com.example.polleriaMarios.service.impl;

import com.example.polleriaMarios.entity.Publicidad;
import com.example.polleriaMarios.repository.PublicidadRepository;
import com.example.polleriaMarios.service.PublicidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PublicidadServiceImpl implements PublicidadService {

    @Autowired
    private PublicidadRepository publicidadRepository;

    // Ya no inyectamos AlmacenamientoService

    @Override
    public List<Publicidad> listar() {
        return publicidadRepository.findAll();
    }

    @Override
    public Optional<Publicidad> buscarPorId(Integer id) {
        return publicidadRepository.findById(id);
    }

    @Override
    public List<Publicidad> buscarPorHorario(Integer idHorario) {
        return publicidadRepository.findByHorarioId(idHorario);
    }

    @Override
    @Transactional
    public Publicidad guardar(Publicidad publicidad, MultipartFile imagen) throws IOException {

        // --- Lógica de Imagen para Guardar (Placeholder) ---
        if (imagen != null && !imagen.isEmpty()) {
            // 🚨 SIMULACIÓN: Asignamos una URL de placeholder usando el nombre del archivo
            publicidad.setImagen("/uploads/publicidad/" + imagen.getOriginalFilename());
        }
        // Si no hay archivo, se mantiene la URL externa si se envió en la entidad JSON

        return publicidadRepository.save(publicidad);
    }

    @Override
    @Transactional
    public Publicidad editar(Publicidad publicidad, MultipartFile imagen) throws IOException {
        Optional<Publicidad> publicidadExistenteOpt = publicidadRepository.findById(publicidad.getId());

        if (publicidadExistenteOpt.isEmpty()) {
            throw new RuntimeException("Publicidad no encontrada para editar: " + publicidad.getId());
        }

        Publicidad publicidadExistente = publicidadExistenteOpt.get();

        // 1. Actualizar campos de la entidad
        publicidadExistente.setTitulo(publicidad.getTitulo());
        publicidadExistente.setDescripcion(publicidad.getDescripcion());
        publicidadExistente.setActivo(publicidad.getActivo());
        publicidadExistente.setFechaInicio(publicidad.getFechaInicio());
        publicidadExistente.setFechaFin(publicidad.getFechaFin());
        publicidadExistente.setHorario(publicidad.getHorario());


        // 2. --- Lógica de Imagen para Editar (Placeholder) ---
        if (imagen != null && !imagen.isEmpty()) {
            // Se subió una NUEVA imagen
            // 🚨 SIMULACIÓN: Asignamos una URL de placeholder
            publicidadExistente.setImagen("/uploads/publicidad/" + imagen.getOriginalFilename());

        } else if (publicidad.getImagen() != null && !publicidad.getImagen().isEmpty()) {
            // No se subió archivo, pero el frontend envió la URL existente o URL externa
            publicidadExistente.setImagen(publicidad.getImagen());

        } else {
            // La imagen fue eliminada (se envió `imagen: null` desde Angular)
            publicidadExistente.setImagen(null);
        }

        return publicidadRepository.save(publicidadExistente);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        // En un servicio real, aquí iría la lógica para borrar el archivo físico.
        publicidadRepository.deleteById(id);
    }
}