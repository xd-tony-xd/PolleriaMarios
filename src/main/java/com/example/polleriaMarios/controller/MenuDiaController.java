package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.entity.MenuDia;
import com.example.polleriaMarios.service.MenuDiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
@CrossOrigin(origins = "*")
public class MenuDiaController {

    @Autowired
    private MenuDiaService menuDiaService;

    @GetMapping
    public List<MenuDia> listar() {
        return menuDiaService.listar();
    }

    @GetMapping("/{id}")
    public MenuDia buscarPorId(@PathVariable Integer id) {
        return menuDiaService.buscarPorId(id).orElse(null);
    }

    @GetMapping("/fecha/{fecha}")
    public List<MenuDia> buscarPorFecha(@PathVariable String fecha) {
        return menuDiaService.buscarPorFecha(LocalDate.parse(fecha));
    }

    @GetMapping("/horario/{idHorario}")
    public List<MenuDia> buscarPorHorario(@PathVariable Integer idHorario) {
        return menuDiaService.buscarPorHorario(idHorario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public MenuDia guardar(@RequestBody MenuDia menu) {
        return menuDiaService.guardar(menu);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public MenuDia editar(@PathVariable Integer id, @RequestBody MenuDia menu) {
        menu.setId(id);
        return menuDiaService.editar(menu);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        menuDiaService.eliminar(id);
    }
}
