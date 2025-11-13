package com.example.polleriaMarios.repository;

import com.example.polleriaMarios.entity.Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraRepository extends JpaRepository<Extra, Integer> {
    List<Extra> findByDisponibleTrue();
}
