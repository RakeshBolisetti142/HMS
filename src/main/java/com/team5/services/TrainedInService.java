package com.team5.services;

import java.time.LocalDateTime;
import java.util.List;

import com.team5.beans.Physician;
import com.team5.beans.Procedures;
import com.team5.beans.TrainedIn;

public interface TrainedInService {

	String addNewCertification(TrainedIn trainedIn);

	List<Procedures> getAllProceduresByTreatment();

	List<Procedures> getAllProceduresDoneByPhysician(int physicianId);

	List<Physician> getAllPhysiciansByTreatment(int treatmentId);

	List<Procedures> getAllProceduresWithCertificationExpireInAMonth(int physicianId);
	List<Procedures> getProceduresExpiringSoonForPhysician(Long physicianId);

	boolean updateCertificationExpiry(int physicianId, int procedureId, LocalDateTime newExpiryDate);
}
