package fr.farmeurimmo.backenddemolocation.controllers.users;

import fr.farmeurimmo.backenddemolocation.dtos.users.CreateUserDTO;
import fr.farmeurimmo.backenddemolocation.dtos.users.User;
import fr.farmeurimmo.backenddemolocation.dtos.users.ValidateUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserService userService;

    public UUID convertHexToUUID(String hexString) {
        if (hexString.startsWith("0x")) {
            hexString = hexString.substring(2);
        }

        String formattedHex = hexString.replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                "$1-$2-$3-$4-$5"
        );

        return UUID.fromString(formattedHex);
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<?> getUserByUuid(@PathVariable String uuid) {
        if (uuid == null) {
            return ResponseEntity.status(400).body("Invalid UUID");
        }

        Optional<User> user = userService.getUserByUUID(convertHexToUUID(uuid));

        if (user.isPresent()) {
            return ResponseEntity.status(200).body(user.get());
        }

        return ResponseEntity.status(404).body("User not found");
    }

    @PutMapping(path = "/{uuid}")
    public User updateUser(@PathVariable String uuid, @RequestBody User updatedUser) {
        return userService.updateUser(convertHexToUUID(uuid), updatedUser);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO newUser) {
        String hashedPassword = passwordEncoder.encode(newUser.password());

        SecureRandom secureRandom = new SecureRandom();
        byte[] apiKeyBytes = new byte[64];
        secureRandom.nextBytes(apiKeyBytes);
        String apiKey = Base64.getUrlEncoder().withoutPadding().encodeToString(apiKeyBytes);

        if (userService.getUserByEmail(newUser.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        return ResponseEntity.status(200).body(userService.createUser(new User(newUser.lastName(), newUser.firstName(),
                newUser.email(), hashedPassword, apiKey, 0, System.currentTimeMillis(), System.currentTimeMillis())));
    }

    @PostMapping(value = "/validate")
    public ResponseEntity<?> validateUserPassword(@RequestBody ValidateUserDTO validateUserDTO) {
        Optional<User> user = userService.getUserByEmail(validateUserDTO.email());

        if (user.isPresent() && passwordEncoder.matches(validateUserDTO.password(), user.get().getHashedPassword())) {
            return ResponseEntity.status(200).body(user.get());
        }

        return ResponseEntity.status(401).body("Invalid email or password");
    }
}