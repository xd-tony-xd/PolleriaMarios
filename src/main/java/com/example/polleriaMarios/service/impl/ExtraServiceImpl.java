package com.example.polleriaMarios.service.impl;

import com.example.polleriaMarios.entity.Extra;
import com.example.polleriaMarios.repository.ExtraRepository;
import com.example.polleriaMarios.service.ExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ExtraServiceImpl implements ExtraService {

    @Autowired
    private ExtraRepository extraRepository;

    // Ruta de subida de archivos (Debe coincidir con la configuración de WebConfig)
    private final Path UPLOAD_DIR = Paths.get("./uploads").toAbsolutePath().normalize();

    @Override
    public List<Extra> listar() {
        return extraRepository.findAll();
    }

    @Override
    public Optional<Extra> buscarPorId(Integer id) {
        return extraRepository.findById(id);
    }

    // ==============================================================================
    // 🚨 IMPLEMENTACIÓN GUARDAR (Maneja archivo o URL)
    // ==============================================================================
    @Override
    public Extra guardar(String nombre, String descripcion, Double precio, Boolean disponible, MultipartFile imagen, String imagenUrl) {
        Extra extra = new Extra();
        extra.setNombre(nombre);
        extra.setDescripcion(descripcion);
        extra.setPrecio(precio);
        extra.setDisponible(disponible);

        // 1. Priorizar la imagen subida
        if (imagen != null && !imagen.isEmpty()) {
            try {
                // Lógica de guardado local
                extra.setImagen(guardarImagenLocal(imagen)); // Guarda /nombre_archivo.jpg
            } catch (IOException e) {
                // Manejo de error de IO
                throw new RuntimeException("Error al guardar la imagen localmente.", e);
            }
        }
        // 2. Si no hay archivo, usar la URL externa si existe
        else if (imagenUrl != null && !imagenUrl.isEmpty()) {
            extra.setImagen(imagenUrl); // Guarda la URL de Shutterstock
        } else {
            // Manejar la falta de imagen/URL si es necesario (ej: placeholder)
            extra.setImagen(null);
        }

        return extraRepository.save(extra);
    }

    // ==============================================================================
    // 🚨 IMPLEMENTACIÓN EDITAR (Maneja nueva imagen, URL o mantiene la existente)
    // ==============================================================================
    @Override
    public Extra editar(Integer id, String nombre, String descripcion, Double precio, Boolean disponible, MultipartFile nuevaImagen, String imagenUrl) {
        return extraRepository.findById(id).map(extraExistente -> {
            extraExistente.setNombre(nombre);
            extraExistente.setDescripcion(descripcion);
            extraExistente.setPrecio(precio);
            extraExistente.setDisponible(disponible);

            // 1. Priorizar la NUEVA imagen subida
            if (nuevaImagen != null && !nuevaImagen.isEmpty()) {
                try {
                    extraExistente.setImagen(guardarImagenLocal(nuevaImagen));
                } catch (IOException e) {
                    throw new RuntimeException("Error al guardar la nueva imagen localmente.", e);
                }
            }
            // 2. Si no hay nuevo archivo, usar la URL externa proporcionada (si se cambió)
            else if (imagenUrl != null) {
                // Si la URL es una cadena vacía, se borra la imagen
                extraExistente.setImagen(imagenUrl.isEmpty() ? null : imagenUrl);
            }
            // 3. Si ambos son nulos/vacíos, la URL de imagen existente se mantiene.

            return extraRepository.save(extraExistente);
        }).orElseThrow(() -> new RuntimeException("Extra no encontrado con ID: " + id));
    }


    @Override
    public void eliminar(Integer id) {
        // Opcional: Agregar lógica para borrar el archivo físico si es local.
        extraRepository.deleteById(id);
    }

    // ==============================================================================
    // 🚨 MÉTODO PRIVADO PARA GUARDAR ARCHIVO EN DISCO
    // ==============================================================================
    private String guardarImagenLocal(MultipartFile imagen) throws IOException {
        if (Files.notExists(UPLOAD_DIR)) {
            Files.createDirectories(UPLOAD_DIR);
        }

        // Crear nombre de archivo único si es necesario, pero usaremos el original por simplicidad
        String fileName = imagen.getOriginalFilename();
        Path filePath = UPLOAD_DIR.resolve(fileName);

        // Guardar el archivo físicamente, sobrescribiendo si existe
        Files.copy(imagen.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // Devolvemos la URL mínima que Spring Boot mapea al directorio físico
        return "/" + fileName; // Ejemplo: /nombre_archivo.jpg
    }
}