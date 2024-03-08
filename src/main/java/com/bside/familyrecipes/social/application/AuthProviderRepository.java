package com.bside.familyrecipes.social.application;

import com.bside.familyrecipes.social.domain.AuthProvider;
import com.bside.familyrecipes.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, String> {
    AuthProvider searchAuthProviderById(String id);
    Optional<AuthProvider> searchAuthProviderByUser(User user);
}
