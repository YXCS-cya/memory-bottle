package com.memorybottle.memory_app.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class MediaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    private LocalDateTime uploadedTime = LocalDateTime.now();

    @ManyToOne
    private Memory memory;
}
