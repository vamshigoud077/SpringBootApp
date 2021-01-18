package com.training.hamburger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.hamburger.model.Menu;
import com.training.hamburger.repository.MenuRepository;

@RestController
@RequestMapping("/api")
public class MenuController {
	
	@Autowired
	MenuRepository menuRepository;
	
	@GetMapping("/menus")
	public ResponseEntity<List<Menu>> getAllItems(@RequestParam(required=false) String name){
		
		try {
			List<Menu> menu = new ArrayList<>();
			if(name==null) {
				menuRepository.findAll().forEach(menu::add);
			}else {
				menuRepository.findByNameContaining(name).forEach(menu::add);
			}
			if(menu.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(menu,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/menus/{id}")
	public ResponseEntity<Menu> getItem(@PathVariable("id") String id){
		Optional<Menu> menuData = menuRepository.findByItemCode(id);	
		try {
			if(menuData.isPresent()) {
				return new ResponseEntity<>(menuData.get(),HttpStatus.OK);
			}else {
				return new ResponseEntity(null,HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/menus")
	public ResponseEntity<Menu> createItem(@RequestBody Menu menu){
		try {
			Menu _menuData = menuRepository.save(menu);
			return new ResponseEntity<>(_menuData,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PutMapping("/menus/{id}")
	public ResponseEntity<Menu> updateItem(@RequestBody Menu menu, @PathVariable("id") String id){
		Optional<Menu> menuData = menuRepository.findById(id);
		
		if(menuData.isPresent()) {
			Menu _menu = menuData.get();
			_menu.setType(menu.getType());
			_menu.setName(menu.getName());
			_menu.setPrice(menu.getPrice());
			return new ResponseEntity<>(menuRepository.save(_menu),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/menus/{id}")
	public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") String id){
		
		try {
			menuRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/menus")
	public ResponseEntity<HttpStatus> deleteAllItems(){
		try {
			menuRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
