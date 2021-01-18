package com.training.hamburger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.training.hamburger.model.Reservation;
import com.training.hamburger.repository.ReservationRepository;

@Service
public class ReservationService {
	
	@Autowired
	ReservationRepository reservationRepository;
	
	public List<Reservation> findAllReservation(String name){
		List<Reservation> reservation = new ArrayList<>();
		
		if(name==null) {
			reservationRepository.findAll().forEach(reservation::add);
		}else {
			reservationRepository.findByNameContaining(name).forEach(reservation::add);
		}
		return reservation;	
	}
	
	public Optional<Reservation> getReservation(String id) {
	
		return reservationRepository.findById(id);
		
	}
	
	public Reservation createReservation(Reservation reservation) {
		
		return reservationRepository.save(reservation);
		
		
	}
	
	public Optional<Reservation> updateReservation(String id, Reservation reservation) {
		Optional<Reservation> reservationData = reservationRepository.findById(id);
		Reservation _reservation = reservationData.get();
		_reservation.setName(reservation.getName());
		_reservation.setDate(reservation.getDate());
		reservationRepository.save(_reservation);
		return Optional.of(_reservation);
	}

	public void deleteReservation(String id) {
		reservationRepository.deleteById(id);
	}
	
	public void deleteAllReservations() {
		reservationRepository.deleteAll();
	}
	
	

}