package com.team5.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team5.beans.Procedures;
import com.team5.exceptions.NotFoundException;
import com.team5.repository.ProceduresDao;

@Service
public class ProceduresServiceImpl implements ProceduresService {

    @Autowired
    ProceduresDao proceduresDao;

    public String addNewProcedures(Procedures procedures) {
        if (procedures.getCode() == 0) {
            throw new NotFoundException("Procedure code should not be empty");
        }

        proceduresDao.save(procedures);
        return "Record Created Successfully";
    }

    @Override
    public List<Procedures> getAllProceduresByProceduresService() {
        List<Procedures> procedures = proceduresDao.findAll();
        if (procedures.isEmpty()) throw new NotFoundException("No Procedures found");

        return procedures;
    }

    @Override
    public Procedures getProcedurescostById(int id) {
        Procedures procedures = proceduresDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Procedures not found with ID: " + id));
        return procedures;
    }

    @Override
    public Procedures getprocedurescostfindByName(String name) {
        Procedures procedures = proceduresDao.findByName(name);
        if (procedures != null) return procedures;
        else {
            throw new NotFoundException("Procedures not found with name: " + name);
        }
    }

    @Override
    public Procedures updateProcedurescostById(int id, int cost) {
        Procedures procedures = proceduresDao.findById(id).orElse(null);

        if (procedures != null) {
            procedures.setCost(cost);
            proceduresDao.save(procedures);
            return procedures;
        } else {
            throw new NotFoundException("Procedures not found with ID: " + id);
        }
    }

    @Override
    public Procedures updateProceduresnameById(int id, String name) {
        Procedures procedures = proceduresDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Procedures not found with ID: " + id));
        procedures.setName(name);
        proceduresDao.save(procedures);
        return procedures;
    }
}
