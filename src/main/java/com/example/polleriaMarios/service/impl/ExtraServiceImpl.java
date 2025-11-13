package com.example.polleriaMarios.service.impl;

import com.example.polleriaMarios.entity.Extra;
import com.example.polleriaMarios.repository.ExtraRepository;
import com.example.polleriaMarios.service.ExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExtraServiceImpl implements ExtraService {

    @Autowired
    private ExtraRepository extraRepository;

    @Override
    public List<Extra> listar() {
        return extraRepository.findAll();
    }

    @Override
    public Optional<Extra> buscarPorId(Integer id) {
        return extraRepository.findById(id);
    }

    @Override
    public Extra guardar(Extra extra) {
        return extraRepository.save(extra);
    }

    @Override
    public Extra editar(Extra extra) {
        return extraRepository.save(extra);
    }

    @Override
    public void eliminar(Integer id) {
        extraRepository.deleteById(id);
    }
}
