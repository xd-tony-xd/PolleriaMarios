package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.entity.Publicidad;
import com.example.polleriaMarios.service.PublicidadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/publicidad")
@CrossOrigin(origins = "*")
public class PublicidadController {

    @Autowired
    private PublicidadService publicidadService;

    @Autowired
    private ObjectMapper objectMapper;

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

    // 🔒 Solo ADMIN puede crear (Acepta FormData)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Publicidad guardar(
            @RequestPart("data") String publicidadJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) throws IOException {
        Publicidad publicidad = objectMapper.readValue(publicidadJson, Publicidad.class);
        return publicidadService.guardar(publicidad, imagen);
    }

    // 🔒 Solo ADMIN puede editar (Acepta FormData)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Publicidad editar(
            @PathVariable Integer id,
            @RequestPart("data") String publicidadJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) throws IOException {
        Publicidad publicidad = objectMapper.readValue(publicidadJson, Publicidad.class);
        publicidad.setId(id);
        return publicidadService.editar(publicidad, imagen);
    }

    // 🔒 Solo ADMIN puede eliminar
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        publicidadService.eliminar(id);
    }
}