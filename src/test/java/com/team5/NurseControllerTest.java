package com.team5;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.team5.beans.Nurse;
import com.team5.controllers.NurseController;
import com.team5.services.NurseService;

@SpringBootTest
public class NurseControllerTest {
	
	 @Mock
	    private NurseService nurseService;

	    @InjectMocks
	    private NurseController nurseController;

	    @Test
	    public void testAddNewNurse() {
	      
	        Nurse nurse = new Nurse();
	        nurse.setName("Test Nurse");
	      
	        when(nurseService.addNewNurse(any(Nurse.class))).thenReturn("Nurse added successfully");

	        
	        String result = nurseController.addNewNurse(nurse);

	      
	        assertEquals("Nurse added successfully", result);
	    }

	    @Test
	    public void testGetAllNurses() {
	       
	        List<Nurse> nurseList = new ArrayList<>();
	      
	        when(nurseService.getAllNurseList()).thenReturn(nurseList);

	 
	        List<Nurse> result = nurseController.getAllnurseService();

	        assertEquals(nurseList, result);
	    }

	    @Test
	    public void testGetNurseById() {
	        int empId = 103;
	       
	        Nurse nurse = new Nurse();
	        nurse.setEmployeeId(empId);
	       
	        when(nurseService.getNurseById(empId)).thenReturn(nurse);

	       
	        Nurse result = nurseController.getNurseById(empId);

	        
	        assertEquals(nurse, result);
	    }
	    @Test
	    public void testGetPositionById() {
	        int empId = 102;
	        
	        Nurse nurse = new Nurse();
	        nurse.setPosition("Nurse");
	       
	        when(nurseService.getPositionById(empId)).thenReturn(nurse);

	        
	        Nurse result = nurseController.getpositionById(empId);

	       
	        verify(nurseService, times(1)).getPositionById(empId);
	        assertEquals(nurse, result);
	    }

	    @Test
	    public void testGetNurseIsRegisteredOrNot() {
	        int empId = 101;
	       
	        when(nurseService.getRegisteredStatus(empId)).thenReturn(true);

	        
	        Boolean result = nurseController.getnurseisregisteredornot(empId);

	        
	        assertTrue(result);
	    }

	    @Test
	    public void testUpdateRegisteredById() {
	        int empId = 101;
	        boolean getRegistered = true;
	       
	        Nurse nurse = new Nurse();
	        nurse.setEmployeeId(empId);
	      
	        when(nurseService.updateRegisteredStatusById(getRegistered, empId)).thenReturn(nurse);

	        
	        Nurse result = nurseController.updateregisteredById(getRegistered, empId);

	        
	        assertEquals(nurse, result);
	    }

	    @Test
	    public void testUpdateNurseSSN() {
	        int ssn = 111111110;
	        int empId = 101;
	        
	        Nurse nurse = new Nurse();
	        nurse.setSsn(ssn);
	        
	        when(nurseService.updateNurseSSN(ssn, empId)).thenReturn(nurse);

	        
	        Nurse result = nurseController.updateNurseSSN(ssn, empId);

	        
	        assertEquals(nurse, result);
	    }
	}
	