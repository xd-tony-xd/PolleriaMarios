package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.entity.Extra;
import com.example.polleriaMarios.service.ExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/extras")
@CrossOrigin(origins = "*")
public class ExtraController {

    @Autowired
    private ExtraService extraService;

    @GetMapping
    public List<Extra> listar() {
        return extraService.listar();
    }

    // ==============================================================================
    // 🚨 POST CORREGIDO: Imagen es opcional, imagenUrl también
    // ==============================================================================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Extra guardar(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("disponible") Boolean disponible,
            // El archivo es opcional (required = false)
            @RequestPart(value = "imagen", required = false) MultipartFile imagen,
            // La URL de texto también es opcional
            @RequestParam(value = "imagenUrl", required = false) String imagenUrl
    ) {
        return extraService.guardar(nombre, descripcion, precio, disponible, imagen, imagenUrl);
    }

    // ==============================================================================
    // 🚨 PUT CORREGIDO: Imagen y imagenUrl son opcionales
    // ==============================================================================
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Extra editar(
            @PathVariable Integer id,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("disponible") Boolean disponible,
            // El archivo es opcional
            @RequestPart(value = "imagen", required = false) MultipartFile imagen,
            // La URL de texto es opcional (se usa para actualizar la URL externa)
            @RequestParam(value = "imagenUrl", required = false) String imagenUrl
    ) {
        return extraService.editar(id, nombre, descripcion, precio, disponible, imagen, imagenUrl);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        extraService.eliminar(id);
    }
}