package com.example.polleriaMarios.repository;

import com.example.polleriaMarios.entity.MenuDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuDiaRepository extends JpaRepository<MenuDia, Integer> {
    List<MenuDia> findByFecha(LocalDate fecha);
    List<MenuDia> findByHorarioId(Integer idHorario);
}
