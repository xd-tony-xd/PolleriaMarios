package com.example.polleriaMarios.controller;

import com.example.polleriaMarios.entity.Horario;
import com.example.polleriaMarios.entity.MenuDia;
import com.example.polleriaMarios.service.MenuDiaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
@CrossOrigin(origins = "*")
public class MenuDiaController {

    @Autowired
    private MenuDiaService menuDiaService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ... (Métodos GET) ...

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

    // ... (Métodos POST y PUT corregidos para FormData) ...

    @PostMapping
    public MenuDia guardar(
            @RequestPart("data") String menuDiaJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) throws Exception {

        MenuDia menu = objectMapper.readValue(menuDiaJson, MenuDia.class);

        if (menu.getHorario() == null) {
            Integer idHorario = objectMapper.readTree(menuDiaJson).get("horario").get("id").asInt();
            menu.setHorario(new Horario());
            menu.getHorario().setId(idHorario);
        }

        return menuDiaService.guardarConImagen(menu, imagen);
    }

    @PutMapping("/{id}")
    public MenuDia editar(
            @PathVariable Integer id,
            @RequestPart("data") String menuDiaJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) throws Exception {

        MenuDia menu = objectMapper.readValue(menuDiaJson, MenuDia.class);
        menu.setId(id);

        if (menu.getHorario() == null) {
            Integer idHorario = objectMapper.readTree(menuDiaJson).get("horario").get("id").asInt();
            menu.setHorario(new Horario());
            menu.getHorario().setId(idHorario);
        }

        return menuDiaService.editarConImagen(menu, imagen);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        menuDiaService.eliminar(id);
    }
}