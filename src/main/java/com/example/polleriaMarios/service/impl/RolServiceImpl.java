package com.example.polleriaMarios.service.impl;

import com.example.polleriaMarios.entity.Rol;
import com.example.polleriaMarios.repository.RolRepository;
import com.example.polleriaMarios.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> listar() {
        return rolRepository.findAll();
    }

    @Override
    public Optional<Rol> buscarPorId(Integer id) {
        return rolRepository.findById(id);
    }

    @Override
    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public Rol editar(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void eliminar(Integer id) {
        rolRepository.deleteById(id);
    }
}
