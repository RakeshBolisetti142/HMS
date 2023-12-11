package com.team5.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team5.beans.Physician;
import com.team5.exceptions.NotFoundException;
import com.team5.repository.PhysicianDao;

@Service
public class PhysicianServiceImpl implements PhysicianService {

    @Autowired
    PhysicianDao physicianDao;

    public Physician getPhysicianById(int empId) {
        return physicianDao.findById(empId)
                .orElseThrow(() -> new NotFoundException("Physician with ID " + empId + " not found"));
    }

    public String addNewPhysician(Physician p) {
        if (p.getEmployeeId() == 0) throw new NotFoundException("Physician ID should not be empty");
        physicianDao.save(p);
        return "Record Created Successfully";
    }

    @Override
    public List<Physician> getAllPhysiciansByPosService(String position) {
        List<Physician> ph = physicianDao.findByPosition(position);
        if (ph.isEmpty()) throw new NotFoundException("There are no Physicians with position " + position);
        return ph;
    }

    public Physician updatePhysicianPosService(String position, int empId) {
        Physician ph = getPhysicianById(empId);
        if (ph != null) {
            ph.setPosition(position);
            return physicianDao.save(ph);
        } else
            throw new NotFoundException("Physician with ID " + empId + " not found");
    }

    @Override
    public Physician updatePhysicianNameService(String name, int empId) {
        Physician ph = getPhysicianById(empId);
        ph.setName(name);
        return physicianDao.save(ph);
    }

    @Override
    public Physician updatePhysicianSsnService(int ssn, int empId) {
        Physician ph = getPhysicianById(empId);
        ph.setSsn(ssn);
        return ph;
    }

    @Override
    public Physician findByPhysicianNameService(String name) {
        Physician ph = physicianDao.findByName(name);
        if (ph != null) {
            return ph;
        } else {
            throw new NotFoundException("Physician with name " + name + " not found");
        }
    }
}
