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

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "created_at", nullable = false, updatable = false)
    private long createdAt;

    @Column(name = "updated_at", nullable = false)
    private long updatedAt;

    public Location(UUID userUuid, String name, String description, String localization, String frontImage, String category, long createdAt, long updatedAt) {
        this.userUuid = userUuid;
        this.name = name;
        this.description = description;
        this.localization = localization;
        this.frontImage = frontImage;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Location() {
    }
}
