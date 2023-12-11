package com.team5.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team5.beans.Patient;
import com.team5.exceptions.NotFoundException;
import com.team5.repository.PatientDao;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientDao patientDao;

    @Override
    public String addNewPatient(Patient p) {
        patientDao.save(p);
        return "Record Created Successfully";
    }

    @Override
    public List<Patient> getAllPatientDetail() {
        List<Patient> patients = patientDao.findAll();
        if (patients.isEmpty()) {
            throw new NotFoundException("No patients found");
        }
        return patients;
    }

    @Override
    public List<Patient> getPatientsByPhysician(int physicianId) {
        List<Patient> patients = patientDao.findByPhysicianEmployeeId(physicianId);
        if (patients.isEmpty()) {
            throw new NotFoundException("No patients found for physician with ID: " + physicianId);
        }
        return patients;
    }

    @Override
    public Patient getPatientDetailsByPhysician(int physicianId, int patientId) {
        Patient patient = patientDao.findByPhysicianEmployeeIdAndSsn(physicianId, patientId);
        if (patient == null) {
            throw new NotFoundException("Patient not found for physician with ID: " + physicianId + " and patient ID: " + patientId);
        }
        return patient;
    }

    @Override
    public int getInsuranceService(int patientId) {
        Patient p = patientDao.findById(patientId).orElseThrow(() -> new NotFoundException("Invalid patient ID: " + patientId));
        return p.getInsuranceId();
    }

    @Override
    public Patient updateAddressService(int patientId, String address) {
        Patient patient = patientDao.findById(patientId).orElseThrow(() -> new NotFoundException("Invalid patient ID: " + patientId));
        patient.setAddress(address);
        patientDao.save(patient);
        return patient;
    }

    @Override
    public Patient updatePhoneService(int patientId, String phoneNumber) {
        Patient patient = patientDao.findById(patientId).orElseThrow(() -> new NotFoundException("Invalid patient ID: " + patientId));
        patient.setPhone(phoneNumber);
        patientDao.save(patient);
        return patient;
    }
}
