package com.bside.familyrecipes.users.application;

import com.bside.familyrecipes.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserById(Long userId);
}
