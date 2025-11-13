package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.entity.Horario;
import com.example.polleriaMarios.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public List<Horario> listar() {
        return horarioService.listar();
    }

    @GetMapping("/{id}")
    public Horario buscarPorId(@PathVariable Integer id) {
        return horarioService.buscarPorId(id).orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Horario guardar(@RequestBody Horario horario) {
        return horarioService.guardar(horario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Horario editar(@PathVariable Integer id, @RequestBody Horario horario) {
        horario.setId(id);
        return horarioService.editar(horario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        horarioService.eliminar(id);
    }
}
