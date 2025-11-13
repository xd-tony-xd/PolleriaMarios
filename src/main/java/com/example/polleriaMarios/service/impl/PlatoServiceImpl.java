package com.example.polleriaMarios.service.impl;

import com.example.polleriaMarios.entity.Plato;
import com.example.polleriaMarios.repository.PlatoRepository;
import com.example.polleriaMarios.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoServiceImpl implements PlatoService {

    @Autowired
    private PlatoRepository platoRepository;

    @Override
    public List<Plato> listar() {
        return platoRepository.findAll();
    }

    @Override
    public Optional<Plato> buscarPorId(Integer id) {
        return platoRepository.findById(id);
    }

    @Override
    public List<Plato> buscarPorHorario(Integer idHorario) {
        return platoRepository.findByHorarioId(idHorario);
    }

    @Override
    public List<Plato> buscarPorCategoria(Integer idCategoria) {
        return platoRepository.findByCategoriaId(idCategoria) ;
    }

    @Override
    public Plato guardar(Plato plato) {
        return platoRepository.save(plato);
    }

    @Override
    public Plato editar(Plato plato) {
        return platoRepository.save(plato);
    }

    @Override
    public void eliminar(Integer id) {
        platoRepository.deleteById(id);
    }
}
