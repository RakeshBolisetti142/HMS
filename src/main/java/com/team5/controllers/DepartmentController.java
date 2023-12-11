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

import com.team5.beans.Department;
import com.team5.beans.Physician;
import com.team5.beans.TrainedIn;
import com.team5.exceptions.InvalidValueException;
import com.team5.services.DepartmentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@RestController
@RequestMapping("/api/department")
@SecurityRequirement(name= "Bearer Authentication")
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;
 
	@PostMapping
	public String addNewDepartment(@RequestBody Department department) {
		System.out.println(department);
		return departmentService.addNewDepartment(department);
	}
 
	@GetMapping("/")
	public List<Department> getAllDepartmentService() {
		return departmentService.getAllDepartmentService();
	}
 
	@GetMapping("/{deptId}")
	public Department getDepartmentById(@PathVariable int deptId) {
		if(deptId<0) throw new InvalidValueException("id must greater than 0");
		Department dept = departmentService.findByDepartmentId(deptId);
		if (dept == null)
			System.out.println("Department is not present for the Id : " + deptId);
		return dept;
 
	}
 
	@GetMapping("/head/{deptId}")
	public Physician getHeadOfDepartment(@PathVariable int deptId) {
		if(deptId<0) throw new InvalidValueException("id must greater than 0");
		Department dept = departmentService.findByDepartmentId(deptId);
		if (dept != null)
			return dept.getHead();
		System.out.println("Department is not present for the Id : " + deptId);
		return null;
	}
 
	@GetMapping("/physician/{head}")
	public List<Department> getDepartmentsByPhysicianId(@PathVariable int head) {
		if(head<0) throw new InvalidValueException("id must greater than 0");
		return departmentService.getDepartmentsByPhysicianId(head);
	}
 
	@GetMapping("/check/{physicianId}")
	public boolean checkPhysicianIsHeadOfDepartment(@PathVariable int physicianId) {
		if(physicianId<0) throw new InvalidValueException("id must greater than 0");
		return departmentService.checkPhysicianIsHeadOfDepartment(physicianId);
	}
 
	@PutMapping("/update/{deptId}")
	public Department updateDepartmentHead(@RequestBody Physician physician, @PathVariable("deptId") int deptId) {
		if(deptId<0) throw new InvalidValueException("id must greater than 0");
		return departmentService.updateDepartmentHead(physician, deptId);
	}
 
	@PutMapping("/update/{deptname}/{deptId}")
	public Department updateDepartmentName(@PathVariable("deptname") String deptName, @PathVariable("deptId") int deptId) {
		if(deptId<0) throw new InvalidValueException("id must greater than 0");
		return departmentService.updateDepartmentName(deptName, deptId);
	}
	@GetMapping("headcertification/{deptid}")
	public List<TrainedIn> getHeadCertification(@PathVariable("deptid") int deptId){
		if(deptId<0) throw new InvalidValueException("id must greater than 0");
		return departmentService.getHeadCertificationService(deptId);
		
	}
	
}
