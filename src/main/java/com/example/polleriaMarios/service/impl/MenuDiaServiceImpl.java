package com.example.polleriaMarios.service.impl;

import com.example.polleriaMarios.entity.MenuDia;
import com.example.polleriaMarios.repository.MenuDiaRepository;
import com.example.polleriaMarios.service.MenuDiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MenuDiaServiceImpl implements MenuDiaService {

    @Autowired
    private MenuDiaRepository menuDiaRepository;

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
    public MenuDia guardar(MenuDia menu) {
        return menuDiaRepository.save(menu);
    }

    @Override
    public MenuDia editar(MenuDia menu) {
        return menuDiaRepository.save(menu);
    }

    @Override
    public void eliminar(Integer id) {
        menuDiaRepository.deleteById(id);
    }
}
