package com.project.restaurant.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.restaurant.entity.Menu;
import com.project.restaurant.errorhandler.CommitFailedException;
import com.project.restaurant.errorhandler.DataNotFoundException;
import com.project.restaurant.errorhandler.UndefinedException;
import com.project.restaurant.repository.MenuRepository;
import com.project.restaurant.util.SuccessTemplateMessage;

@Service
public class MenuService{

	
	@Autowired
    private MenuRepository menuRepository;
	
	public List<Menu> getAll() {
		// TODO Auto-generated method stub
		return menuRepository.findAll();
	}
	
	public List<Menu> findMenuByIds(Set<Long> ids) {
		// TODO Auto-generated method stub
		return menuRepository.findMenuByIds(ids);
	}
	
	public Menu createNew(Menu newData) throws CommitFailedException, UndefinedException {
		try {
			return menuRepository.save(newData);
		} catch (Exception e) {
			if (e.getMessage().contains("Error while committing")) {
				throw new CommitFailedException();
			} else {
				throw new UndefinedException(e.toString());
			}
		}
	}

	public Menu getById(Long id) throws DataNotFoundException {
		return menuRepository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	public Menu updateById(Menu updatedData, Long id) {

		return menuRepository.findById(id).map(data -> {
			updatedData.setId(id);
			data = updatedData;

			return menuRepository.save(data);
		}).orElseGet(() -> {
			return menuRepository.save(updatedData);
		});
	}

	public ResponseEntity<Object> deleteById(long id) throws DataNotFoundException {

		try {
			if (menuRepository.findById(id) != null) {
				menuRepository.deleteById(id);
			}
		} catch (Exception e) {
			throw new DataNotFoundException();
		}
		return new ResponseEntity<Object>(new SuccessTemplateMessage(), HttpStatus.OK);
	}

}
