package com.memorybottle.memory_app.repository;

import com.memorybottle.memory_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {}
