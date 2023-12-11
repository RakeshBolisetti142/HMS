package com.team5.services;

import java.util.List;

import com.team5.beans.Physician;

public interface PhysicianService {
	
	String addNewPhysician(Physician p);
	
	List<Physician> getAllPhysiciansByPosService(String position);
	Physician getPhysicianById(int empId);

	Physician updatePhysicianPosService(String position, int empId);
	
	Physician updatePhysicianNameService(String name,int empId);
	
	Physician updatePhysicianSsnService(int ssn, int empId);
	Physician findByPhysicianNameService(String name);
		

}
