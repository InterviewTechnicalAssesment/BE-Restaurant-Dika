package com.project.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.project.restaurant.entity.Restaurant;
import com.project.restaurant.errorhandler.CommitFailedException;
import com.project.restaurant.errorhandler.DataNotFoundException;
import com.project.restaurant.errorhandler.UndefinedException;
import com.project.restaurant.service.RestaurantService;

@RestController
@RequestMapping("restaurant")
public class RestaurantController {
	@Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> get() {

        return restaurantService.getAll();
    }
    
    @PostMapping("/create")
	public @ResponseBody Restaurant createNew(@RequestBody Restaurant restaurant) {
		try {
			return restaurantService.createNew(restaurant);
		} catch (CommitFailedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		} catch (UndefinedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
    
    @GetMapping("/{id}/detail")
	public @ResponseBody Restaurant findById(@PathVariable Long id) {
		try {
			return restaurantService.getById(id);
		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@PutMapping("/{id}/update-data")
	public @ResponseBody Restaurant updateData(@RequestBody Restaurant restaurant, @PathVariable Long id) {
		return restaurantService.updateById(restaurant, id);
	}

	@DeleteMapping("/{id}/delete-data")
	public @ResponseBody ResponseEntity<Object> deleteById(@PathVariable Long id) {
		try {
			return restaurantService.deleteById(id);

		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}

	}
}
