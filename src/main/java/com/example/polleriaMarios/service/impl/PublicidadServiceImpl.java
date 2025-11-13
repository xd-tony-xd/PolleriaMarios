package com.example.polleriaMarios.service.impl;

import com.example.polleriaMarios.entity.Publicidad;
import com.example.polleriaMarios.repository.PublicidadRepository;
import com.example.polleriaMarios.service.PublicidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicidadServiceImpl implements PublicidadService {

    @Autowired
    private PublicidadRepository publicidadRepository;

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
    public Publicidad guardar(Publicidad publicidad) {
        return publicidadRepository.save(publicidad);
    }

    @Override
    public Publicidad editar(Publicidad publicidad) {
        return publicidadRepository.save(publicidad);
    }

    @Override
    public void eliminar(Integer id) {
        publicidadRepository.deleteById(id);
    }
}
