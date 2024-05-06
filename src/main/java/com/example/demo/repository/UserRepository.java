package com.example.demo.repository;


import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    User findByUsername(String username);

    User findFirstByUsername(String username);
}
