package fr.farmeurimmo.backenddemolocation.controllers.users;

import fr.farmeurimmo.backenddemolocation.dtos.users.CreateUserDTO;
import fr.farmeurimmo.backenddemolocation.dtos.users.User;
import fr.farmeurimmo.backenddemolocation.dtos.users.ValidateUserDTO;
import fr.farmeurimmo.backenddemolocation.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserService userService;

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<?> getUserByUuid(@PathVariable String uuid) {
        if (uuid == null) {
            return ResponseEntity.status(400).body("Invalid UUID");
        }

        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUser.getUuid().equals(UuidUtils.convertHexToUUID(uuid))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to view this user");
        }

        Optional<User> user = userService.getUserByUUID(UuidUtils.convertHexToUUID(uuid));

        if (user.isPresent()) {
            return ResponseEntity.status(200).body(user.get());
        }

        return ResponseEntity.status(404).body("User not found");
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

    @PutMapping(path = "/{uuid}")
    public ResponseEntity<?> updateUser(@PathVariable String uuid, @RequestBody User updatedUser) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUser.getUuid().equals(UuidUtils.convertHexToUUID(uuid))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this user");
        }

        return ResponseEntity.ok(userService.updateUser(UuidUtils.convertHexToUUID(uuid), updatedUser));
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