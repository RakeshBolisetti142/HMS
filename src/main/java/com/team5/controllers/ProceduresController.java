package com.team5.controllers;

import 	java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team5.beans.Procedures;
import com.team5.exceptions.InvalidTypeConversionException;
import com.team5.exceptions.InvalidValueException;
import com.team5.services.ProceduresService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name="Bearer Authentication")
@RestController
@RequestMapping("/api/procedures")
public class ProceduresController {

	@Autowired
	ProceduresService proceduresService;

	@PostMapping
	public String addNewTreatment(@RequestBody Procedures procedures) {
		return proceduresService.addNewProcedures(procedures);
	}
	@GetMapping("/")
	public List<Procedures> getAllProcedures() {
		return proceduresService.getAllProceduresByProceduresService();

	}
	@GetMapping("/cost/{id}")
	public Procedures getProcedurescostById(@PathVariable("id") String id) {
		int identity;
		if(id.matches("[0-9]+")) identity = Integer.parseInt(id);
		else throw new InvalidTypeConversionException("id must be Integer");
		if(identity<0) throw new InvalidValueException("id must be greater than 0");
	
		return proceduresService.getProcedurescostById(identity);

	}
	@GetMapping("/cost/name/{name}")
	public Procedures getprocedurescostfindByName(@PathVariable("name") String name) {
		return proceduresService.getprocedurescostfindByName(name);
	}
	@PutMapping("/cost/{cost}/{Id}")
	public Procedures updateProcedurescostById(@PathVariable("Id") String id,@PathVariable("cost") int cost) {
		int identity;
		if(id.matches("[0-9]+")) identity = Integer.parseInt(id);
		else throw new InvalidTypeConversionException("id must be Integer");
		if(identity<0) throw new InvalidValueException("id must be greater than 0");
		return proceduresService.updateProcedurescostById(identity,cost);
	}
	@PutMapping("/name/{name}/id/{id}")
	public Procedures updateProceduresnameById(@PathVariable("id") String id,@PathVariable("name")String name ) {
		int identity;
		if(id.matches("[0-9]+")) identity = Integer.parseInt(id);
		else throw new InvalidTypeConversionException("id must be Integer");
		if(identity<0) throw new InvalidValueException("id must be greater than 0");
		return proceduresService.updateProceduresnameById(identity,name);
	}

}
