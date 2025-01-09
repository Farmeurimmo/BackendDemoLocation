package fr.farmeurimmo.backenddemolocation.controllers.locations;

import fr.farmeurimmo.backenddemolocation.dtos.locations.CreateLocationDTO;
import fr.farmeurimmo.backenddemolocation.dtos.locations.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public Location updateLocation(UUID uuid, Location updatedLocation) {
        return locationRepository.findById(uuid).map(location -> {
            location.setName(updatedLocation.getName());
            location.setDescription(updatedLocation.getDescription());
            location.setLocalization(updatedLocation.getLocalization());
            location.setFrontImage(updatedLocation.getFrontImage());
            location.setCategory(updatedLocation.getCategory());
            location.setUpdatedAt(System.currentTimeMillis());
            return locationRepository.save(location);
        }).orElseThrow(() -> new RuntimeException("Location not found"));
    }

    @Transactional
    public Location createLocation(CreateLocationDTO newLocation) {
        Location location = new Location(
                newLocation.userUuid(),
                newLocation.name(),
                newLocation.description(),
                newLocation.localization(),
                newLocation.frontImage(),
                newLocation.category(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
        );

        return locationRepository.save(location);
    }

    @Transactional
    public Iterable<Location> getLocationsByUserUUID(UUID uuid) {
        return locationRepository.findAllByUserUuid(uuid);
    }

    @Transactional
    public Location getLocationByUUID(UUID uuid) {
        return locationRepository.findById(uuid).orElse(null);
    }

    @Transactional
    public void deleteLocation(UUID uuid) {
        locationRepository.deleteById(uuid);
    }
}
