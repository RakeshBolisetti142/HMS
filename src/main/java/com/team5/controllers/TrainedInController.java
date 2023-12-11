package com.team5.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team5.beans.Physician;
import com.team5.beans.Procedures;
import com.team5.beans.TrainedIn;
import com.team5.exceptions.InvalidTypeConversionException;
import com.team5.exceptions.InvalidValueException;
import com.team5.services.TrainedInService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/trained_in")
@SecurityRequirement(name= "Bearer Authentication")
public class TrainedInController {
	@Autowired
	TrainedInService trainedInService;

	@PostMapping
	public String addNewCertification(@RequestBody TrainedIn trainedIn) {

		return trainedInService.addNewCertification(trainedIn);
	}

	@GetMapping
	public List<Procedures> getAllProcedures() {
		return trainedInService.getAllProceduresByTreatment();
	}

	@GetMapping("/treatment/{physicianId}")
	public List<Procedures> getAllProceduresDoneByPhysician(@PathVariable String physicianId) {
		int identity;
		if(physicianId.matches("[(-9)-9]+")) {
			identity = Integer.parseInt(physicianId);
			if(identity<0) throw new InvalidValueException("id must be greater than 0");
		}
		else {
			
			throw new InvalidTypeConversionException("id must be Integer");
		}
		
		return trainedInService.getAllProceduresDoneByPhysician(identity);
	}

	@GetMapping("/physians/{procedureId}")
	public List<Physician> getAllPhysiciansByTreatment(@PathVariable int procedureId) {
		if(procedureId<0)throw new InvalidValueException("procedureId must be greater than 0");
		return trainedInService.getAllPhysiciansByTreatment(procedureId);
	}


    @GetMapping("/expiredsooncerti/{physicianId}")
    public List<Procedures> getProceduresWithExpiredCertification(@PathVariable("physicianId") Long physicianId) {
        // Assuming TrainedInService has a method to retrieve the list of procedures expiring soon
        return trainedInService.getProceduresExpiringSoonForPhysician(physicianId);
    }

@PutMapping("/{physicianId}&{procedureId}")
public ResponseEntity<Boolean> updateCertificationExpiry(
        @PathVariable("physicianId") int physicianId,
        @PathVariable("procedureId") int procedureId,
        @RequestBody LocalDateTime newExpiryDate) {
	if(physicianId<0|procedureId<0)throw new InvalidValueException("id must be greater than 0");
    
    boolean updated = trainedInService.updateCertificationExpiry(physicianId, procedureId, newExpiryDate);
    
    if (updated) {
        return ResponseEntity.ok().body(true);
    } else {
        return ResponseEntity.badRequest().body(false);
    }
}
}
