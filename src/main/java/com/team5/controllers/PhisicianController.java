package com.team5.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.team5.beans.Physician;
import com.team5.exceptions.InvalidValueException;
import com.team5.services.PhysicianService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/physician")
@SecurityRequirement(name= "Bearer Authentication")
public class PhisicianController {
	
		@Autowired
		public PhysicianService physicianService;
		@PostMapping
		@ResponseStatus(HttpStatus.OK)
		public ResponseEntity<String> addNewPhysician(@RequestBody Physician physician) {
			return new ResponseEntity<>(physicianService.addNewPhysician(physician),HttpStatus.OK);
		}
		@GetMapping("/{pos}")
		@ResponseStatus(HttpStatus.OK)
		public ResponseEntity<List<Physician>> getAllPhysician(@PathVariable("pos") String position){
			
			
			return new ResponseEntity<>(physicianService.getAllPhysiciansByPosService(position),HttpStatus.OK);
			
		}
		@GetMapping("/id/{empId}")
		public Physician getPhysicianById(@PathVariable("empId") int empId) {
			if(empId<0) throw new InvalidValueException("id must greater than 0");
				physicianService.getPhysicianById(empId);
			return physicianService.getPhysicianById(empId);
		}
		@PutMapping("/update/position/{position}/{empId}")
		public Physician updatePhysicianPos( @PathVariable("position")String position, @PathVariable("empId") int empId)
		{
			if(empId<0) throw new InvalidValueException("id must greater than 0");
			return physicianService.updatePhysicianPosService(position, empId);
		}
		@PutMapping("/update/name/{name}/{empId}")
		public Physician updatePhysicianName(@PathVariable("name") String name,@PathVariable("empId") int empId) {
			if(empId<0) throw new InvalidValueException("id must greater than 0");
			return physicianService.updatePhysicianNameService(name, empId);
		}
		@PutMapping("/update/ssn/{ssn}/{empId}")
		public Physician updatePhysicianSSN(@PathVariable("ssn") int ssn,@PathVariable("empId") int empId) {
			if(empId<0) throw new InvalidValueException("id must greater than 0");
			return physicianService.updatePhysicianSsnService(ssn, empId);
		}
		@GetMapping()
		public Physician findByPhysicianName(@RequestParam("name") String name) {
			return physicianService.findByPhysicianNameService(name);
		}
}
