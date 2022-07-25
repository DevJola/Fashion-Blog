package com.devjola.fashionblog.repository;

import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByRole(Role role);

}
