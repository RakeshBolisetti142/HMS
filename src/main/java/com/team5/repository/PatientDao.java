package com.team5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team5.beans.Patient;
@Repository
public interface PatientDao extends JpaRepository<Patient,Integer>{
	List<Patient> findByPhysicianEmployeeId(int employeeId);
	Patient findByPhysicianEmployeeIdAndSsn(int physicianId, int patientId);
	
	
	
	
}
