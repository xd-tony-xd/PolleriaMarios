package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.entity.Publicidad;
import com.example.polleriaMarios.service.PublicidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicidad")
@CrossOrigin(origins = "*")
public class PublicidadController {

    @Autowired
    private PublicidadService publicidadService;

    // 👀 Cualquier usuario puede ver la lista
    @GetMapping
    public List<Publicidad> listar() {
        return publicidadService.listar();
    }

    // 👀 Cualquiera puede ver por horario
    @GetMapping("/horario/{idHorario}")
    public List<Publicidad> buscarPorHorario(@PathVariable Integer idHorario) {
        return publicidadService.buscarPorHorario(idHorario);
    }

    // 🔒 Solo ADMIN puede crear
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Publicidad guardar(@RequestBody Publicidad publicidad) {
        return publicidadService.guardar(publicidad);
    }

    // 🔒 Solo ADMIN puede editar
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Publicidad editar(@PathVariable Integer id, @RequestBody Publicidad publicidad) {
        publicidad.setId(id);
        return publicidadService.editar(publicidad);
    }

    // 🔒 Solo ADMIN puede eliminar
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        publicidadService.eliminar(id);
    }
}
