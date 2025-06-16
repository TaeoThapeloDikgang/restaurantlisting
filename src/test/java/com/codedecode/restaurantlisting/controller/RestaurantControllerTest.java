package com.codedecode.restaurantlisting.controller;

import com.codedecode.restaurantlisting.dto.RestaurantDTO;
import com.codedecode.restaurantlisting.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class RestaurantControllerTest {

    // Mocking is a process of developing the objects that act as the mock or clone of the real objects.
    // Mocking for unit testing is when you create an object that implements the behavior of a real subsystem in controlled ways.
    // A method invoked using mocked reference does not execute the actual method body defined in the class file, rather the method behavior is configured using when(...).thenReturn(...) methods.
    // In mockito-based junit tests, @Mock annotation creates mocks and @InjectMocks creates actual objects and injects mocked dependencies into it.
    // Why do you need Mocking at first place?
    //  1. Database connections - Sometimes the database queries can take 10, 20, or more seconds to execute.
    //  2. Classes with side effects like who sends email to clients
    //  3. Web services / External resource. Connecting to an external resource can take too much time. And can be down which impacts our testing.
    //  4. Classes that are slow,

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllRestaurantsTest() {
        // arrange
        List<RestaurantDTO> mockRestaurants = Arrays.asList(
                new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
                new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2")
        );
        when(restaurantService.findAllRestaurants()).thenReturn(mockRestaurants);

        // Act
        ResponseEntity<List<RestaurantDTO>> response = restaurantController.fetchAllRestaurants();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRestaurants, response.getBody());
        verify(restaurantService, times(1)).findAllRestaurants();
    }

    @Test
    public void saveRestaurantTest() {
        // Arrange
        RestaurantDTO mockRestaurantDTO =  new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
        when(restaurantService.saveRestaurant(mockRestaurantDTO)).thenReturn(mockRestaurantDTO);

        // Act
        ResponseEntity<RestaurantDTO> response = restaurantController.addRestaurant(mockRestaurantDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockRestaurantDTO, response.getBody());
        verify(restaurantService, times(1)).saveRestaurant(mockRestaurantDTO);
    }

    @Test
    public void fetchRestaurantById() {
        // Arrange
        RestaurantDTO mockRestaurantDTO = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
        ResponseEntity<RestaurantDTO> mockResponseEntity = new ResponseEntity<>(mockRestaurantDTO, HttpStatus.OK);
        when(restaurantService.fetchRestaurantById(1)).thenReturn(mockResponseEntity);

        // Act
        ResponseEntity<RestaurantDTO> response = restaurantController.fetchRestaurantById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRestaurantDTO, response.getBody());
        verify(restaurantService, times(1)).fetchRestaurantById(1);
    }
}
