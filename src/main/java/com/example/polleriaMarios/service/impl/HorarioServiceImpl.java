package com.example.polleriaMarios.service.impl;

import com.example.polleriaMarios.entity.Horario;
import com.example.polleriaMarios.repository.HorarioRepository;
import com.example.polleriaMarios.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioServiceImpl implements HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public List<Horario> listar() {
        return horarioRepository.findAll();
    }

    @Override
    public Optional<Horario> buscarPorId(Integer id) {
        return horarioRepository.findById(id);
    }

    @Override
    public Horario guardar(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Override
    public Horario editar(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Override
    public void eliminar(Integer id) {
        horarioRepository.deleteById(id);
    }
}
