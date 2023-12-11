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

import com.team5.beans.Nurse;
import com.team5.exceptions.InvalidValueException;
import com.team5.services.NurseService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/nurse")
@SecurityRequirement(name= "Bearer Authentication")
public class NurseController {
	
	@Autowired
	private NurseService nurseService;
	
	@PostMapping
	public String addNewNurse(@RequestBody Nurse nurse) {
		return nurseService.addNewNurse(nurse);
	}
	
	@GetMapping
	public List<Nurse> getAllnurseService(){
		return nurseService.getAllNurseList();
		
	}
	
	
    @GetMapping("/{empid}")
    public Nurse getNurseById(@PathVariable("empid") int  empId) {
    	if(empId<0) throw new InvalidValueException("id must greater than 0");
    	
    	return nurseService.getNurseById(empId);
   
	}
    
   
    
    @GetMapping("/position/{empId}")
    public Nurse getpositionById(@PathVariable("empId")int empId) {
    	if(empId<0) throw new InvalidValueException("id must greater than 0");
    	return nurseService.getPositionById(empId);
    	
    }
	
    
	
	  @GetMapping("/registered/{empid}") 
	  public Boolean getnurseisregisteredornot(@PathVariable("empid")int empId) { 
		  if(empId<0) throw new InvalidValueException("id must greater than 0");
		  return nurseService.getRegisteredStatus(empId);
	  
	  }
	 
	  
	  
	  
     @PutMapping("update/register/{registered}/{empid}")
    public Nurse updateregisteredById(@PathVariable("registered")boolean registered,@PathVariable("empid")int empId){
    	 if(empId<0) throw new InvalidValueException("id must greater than 0");
     return nurseService.updateRegisteredStatusById(registered,empId);
    }
     
    
     @PutMapping("/update/{ssn}/{empid}")
     public Nurse updateNurseSSN(@PathVariable("ssn")int ssn,@PathVariable("empid")int empId) {
    	 if(empId<0) throw new InvalidValueException("id must greater than 0");
    	 return nurseService.updateNurseSSN(ssn,empId);
     }
     
}
