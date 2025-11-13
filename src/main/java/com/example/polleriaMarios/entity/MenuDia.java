package com.example.polleriaMarios.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_dia")
public class MenuDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_horario", nullable = false)
    private Horario horario;

    @Column(nullable = false, length = 100)
    private String titulo;

    private String descripcion;
    private String imagen;
    private Double precio;
    private Boolean disponible = true;
}
