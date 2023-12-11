package com.team5.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team5.beans.Nurse;
import com.team5.exceptions.NotFoundException;
import com.team5.repository.NurseDao;

@Service
public class NurseServiceImpl implements NurseService {

    @Autowired
    NurseDao nurseDao;

    @Override
    public String addNewNurse(Nurse nurse) {
        nurseDao.save(nurse);
        return "Record created successfully";
    }

    @Override
    public List<Nurse> getAllNurseList() {
        List<Nurse> nurses = nurseDao.findAll();
        if (nurses.isEmpty()) {
            throw new NotFoundException("No nurses found");
        }
        return nurses;
    }

    @Override
    public Nurse getNurseById(int empId) {
        Nurse nurse = nurseDao.findById(empId).orElseThrow(() -> new NotFoundException("Nurse with ID " + empId + " not found"));
        return nurse;
    }

    @Override
    public Nurse getPositionById(int empId) {
        Nurse nurse = nurseDao.findById(empId).orElseThrow(() -> new NotFoundException("Nurse with ID " + empId + " not found"));
        return nurse;
    }

    @Override
    public Boolean getRegisteredStatus(int empId) {
        List<Nurse> nurses = nurseDao.getRegisteredStatus(empId);
        if (nurses.isEmpty()) {
            throw new NotFoundException("Nurse with ID " + empId + " not found");
        }
        return nurses.size() > 0;
    }

    @Override
    public Nurse updateRegisteredStatusById(boolean registered, int empId) {
        Nurse nurse = nurseDao.findById(empId).orElseThrow(() -> new NotFoundException("Nurse with ID " + empId + " not found"));
        nurse.setRegistered(registered);
        return nurseDao.save(nurse);
    }

    @Override
    public Nurse updateNurseSSN(int ssn, int empId) {
        Nurse nurse = nurseDao.findById(empId).orElseThrow(() -> new NotFoundException("Nurse with ID " + empId + " not found"));
        nurse.setSsn(ssn);
        return nurseDao.save(nurse);
    }
}
