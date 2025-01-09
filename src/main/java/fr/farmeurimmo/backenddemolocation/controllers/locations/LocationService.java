package fr.farmeurimmo.backenddemolocation.controllers.locations;

import fr.farmeurimmo.backenddemolocation.dtos.locations.CreateLocationDTO;
import fr.farmeurimmo.backenddemolocation.dtos.locations.Location;
import fr.farmeurimmo.backenddemolocation.dtos.locations.UpdatedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public Location updateLocation(UUID uuid, UpdatedLocation updatedLocation) {
        return locationRepository.findById(uuid).map(location -> {
            location.setName(updatedLocation.name());
            location.setDescription(updatedLocation.description());
            location.setLocalization(updatedLocation.localization());
            location.setFrontImage(updatedLocation.frontImage());
            location.setCategory(updatedLocation.category());
            location.setUpdatedAt(System.currentTimeMillis());
            return locationRepository.save(location);
        }).orElse(null);
    }

    @Transactional
    public Location createLocation(CreateLocationDTO newLocation) {
        return locationRepository.save(new Location(
                newLocation.userUuid(),
                newLocation.name(),
                newLocation.description(),
                newLocation.localization(),
                newLocation.frontImage(),
                newLocation.category(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
        ));
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
