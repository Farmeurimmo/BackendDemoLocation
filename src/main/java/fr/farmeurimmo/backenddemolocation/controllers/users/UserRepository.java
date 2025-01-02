package fr.farmeurimmo.backenddemolocation.controllers.users;

import fr.farmeurimmo.backenddemolocation.dtos.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByApiKey(String apiKey);
}
