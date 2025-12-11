    package com.example.polleriaMarios.service.impl;

    import com.example.polleriaMarios.entity.Plato;
    import com.example.polleriaMarios.repository.PlatoRepository;
    import com.example.polleriaMarios.service.PlatoService;
    // import com.example.polleriaMarios.service.IAlmacenamientoService; // Descomentar si tienes este servicio
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class PlatoServiceImpl implements PlatoService {

        @Autowired
        private PlatoRepository platoRepository;

        // @Autowired
        // private IAlmacenamientoService almacenamientoService; // Descomentar si tienes este servicio

        @Override
        public List<Plato> listar() {
            return platoRepository.findAll();
        }

        @Override
        public Optional<Plato> buscarPorId(Integer id) {
            return platoRepository.findById(id);
        }

        @Override
        public List<Plato> buscarPorHorario(Integer idHorario) {
            return platoRepository.findByHorarioId(idHorario);
        }

        @Override
        public List<Plato> buscarPorCategoria(Integer idCategoria) {
            return platoRepository.findByCategoriaId(idCategoria) ;
        }

        @Override
        @Transactional
        public Plato guardar(Plato plato, MultipartFile imagenFile) {
            // --- Lógica de Imagen para Guardar ---
            if (imagenFile != null && !imagenFile.isEmpty()) {
                // 🚨 Reemplaza esto con tu lógica real de guardado (almacenamientoService.guardarImagen):
                // plato.setImagen(almacenamientoService.guardarImagen(imagenFile, "platos"));
                plato.setImagen("/uploads/platos/" + imagenFile.getOriginalFilename());
            }

            return platoRepository.save(plato);
        }

        @Override
        @Transactional
        public Plato editar(Plato plato, MultipartFile imagenFile) {
            Optional<Plato> platoExistenteOpt = platoRepository.findById(plato.getId());

            if (platoExistenteOpt.isEmpty()) {
                throw new RuntimeException("Plato no encontrado para editar: " + plato.getId());
            }

            Plato platoExistente = platoExistenteOpt.get();

            // Actualizar campos de la entidad
            platoExistente.setNombre(plato.getNombre());
            platoExistente.setDescripcion(plato.getDescripcion());
            platoExistente.setPrecio(plato.getPrecio());
            platoExistente.setDisponible(plato.getDisponible());
            platoExistente.setCategoria(plato.getCategoria());
            platoExistente.setHorario(plato.getHorario());

            // --- Lógica de Imagen para Editar ---
            if (imagenFile != null && !imagenFile.isEmpty()) {
                // 1. Se subió una NUEVA imagen
                // 🚨 Reemplaza esto con tu lógica real de guardado:
                // platoExistente.setImagen(almacenamientoService.guardarImagen(imagenFile, "platos"));
                platoExistente.setImagen("/uploads/platos/" + imagenFile.getOriginalFilename());

            } else if (plato.getImagen() != null && !plato.getImagen().isEmpty()) {
                // 2. No se subió archivo, pero el frontend envió la URL existente
                platoExistente.setImagen(plato.getImagen());

            } else {
                // 3. La imagen fue eliminada o no se proporcionó
                platoExistente.setImagen(null);
            }

            return platoRepository.save(platoExistente);
        }

        @Override
        @Transactional
        public void eliminar(Integer id) {
            platoRepository.deleteById(id);
        }

        // 🚨 IMPLEMENTACIÓN DEL NUEVO MÉTODO
        @Override
        @Transactional
        public Plato actualizarDisponibilidad(Integer id, boolean disponible) {
            Optional<Plato> platoExistenteOpt = platoRepository.findById(id);

            if (platoExistenteOpt.isEmpty()) {
                throw new RuntimeException("Plato no encontrado: " + id);
            }

            Plato platoExistente = platoExistenteOpt.get();
            platoExistente.setDisponible(disponible);
            return platoRepository.save(platoExistente);
        }
    }