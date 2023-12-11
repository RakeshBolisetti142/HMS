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

import com.team5.beans.AffiliatedWith;
import com.team5.beans.Department;
import com.team5.beans.Physician;
import com.team5.exceptions.InvalidValueException;
import com.team5.services.AffiliatedWithService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/affiliated_with")
@SecurityRequirement(name= "Bearer Authentication")
public class AffiliatedWithController {

	@Autowired
	AffiliatedWithService affliatedWithService;

	@PostMapping("/post")
	public String addAfliatedWithExistingPhysician(@RequestBody AffiliatedWith affiliatedWith) {

		affliatedWithService.addAffliatedWithService(affiliatedWith);
		return "Record Created Successfully";

	}

	@GetMapping("/physicians/{deptid}")
	public List<Physician> getPhysiciansByDepartment(@PathVariable("deptid") int deptId) {
		if (deptId < 0)
			throw new InvalidValueException("id must greater than 0");
		List<Physician> physicians = affliatedWithService.getPhysiciansByDepartment(deptId);

		return physicians;

	}

	@GetMapping("/department/{physicianid}")
	public List<Department> getDepartmentsByPhysician(@PathVariable("physicianid") int physicianId) {
		if (physicianId < 0)
			throw new InvalidValueException("id must greater than 0");
		List<Department> departments = affliatedWithService.getDepartmentsByPhysician(physicianId);
		return departments;

	}

	@GetMapping("/countphysician/{deptid}")
	public int getPhysiciansCountByDepartment(@PathVariable("deptid") int deptId) {
		if (deptId < 0)
			throw new InvalidValueException("id must greater than 0");

		return getPhysiciansByDepartment(deptId).size();
	}

	@GetMapping("/primary/{physicianid}")
	public boolean isPrimaryAffiliation(@PathVariable("physicianid") int physicianId) {
		if (physicianId < 0)
			throw new InvalidValueException("id must greater than 0");
		boolean isPrimaryAffiliation = affliatedWithService.isPrimaryAffiliation(physicianId);

		return isPrimaryAffiliation;
	}

	@PutMapping("/primary/{physicianid}/{affiliation}")
	public boolean updatePrimaryAffiliation(@PathVariable("physicianid") int physicianId,
			@PathVariable("affiliation") boolean affiliation) {
		if (physicianId < 0)
			throw new InvalidValueException("id must greater than 0");
		return affliatedWithService.updatePrimaryAffiliation(physicianId, affiliation);

	}

}
