package com.team5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.team5.beans.Patient;
import com.team5.repository.PatientDao;
import com.team5.services.PatientServiceImpl;

@SpringBootTest
class PatientServiceImplTest {

    @Mock
    private PatientDao patientDao;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void testAddNewPatient() {
        Patient patient = new Patient(); // Create a dummy patient object
        when(patientDao.save(any())).thenReturn(patient); // Mock the save method of the repository

        String result = patientService.addNewPatient(patient);

        assertEquals("Record Created Successfully", result);
        verify(patientDao, times(1)).save(any()); // Verify that the save method was called exactly once
    }

    @Test
    void testGetAllPatientDetail() {
  
    	List<Patient> patients = new ArrayList<Patient>();
        Patient p = new Patient();
        p.setSsn(12);
        patients.add(p);
        when(patientDao.findAll()).thenReturn(patients); 

        List<Patient> result = patientService.getAllPatientDetail();

        assertSame(patients, result);
        verify(patientDao, times(1)).findAll(); 
    }

    @Test
    void testGetPatientsByPhysician() {
        int physicianId = 1;
        List<Patient> patients = new ArrayList<>(); // Create a list of dummy patients
        Patient p = new Patient();
        p.setSsn(20);
        p.setSsn(physicianId);
        patients.add(p);
        when(patientDao.findByPhysicianEmployeeId(physicianId)).thenReturn(patients); // Mock the findByPhysicianEmployeeId method

        List<Patient> result = patientService.getPatientsByPhysician(physicianId);

        assertSame(patients, result);
        verify(patientDao, times(1)).findByPhysicianEmployeeId(physicianId); // Verify that the findByPhysicianEmployeeId method was called exactly once
    }

    @Test
    void testGetPatientDetailsByPhysician() {
        int physicianId = 1;
        int patientId = 2;
        Patient patient = new Patient(); // Create a dummy patient object
        when(patientDao.findByPhysicianEmployeeIdAndSsn(physicianId, patientId)).thenReturn(patient); // Mock the findByPhysicianEmployeeIdAndSsn method

        Patient result = patientService.getPatientDetailsByPhysician(physicianId, patientId);

        assertSame(patient, result);
        verify(patientDao, times(1)).findByPhysicianEmployeeIdAndSsn(physicianId, patientId); // Verify that the findByPhysicianEmployeeIdAndSsn method was called exactly once
    }

    @Test
    void testGetInsuranceService() {
        int patientId = 1;
        Patient patient = new Patient(); // Create a dummy patient object
        when(patientDao.findById(patientId)).thenReturn(Optional.of(patient)); // Mock the findById method

        int result = patientService.getInsuranceService(patientId);

        assertEquals(0, result); // Assuming getInsuranceId() returns 0 by default
        verify(patientDao, times(1)).findById(patientId); // Verify that the findById method was called exactly once
    }

    @Test
    void testUpdateAddressService() {
        int patientId = 1;
        String newAddress = "New Address";
        Patient patient = new Patient(); // Create a dummy patient object
        when(patientDao.findById(patientId)).thenReturn(Optional.of(patient)); // Mock the findById method
        when(patientDao.save(any())).thenReturn(patient); // Mock the save method

        Patient result = patientService.updateAddressService(patientId, newAddress);

        assertSame(patient, result);
        assertEquals(newAddress, result.getAddress()); // Check that the address was updated
        verify(patientDao, times(1)).findById(patientId); // Verify that the findById method was called exactly once
        verify(patientDao, times(1)).save(patient); // Verify that the save method was called exactly once
    }

    @Test
    void testUpdatePhoneService() {
        int patientId = 1;
        String newPhoneNumber = "123-456-7890";
        Patient patient = new Patient(); // Create a dummy patient object
        when(patientDao.findById(patientId)).thenReturn(Optional.of(patient)); // Mock the findById method
        when(patientDao.save(any())).thenReturn(patient); // Mock the save method

        Patient result = patientService.updatePhoneService(patientId, newPhoneNumber);

        assertSame(patient, result);
        assertEquals(newPhoneNumber, result.getPhone()); // Check that the phone number was updated
        verify(patientDao, times(1)).findById(patientId); // Verify that the findById method was called exactly once
        verify(patientDao, times(1)).save(patient); // Verify that the save method was called exactly once
    }

   
}

