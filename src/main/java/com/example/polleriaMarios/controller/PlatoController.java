package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.entity.Plato;
import com.example.polleriaMarios.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/platos")
@CrossOrigin(origins = "*")
public class PlatoController {

    @Autowired
    private PlatoService platoService;

    // 👀 Todos pueden ver platos
    @GetMapping
    public List<Plato> listar() {
        return platoService.listar();
    }

    // 👀 Todos pueden buscar por id
    @GetMapping("/{id}")
    public Optional<Plato> buscarPorId(@PathVariable Integer id) {
        return platoService.buscarPorId(id);
    }

    // 👀 Todos pueden ver por categoría
    @GetMapping("/categoria/{idCategoria}")
    public List<Plato> buscarPorCategoria(@PathVariable Integer idCategoria) {
        return platoService.buscarPorCategoria(idCategoria);
    }

    // 👀 Todos pueden ver por horario
    @GetMapping("/horario/{idHorario}")
    public List<Plato> buscarPorHorario(@PathVariable Integer idHorario) {
        return platoService.buscarPorHorario(idHorario);
    }

    // 🔒 Solo ADMIN puede crear
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Plato guardar(@RequestBody Plato plato) {
        return platoService.guardar(plato);
    }

    // 🔒 Solo ADMIN puede editar
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Plato editar(@PathVariable Integer id, @RequestBody Plato plato) {
        plato.setId(id);
        return platoService.editar(plato);
    }

    // 🔒 Solo ADMIN puede eliminar
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        platoService.eliminar(id);
    }
}
