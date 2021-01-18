package com.training.hamburger.controller;



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

import com.training.hamburger.model.Reservation;
import com.training.hamburger.service.ReservationService;

@RestController
@RequestMapping("/api")
public class ReservationController {

	@Autowired
	ReservationService reservationService;
	
	@GetMapping("/reservations")
	public ResponseEntity<List<Reservation>> findAllReservation(@RequestParam(required=false) String name){
		List<Reservation> reservations = reservationService.findAllReservation(name);
		try {
			if(reservations.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(reservations,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@GetMapping("/reservations/{id}")
	public ResponseEntity<Reservation> getReservation(@PathVariable("id") String id){
		Optional<Reservation> reservationData = reservationService.getReservation(id);
		
		if(reservationData.isPresent()) {
			return new ResponseEntity<>(reservationData.get(),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}	
	}
	
	@PostMapping("/reservations")
	public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){ 
		try {
			return new ResponseEntity<>(reservationService.createReservation(reservation),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/reservations/{id}")
	public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation reservation, @PathVariable("id") String id){
		Optional<Reservation> reservationData = reservationService.updateReservation(id, reservation);
		try {
			return new ResponseEntity<>(reservationData.get(),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/reservations")
	public ResponseEntity<Reservation> deleteAllReservation(){	
		try {
			reservationService.deleteAllReservations();
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<Reservation> deleteReservation(@PathVariable("id") String id){	
		try {
			reservationService.deleteReservation(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}