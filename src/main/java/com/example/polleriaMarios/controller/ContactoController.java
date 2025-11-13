package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.entity.Contacto;
import com.example.polleriaMarios.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@CrossOrigin(origins = "*")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @GetMapping
    public List<Contacto> listar() {
        return contactoService.listar();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Contacto guardar(@RequestBody Contacto contacto) {
        return contactoService.guardar(contacto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        contactoService.eliminar(id);
    }
}
