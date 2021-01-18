package com.training.hamburger.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.training.hamburger.model.Reservation;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
	List<Reservation> findByNameContaining(String name);
}