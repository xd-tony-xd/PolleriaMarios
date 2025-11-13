package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.entity.Extra;
import com.example.polleriaMarios.service.ExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Extra guardar(@RequestBody Extra extra) {
        return extraService.guardar(extra);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Extra editar(@PathVariable Integer id, @RequestBody Extra extra) {
        extra.setId(id);
        return extraService.editar(extra);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        extraService.eliminar(id);
    }
}
