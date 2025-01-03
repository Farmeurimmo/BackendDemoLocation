package fr.farmeurimmo.backenddemolocation.controllers.locations;

import fr.farmeurimmo.backenddemolocation.controllers.users.UserService;
import fr.farmeurimmo.backenddemolocation.dtos.locations.CreateLocationDTO;
import fr.farmeurimmo.backenddemolocation.dtos.locations.Location;
import fr.farmeurimmo.backenddemolocation.dtos.users.User;
import fr.farmeurimmo.backenddemolocation.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createLocation(@RequestBody CreateLocationDTO newLocation) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUser.getUuid().equals(newLocation.userUuid())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to create a location for this user");
        }

        Optional<User> user = userService.getUserByUUID(newLocation.userUuid());

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.status(201).body(locationService.createLocation(newLocation));
    }

    @GetMapping(path = "/users/{uuid}")
    public ResponseEntity<?> getLocationByUuid(@PathVariable String uuid) {
        if (uuid == null) {
            return ResponseEntity.status(400).body("Invalid UUID");
        }

        Iterable<Location> locations = locationService.getLocationsByUserUUID(UuidUtils.convertHexToUUID(uuid));

        if (locations.iterator().hasNext()) {
            return ResponseEntity.status(200).body(locations);
        }

        return ResponseEntity.status(404).body("No location found");
    }
}
