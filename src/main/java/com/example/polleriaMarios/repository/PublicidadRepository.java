package com.example.polleriaMarios.repository;

import com.example.polleriaMarios.entity.Publicidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicidadRepository extends JpaRepository<Publicidad, Integer> {
    List<Publicidad> findByActivoTrue();
    List<Publicidad> findByHorarioId(Integer idHorario);
}
