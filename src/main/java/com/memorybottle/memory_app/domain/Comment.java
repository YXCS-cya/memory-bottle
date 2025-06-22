package com.memorybottle.memory_app.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdTime = LocalDateTime.now();

    @ManyToOne
    private Memory memory;
}
