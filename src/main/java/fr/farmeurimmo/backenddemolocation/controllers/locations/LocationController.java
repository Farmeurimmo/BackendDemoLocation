package fr.farmeurimmo.backenddemolocation.controllers.locations;

import fr.farmeurimmo.backenddemolocation.controllers.users.UserService;
import fr.farmeurimmo.backenddemolocation.dtos.locations.CreateLocationDTO;
import fr.farmeurimmo.backenddemolocation.dtos.locations.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Location createLocation(@RequestBody CreateLocationDTO newLocation) {
        //TODO: Check if user exists

        return locationService.createLocation(newLocation);
    }
}
