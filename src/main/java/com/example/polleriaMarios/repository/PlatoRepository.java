package com.example.polleriaMarios.repository;

import com.example.polleriaMarios.entity.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {
    List<Plato> findByHorarioId(Integer idHorario);
    List<Plato> findByDisponibleTrue();

    List<Plato> findByCategoriaId(Integer categoriaId);
}
