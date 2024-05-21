package com.javateam.campProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.javateam.campProject.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
}
