package com.team5.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team5.beans.Physician;
import com.team5.beans.Procedures;
import com.team5.beans.TrainedIn;
import com.team5.exceptions.NotFoundException;
import com.team5.repository.TrainedInDao;

@Service
public class TrainedInServiceImpl implements TrainedInService {
    @Autowired
    TrainedInDao trainedInDao;

    public String addNewCertification(TrainedIn t) {
        trainedInDao.save(t);
        return "Record Created Successfully";
    }

    @Override
    public List<Procedures> getAllProceduresByTreatment() {
        List<TrainedIn> trainedInList = trainedInDao.findAll();
        if (trainedInList.isEmpty()) throw new NotFoundException("No TrainedIn found");
        List<Procedures> procedures = new ArrayList<>();
        trainedInList.forEach(t -> {
            procedures.add(t.getId().getTreatmentId());
        });
        return procedures;
    }

    @Override
    public List<Procedures> getAllProceduresDoneByPhysician(int physicianId) {
        List<TrainedIn> trainedInList = trainedInDao.findByIdPhysicianEmployeeId(physicianId);
        if (trainedInList.isEmpty()) {
            throw new NotFoundException("No procedures found for physician with ID: " + physicianId);
        }
        List<Procedures> procedures = new ArrayList<>();
        trainedInList.forEach(t -> procedures.add(t.getId().getTreatmentId()));
        return procedures;
    }

    @Override
    public List<Physician> getAllPhysiciansByTreatment(int treatmentId) {
        List<TrainedIn> trainedInList = trainedInDao.findByIdTreatmentCode(treatmentId);
        if (trainedInList.isEmpty()) {
            throw new NotFoundException("No physicians found for treatment with ID: " + treatmentId);
        }

        List<Physician> physicians = new ArrayList<>();
        trainedInList.forEach(t -> {
            physicians.add(t.getId().getPhysicianId());
        });
        return physicians;
    }

    public List<Procedures> getProceduresExpiringSoonForPhysician(Long physicianId) {
        LocalDateTime oneMonthFromNow = LocalDateTime.now().plusMonths(1);
        List<Procedures> procedures = trainedInDao.findProceduresExpiringSoonForPhysician(physicianId, oneMonthFromNow);
        if (procedures.isEmpty()) {
            throw new NotFoundException("No procedures found for physician with ID: " + physicianId);
        }
        return procedures;
    }

    @Override
    public List<Procedures> getAllProceduresWithCertificationExpireInAMonth(int physicianId) {
        
        return null;
    }


    public boolean updateCertificationExpiry(int physicianId, int procedureId, LocalDateTime newExpiryDate) {
    	
        TrainedIn trainedIn = trainedInDao.findByIdPhysicianEmployeeIdAndIdTreatmentCode(physicianId, procedureId);
        
        if (trainedIn != null) {
            trainedIn.setCertificationExpires(newExpiryDate);
            trainedInDao.save(trainedIn);
            return true;
        }
        return false;
    }
   

}
