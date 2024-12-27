package fr.farmeurimmo.backenddemolocation.controllers.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "hashed_password", nullable = false)
    @JsonIgnore
    private String hashedPassword;

    @Column(name = "api_key", nullable = false)
    private String apiKey;

    @Column(name = "role", nullable = false)
    private int role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private long createdAt;

    @Column(name = "updated_at", nullable = false)
    private long updatedAt;

    public User(String lastName, String firstName, String email, String hashedPassword, String apiKey, int role, long createdAt, long updatedAt) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.apiKey = apiKey;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User() {
    }
}
