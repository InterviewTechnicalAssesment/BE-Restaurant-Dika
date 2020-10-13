package com.project.restaurant.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.project.restaurant.entity.Menu;
import com.project.restaurant.errorhandler.DataNotFoundException;
import com.project.restaurant.errorhandler.UndefinedException;
import com.project.restaurant.model.FindAllByIdRequest;
import com.project.restaurant.errorhandler.CommitFailedException;
import com.project.restaurant.service.MenuService;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<Menu> get() {

        return menuService.getAll();
    }
    
    @PostMapping("/find_all_by_ids")
	public @ResponseBody List<Menu> findAllByIds(@RequestBody FindAllByIdRequest ids) {
			return menuService.findMenuByIds(ids.getIds());
		
	}
    
    @PostMapping("/create")
	public @ResponseBody Menu createNew(@RequestBody Menu menu) {
		try {
			return menuService.createNew(menu);
		} catch (CommitFailedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		} catch (UndefinedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
    
    @GetMapping("/{id}/detail")
	public @ResponseBody Menu findById(@PathVariable Long id) {
		try {
			return menuService.getById(id);
		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@PutMapping("/{id}/update-data")
	public @ResponseBody Menu updateData(@RequestBody Menu menu, @PathVariable Long id) {
		return menuService.updateById(menu, id);
	}

	@DeleteMapping("/{id}/delete-data")
	public @ResponseBody ResponseEntity<Object> deleteById(@PathVariable Long id) {
		try {
			return menuService.deleteById(id);

		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}

	}
}

