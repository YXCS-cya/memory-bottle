package com.memorybottle.memory_app.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "memories")
public class Memory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime createdTime = LocalDateTime.now();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "memory", cascade = CascadeType.ALL)
    private List<MediaFile> mediaFiles;

    @OneToMany(mappedBy = "memory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TimelineEvent> timelineEvents;

}
