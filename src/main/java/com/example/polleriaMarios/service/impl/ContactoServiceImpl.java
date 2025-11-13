package com.example.polleriaMarios.service.impl;

import com.example.polleriaMarios.entity.Contacto;
import com.example.polleriaMarios.repository.ContactoRepository;
import com.example.polleriaMarios.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactoServiceImpl implements ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    @Override
    public List<Contacto> listar() {
        return contactoRepository.findAll();
    }

    @Override
    public Optional<Contacto> buscarPorId(Integer id) {
        return contactoRepository.findById(id);
    }

    @Override
    public Contacto guardar(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    @Override
    public void eliminar(Integer id) {
        contactoRepository.deleteById(id);
    }
}
