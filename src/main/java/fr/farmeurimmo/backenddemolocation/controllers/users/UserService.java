package fr.farmeurimmo.backenddemolocation.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User updateUser(UUID uuid, User updatedUser) {
        return userRepository.findById(uuid).map(user -> {
            user.setLastName(updatedUser.getLastName());
            user.setFirstName(updatedUser.getFirstName());
            user.setEmail(updatedUser.getEmail());
            user.setHashedPassword(updatedUser.getHashedPassword());
            user.setApiKey(updatedUser.getApiKey());
            user.setRole(updatedUser.getRole());
            user.setUpdatedAt(System.currentTimeMillis());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    @Transactional
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Optional<User> getUserByApiKey(String apiKey) {
        return userRepository.findByApiKey(apiKey);
    }
}
