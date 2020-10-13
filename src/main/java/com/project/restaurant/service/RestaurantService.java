package com.project.restaurant.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.restaurant.entity.Restaurant;
import com.project.restaurant.entity.Restaurant;
import com.project.restaurant.errorhandler.CommitFailedException;
import com.project.restaurant.errorhandler.DataNotFoundException;
import com.project.restaurant.errorhandler.UndefinedException;
import com.project.restaurant.repository.RestaurantRepository;
import com.project.restaurant.util.SuccessTemplateMessage;

@Service
public class RestaurantService{

	
	@Autowired
    private RestaurantRepository restaurantRepository;
	
	public List<Restaurant> getAll() {
		// TODO Auto-generated method stub
		return restaurantRepository.findAll();
	}
	
	public Restaurant createNew(Restaurant newData) throws CommitFailedException, UndefinedException {
		try {
			return restaurantRepository.save(newData);
		} catch (Exception e) {
			if (e.getMessage().contains("Error while committing")) {
				throw new CommitFailedException();
			} else {
				throw new UndefinedException(e.toString());
			}
		}
	}

	public Restaurant getById(Long id) throws DataNotFoundException {
		return restaurantRepository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	public Restaurant updateById(Restaurant updatedData, Long id) {

		return restaurantRepository.findById(id).map(data -> {
			updatedData.setId(id);
			data = updatedData;

			return restaurantRepository.save(data);
		}).orElseGet(() -> {
			return restaurantRepository.save(updatedData);
		});
	}

	public ResponseEntity<Object> deleteById(long id) throws DataNotFoundException {

		try {
			if (restaurantRepository.findById(id) != null) {
				restaurantRepository.deleteById(id);
			}
		} catch (Exception e) {
			throw new DataNotFoundException();
		}
		return new ResponseEntity<Object>(new SuccessTemplateMessage(), HttpStatus.OK);
	}

}
