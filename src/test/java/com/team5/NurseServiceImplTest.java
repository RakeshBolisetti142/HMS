package com.team5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.team5.beans.Nurse;
import com.team5.repository.NurseDao;
import com.team5.services.NurseServiceImpl;

@SpringBootTest
public class NurseServiceImplTest {

    @Mock
    private NurseDao nurseDao;

    @InjectMocks
    private NurseServiceImpl nurseService;

    @Test
    public void testAddNewNurse() {
        Nurse nurse = new Nurse(/* Initialize nurse object */);

        when(nurseDao.save(nurse)).thenReturn(nurse);

        String result = nurseService.addNewNurse(nurse);

        assertEquals("Record created successfully", result);
    }

    @Test
    public void testGetAllNurselist() {
    	  int empId = 1;
          List<Nurse> nurses = new ArrayList<Nurse>();
          Nurse n = new Nurse();
          n.setEmployeeId(empId);
          nurses.add(n);
        when(nurseDao.findAll()).thenReturn(nurses);

        List<Nurse> result = nurseService.getAllNurseList();

        assertEquals(nurses, result);
    }

    @Test
    public void testGetNurseById() {
        int empId = 1;
        Nurse nurse = new Nurse(/* Initialize nurse object */);
        

        when(nurseDao.findById(empId)).thenReturn(Optional.of(nurse));

        Nurse result = nurseService.getNurseById(empId);

        assertEquals(nurse, result);
    }
    @Test
    public void testGetPosistionById() {
        int empId = 1;
        Nurse nurse = new Nurse(/* Initialize nurse object */);

        when(nurseDao.findById(empId)).thenReturn(Optional.of(nurse));

        Nurse result = nurseService.getPositionById(empId);

        assertEquals(nurse, result);
    }

    @Test
    public void testGetRegOrNot() {
        int empId = 1;
        List<Nurse> nurses = new ArrayList<Nurse>();
        Nurse n = new Nurse();
        n.setEmployeeId(empId);
        nurses.add(n);
        
        
        when(nurseDao.getRegisteredStatus(empId)).thenReturn(nurses);

        boolean result = nurseService.getRegisteredStatus(empId);

        assertTrue(result); 
    }

    @Test
    public void testUpdateRegisteredByIdService() {
        int empId = 1;
        boolean registered = true;
        Nurse nurse = new Nurse(/* Initialize nurse object */);

        when(nurseDao.save(nurse)).thenReturn(nurse);
        when(nurseDao.findById(empId)).thenReturn(Optional.of(nurse));

        Nurse result = nurseService.updateRegisteredStatusById(registered, empId);

        assertTrue(result.isRegistered()); 
    }
    @Test
    public void testUpdateNurseSSN() {
        int empId = 1;
        int ssn = 123456789; 
        Nurse nurse = new Nurse();

        when(nurseDao.findById(empId)).thenReturn(Optional.of(nurse));
        when(nurseDao.save(nurse)).thenReturn(nurse);

        Nurse result = nurseService.updateNurseSSN(ssn, empId);

        assertEquals(ssn, result.getSsn()); 
    }

   
}
