package fr.farmeurimmo.backenddemolocation.controllers.locations;

import fr.farmeurimmo.backenddemolocation.dtos.locations.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

}
