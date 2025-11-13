package com.example.polleriaMarios.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "horario")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String turno; // Desayuno, Almuerzo, Pollería

    @Column(name = "hora_inicio", nullable = false)
    private java.time.LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private java.time.LocalTime horaFin;
}
