package com.memorybottle.memory_app.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//标记成自增主键
    private Integer id;

    private String name;

    private String relation;

    private Boolean isAdmin = false;
}
