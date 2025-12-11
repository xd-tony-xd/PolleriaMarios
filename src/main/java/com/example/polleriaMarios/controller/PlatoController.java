package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.entity.Plato;
import com.example.polleriaMarios.entity.Categoria;
import com.example.polleriaMarios.entity.Horario;
import com.example.polleriaMarios.service.PlatoService;
import com.fasterxml.jackson.databind.ObjectMapper; // 🚨 NECESARIO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/platos")
@CrossOrigin(origins = "*") // Asumiendo que tienes CORS
public class PlatoController {

    @Autowired
    private PlatoService platoService;

    private final ObjectMapper objectMapper = new ObjectMapper(); // 🚨 Objeto para parsear JSON

    // ... (Métodos GET - sin cambios) ...

    @GetMapping
    public ResponseEntity<List<Plato>> listar() {
        return new ResponseEntity<>(platoService.listar(), HttpStatus.OK);
    }

    // ... otros GETs ...

    // ===============================================
    // 🚨 CORRECCIÓN CLAVE: MÉTODOS DE ESCRITURA (Usando "data" y ObjectMapper)
    // ===============================================

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Plato> guardar(
            // 🚨 CAMBIADO: Usar "data" como clave para el JSON, como en MenuDia
            @RequestPart("data") String platoJson,
            @RequestPart(value = "imagenFile", required = false) MultipartFile imagenFile) throws Exception {

        Plato plato = objectMapper.readValue(platoJson, Plato.class);

        // 🚨 Manejo manual de las relaciones (Categoria y Horario)
        // Esto es necesario porque el JSON en Angular tiene los IDs de las relaciones.

        // 1. Manejar Categoria
        Integer idCategoria = objectMapper.readTree(platoJson).get("categoria").get("id").asInt();
        plato.setCategoria(new Categoria());
        plato.getCategoria().setId(idCategoria);

        // 2. Manejar Horario
        Integer idHorario = objectMapper.readTree(platoJson).get("horario").get("id").asInt();
        plato.setHorario(new Horario());
        plato.getHorario().setId(idHorario);

        try {
            Plato platoGuardado = platoService.guardar(plato, imagenFile);
            return new ResponseEntity<>(platoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al guardar plato: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Devolvemos 400 en caso de falla interna
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Plato> editar(
            @PathVariable Integer id,
            // 🚨 CAMBIADO: Usar "data" como clave para el JSON, como en MenuDia
            @RequestPart("data") String platoJson,
            @RequestPart(value = "imagenFile", required = false) MultipartFile imagenFile) throws Exception {

        Plato plato = objectMapper.readValue(platoJson, Plato.class);
        plato.setId(id);

        // 🚨 Manejo manual de las relaciones (Categoria y Horario)
        // 1. Manejar Categoria
        Integer idCategoria = objectMapper.readTree(platoJson).get("categoria").get("id").asInt();
        plato.setCategoria(new Categoria());
        plato.getCategoria().setId(idCategoria);

        // 2. Manejar Horario
        Integer idHorario = objectMapper.readTree(platoJson).get("horario").get("id").asInt();
        plato.setHorario(new Horario());
        plato.getHorario().setId(idHorario);

        try {
            Plato platoActualizado = platoService.editar(plato, imagenFile);
            return new ResponseEntity<>(platoActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Error al editar plato: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===============================================
    // ENDPOINT PARA EL TOGGLE DE DISPONIBILIDAD (Sin cambios)
    // ===============================================

    @PutMapping("/{id}/disponibilidad")
    public ResponseEntity<Plato> cambiarDisponibilidad(
            @PathVariable Integer id,
            @RequestBody boolean disponible) {

        try {
            Plato platoActualizado = platoService.actualizarDisponibilidad(id, disponible);
            return new ResponseEntity<>(platoActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        platoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}