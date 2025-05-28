package com.codedecode.restaurantlisting.controller;

import com.codedecode.restaurantlisting.dto.RestaurantDTO;
import com.codedecode.restaurantlisting.entity.Restaurant;
import com.codedecode.restaurantlisting.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin
/*
- This is a browser security feature that blocks frontend JavaScript code from making requests to a different origin (domain, port, or protocol) than the one it was loaded from. E.g. this service was loaded on http://localhost:9091 but can be requested from http://myservername:5432/restaurant/fetchAllRestaurants
- E.g. with postman we use http://localhost:9091/restaurant/fetchAllRestaurants. The service will not complain because the host and port are the same as defined by the service
- however if the request is coming from another service e.g. angular with host and port http://localhost:4200, then this service will not accept the request. To resolve that we need to add @CrossOrigin
- The browser sees that as a cross-origin request. Without CORS enabled on your backend, the browser blocks the request.
*/
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/fetchAllRestaurants")
    public ResponseEntity<List<RestaurantDTO>> fetchAllRestaurants() {
        List<RestaurantDTO> allRestaurants = restaurantService.finalAllRestaurants();
        return new ResponseEntity<>(allRestaurants, HttpStatus.OK);
    }

    @GetMapping("/fetchById/{restaurantId}")
    public ResponseEntity<RestaurantDTO> fetchRestaurantById(@PathVariable int restaurantId) {
        return restaurantService.fetchRestaurantById(restaurantId);
    }

    @PostMapping("/addRestaurant")
    public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO savedRestaurant = restaurantService.saveRestaurant(restaurantDTO);
        return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
    }
}
