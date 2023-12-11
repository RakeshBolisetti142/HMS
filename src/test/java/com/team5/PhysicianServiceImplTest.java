package com.team5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.team5.beans.Physician;
import com.team5.exceptions.NotFoundException;
import com.team5.repository.PhysicianDao;
import com.team5.services.PhysicianService;

@SpringBootTest
public class PhysicianServiceImplTest {

	@MockBean
	private PhysicianDao physicianDao;

	@Autowired
	private PhysicianService physicianService;

	private Physician testPhysician;

	@BeforeEach
	public void setUp() {

		testPhysician = new Physician();
		testPhysician.setEmployeeId(1);
		testPhysician.setName("TestPhysician");
		testPhysician.setPosition("TestPosition");
		testPhysician.setSsn(123456789);

		when(physicianDao.findById(1)).thenReturn(Optional.of(testPhysician));
		when(physicianDao.findById(2)).thenThrow(new NotFoundException("Physician with ID 2 not found"));
		when(physicianDao.findByPosition("TestPosition")).thenReturn(Arrays.asList(testPhysician));
		when(physicianDao.findByPosition("NonexistentPosition")).thenReturn(Arrays.asList());
		when(physicianDao.findByName("TestPhysician")).thenReturn(testPhysician);
		when(physicianDao.findByName("NonexistentPhysician")).thenReturn(null);

	}

	@Test
	public void testGetPhysicianById() {

		Physician result = physicianService.getPhysicianById(1);
		System.out.println(testPhysician + " " + result);
		assertEquals(testPhysician, result);

		try {
			physicianService.getPhysicianById(2);

		} catch (NotFoundException e) {

			assertEquals("Physician with ID 2 not found", e.getMessage());
		}
	}

	@Test
	public void testAddNewPhysician() {

	    testPhysician.setEmployeeId(0);
	    assertThrows(NotFoundException.class, () -> {
	        physicianService.addNewPhysician(testPhysician);
	    });

	  
	    testPhysician.setEmployeeId(1);
	    String result = physicianService.addNewPhysician(testPhysician);
	    assertEquals("Record Created Successfully", result);
	}



	@Test
	public void testGetAllPhysiciansByPosService() {

		List<Physician> result = physicianService.getAllPhysiciansByPosService("TestPosition");
		assertEquals(Arrays.asList(testPhysician), result);
	}

	@Test
    public void testUpdatePhysicianNameService() {
        when(physicianDao.save(any(Physician.class))).thenAnswer(invocation -> invocation.getArgument(0));

        
        Physician result = physicianService.updatePhysicianNameService("NewName", 1);
        assertEquals("NewName", result.getName());

       
        assertThrows(NotFoundException.class, () -> {
            physicianService.updatePhysicianNameService("NewName", 2);
        });
    }

	@Test
    public void testUpdatePhysicianSsnService() {
		System.out.println(testPhysician);
        when(physicianDao.save(any(Physician.class))).thenAnswer(invocation -> invocation.getArgument(0));

       
        Physician result = physicianService.updatePhysicianSsnService(987654321, 1);
        assertEquals(987654321, result.getSsn());

        
        assertThrows(NotFoundException.class, () -> {
            physicianService.updatePhysicianSsnService(987654321, 2);
        });
    }

	
	

}