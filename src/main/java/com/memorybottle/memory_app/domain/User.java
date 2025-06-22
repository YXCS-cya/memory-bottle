package com.memorybottle.memory_app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//标记成自增主键
    private Integer id;

    private String name;

    private String relation;

    private Boolean isAdmin = false;
}
