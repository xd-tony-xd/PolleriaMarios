package com.example.polleriaMarios.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "publicidad")
public class Publicidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
    private String descripcion;
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_horario")
    private Horario horario;

    private Boolean activo = true;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
