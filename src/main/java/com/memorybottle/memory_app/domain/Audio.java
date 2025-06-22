package com.memorybottle.memory_app.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String audioUrl;

    private LocalDateTime uploadedTime = LocalDateTime.now();

    @OneToOne
    private Memory memory;
}
