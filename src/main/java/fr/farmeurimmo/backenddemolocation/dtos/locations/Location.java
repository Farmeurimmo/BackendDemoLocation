package fr.farmeurimmo.backenddemolocation.dtos.locations;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "locations")
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "user_uuid", nullable = false, updatable = false)
    private UUID userUuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "localization", nullable = false)
    private String localization;

    @Column(name = "front_image", nullable = false)
    private String frontImage;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private long createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private long updatedAt;

    public Location(UUID userUuid, String name, String description, String localization, String frontImage, long createdAt, long updatedAt) {
        this.userUuid = userUuid;
        this.name = name;
        this.description = description;
        this.localization = localization;
        this.frontImage = frontImage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Location() {
    }
}
