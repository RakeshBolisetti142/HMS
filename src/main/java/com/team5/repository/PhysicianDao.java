package com.team5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team5.beans.Physician;

@Repository
public interface PhysicianDao extends JpaRepository<Physician, Integer> {
	
	List<Physician> findByPosition(String position);
	Physician findByName(String name);

	
	

}
