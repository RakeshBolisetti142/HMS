package com.team5.services;

import java.util.List;

import com.team5.beans.Patient;

public interface PatientService {
	

	public String addNewPatient(Patient p);
	
	List<Patient> getAllPatientDetail();

   List<Patient> getPatientsByPhysician(int physicianId);

 Patient getPatientDetailsByPhysician(int physicianId, int patientId);

public int getInsuranceService(int patientId);

public Patient updateAddressService(int patientId,String addresss);

public Patient updatePhoneService(int patientId, String phoneNumber);

	
}

 