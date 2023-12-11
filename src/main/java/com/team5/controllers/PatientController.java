package com.team5.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team5.beans.Patient;
import com.team5.exceptions.InvalidValueException;
import com.team5.services.PatientService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/patient")
@SecurityRequirement(name= "Bearer Authentication")
public class PatientController {

	@Autowired
	PatientService patientService;
	

	@PostMapping
	public String addNewPatient(@RequestBody Patient p) {
		System.out.println(p);
		return patientService.addNewPatient(p);

	}

	@GetMapping
	public List<Patient> getAllPatientDetail() {
		return patientService.getAllPatientDetail();
	}

	@GetMapping("/{physicianId}")
	public List<Patient> getPatientsByPhysician(@PathVariable int physicianId) {
		if(physicianId<0) throw new InvalidValueException("id must greater than 0");
		return patientService.getPatientsByPhysician(physicianId);
	}

	@GetMapping("/{physicianId}/{patientId}")
	    public Patient getPatientDetails(@PathVariable int physicianId, @PathVariable int patientId) {
		if(physicianId<0) throw new InvalidValueException("id must greater than 0");
		if(patientId<0) throw new InvalidValueException("id must greater than 0");
	        Patient patient = patientService.getPatientDetailsByPhysician(physicianId, patientId);

	        if (patient == null) throw new RuntimeException("Patient with ID " + patientId + " not found for physician " + physicianId);
	        

	        else return patient;
	}
	
	@GetMapping("/insurance/{patientId}")
	 public int getInsurance(@PathVariable("patientId") int patientId) {
		if(patientId<0) throw new InvalidValueException("id must greater than 0");
		 return patientService.getInsuranceService(patientId);
	 }
	
	@PutMapping("/address/{patientId}/{address}")
	public Patient updateAddress(@PathVariable("patientId") int patientId,@PathVariable String address) {
		if(patientId<0) throw new InvalidValueException("id must greater than 0");
		return patientService.updateAddressService(patientId,address);
		
	}
	@PutMapping("/phoneNumber/{patientId}/{phoneNumber}")
	public Patient updatePhone(@PathVariable("patientId") int patientId,@PathVariable String phoneNumber) {
		if(patientId<0) throw new InvalidValueException("id must greater than 0");
		return patientService.updatePhoneService(patientId,phoneNumber);
		
	}
}
