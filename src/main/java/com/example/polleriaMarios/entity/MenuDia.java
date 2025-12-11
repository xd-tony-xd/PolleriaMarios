package com.example.polleriaMarios.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// Importaciones necesarias para Jackson:
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_dia")
public class MenuDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 🚨 MODIFICACIONES CLAVE AQUÍ:
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fecha; // Jackson ya sabrá cómo convertir el string '2025-12-10' a LocalDate

    @ManyToOne
    @JoinColumn(name = "id_horario", nullable = false)
    private Horario horario;

    // ... (El resto de la entidad es el mismo) ...
    @Column(nullable = false, length = 100)
    private String titulo;

    private String descripcion;
    private String imagen;
    private Double precio;
    private Boolean disponible = true;
}