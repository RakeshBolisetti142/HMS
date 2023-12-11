package com.team5.services;

import java.util.List;

import com.team5.beans.Nurse;

public interface NurseService {

	public String addNewNurse(Nurse nurse);

    
	Nurse getNurseById(int empId);
	public List<Nurse> getAllNurseList();
	Nurse getPositionById(int empid);
	Boolean getRegisteredStatus(int empId);
	Nurse updateRegisteredStatusById(boolean registered,int empId);
    Nurse updateNurseSSN(int ssn,int empId);


   


	


	

	

	
	
	
	



}
