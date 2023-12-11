package com.team5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team5.beans.Procedures;

@Repository

public interface ProceduresDao extends JpaRepository<Procedures, Integer>{

	Procedures findByName(String name);




	

}
