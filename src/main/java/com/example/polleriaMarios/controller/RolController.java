package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.entity.Rol;
import com.example.polleriaMarios.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolService rolService;

    // 🔒 Solo ADMIN puede listar roles
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Rol> listar() {
        return rolService.listar();
    }

    // 🔒 Solo ADMIN puede crear roles
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Rol> guardar(@RequestBody Rol rol) {
        Rol nuevoRol = rolService.guardar(rol);
        return ResponseEntity.created(URI.create("/api/roles/" + nuevoRol.getId()))
                .body(nuevoRol);
    }
}
