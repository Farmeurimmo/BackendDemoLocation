package fr.farmeurimmo.backenddemolocation.dtos.locations;

import java.util.UUID;

public record CreateLocationDTO(UUID userUuid, String name, String description, String localization,
                                String frontImage) {
}
