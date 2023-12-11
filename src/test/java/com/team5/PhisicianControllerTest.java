/*
 * package com.team5;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.junit.jupiter.api.Assertions.assertThrows; import static
 * org.mockito.Mockito.mock; import static org.mockito.Mockito.when;
 * 
 * import java.util.Arrays; import java.util.List;
 * 
 * import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
 * import org.springframework.boot.test.mock.mockito.MockBean; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity;
 * 
 * import com.team5.beans.Physician; import
 * com.team5.controllers.PhisicianController; import
 * com.team5.exceptions.InvalidPhysician; import
 * com.team5.exceptions.InvalidValueException; import
 * com.team5.services.PhysicianService;
 * 
 * public class PhisicianControllerTest { PhysicianService physicianService =
 * mock(PhysicianService.class);
 * 
 * @MockBean PhisicianController controller ; Physician physician = new
 * Physician();
 * 
 * @BeforeEach public void setUp() {
 * 
 * physician.setEmployeeId(1); physician.setName("TestPhysician");
 * physician.setPosition("TestPosition"); physician.setSsn(123456789);
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * @Test public void testAddNewPhysician_Success() {
 * 
 * 
 * 
 * 
 * when(controller.addNewPhysician(physician)).thenReturn(new
 * ResponseEntity("Record Created Successfully",HttpStatus.OK));
 * 
 * 
 * 
 * assertEquals(HttpStatus.OK,
 * controller.addNewPhysician(physician).getStatusCode());
 * assertEquals("Record Created Successfully",
 * controller.addNewPhysician(physician).getBody());
 * 
 * }
 * 
 * @Test public void testAddNewPhysician_InvalidPhysician() {
 * 
 * 
 * Physician physician = new Physician(); physician.setEmployeeId(-1);
 * 
 * when(controller.addNewPhysician(physician)).thenThrow(new
 * InvalidValueException("Id must be greater than 0"));
 * 
 * 
 * assertThrows(InvalidValueException.class,()->controller.addNewPhysician(
 * physician));
 * 
 * 
 * }
 * 
 * @Test public void testGetAllPhysician_Success() {
 * 
 * 
 * 
 * String position = "somePosition"; List<Physician> physicians =
 * Arrays.asList(physician);
 * 
 * when(controller.getAllPhysician(position)).thenReturn(new
 * ResponseEntity<List<Physician>>(physicians,HttpStatus.OK));
 * 
 * 
 * assertEquals(HttpStatus.OK,
 * controller.getAllPhysician(position).getStatusCode());
 * assertEquals(physicians, controller.getAllPhysician(position).getBody());
 * 
 * }
 * 
 * @Test public void testGetAllPhysician_InvalidPhysician() {
 * 
 * PhysicianService physicianService = mock(PhysicianService.class);
 * PhisicianController controller = new PhisicianController();
 * controller.physicianService = physicianService;
 * 
 * String position = "invalidPosition";
 * 
 * when(controller.getAllPhysician(position)).thenThrow(new
 * InvalidPhysician("Invalid Position"));
 * 
 * assertThrows(InvalidPhysician.class, () ->
 * controller.getAllPhysician(position));
 * 
 * }
 * 
 * 
 * }
 */
package com.team5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.team5.beans.Physician;
import com.team5.controllers.PhisicianController;
import com.team5.exceptions.InvalidValueException;
import com.team5.exceptions.NotFoundException;
import com.team5.services.PhysicianService;

public class PhisicianControllerTest {
    PhysicianService physicianService = mock(PhysicianService.class);
    PhisicianController controller;

    Physician physician = new Physician();

    @BeforeEach
    public void setUp() {
        controller = new PhisicianController();
        controller.physicianService = physicianService;

        physician.setEmployeeId(1);
        physician.setName("TestPhysician");
        physician.setPosition("TestPosition");
        physician.setSsn(123456789);
    }

   

    @Test
    public void testAddNewPhysician_InvalidPhysician() {
        Physician invalidPhysician = new Physician();
        invalidPhysician.setEmployeeId(-1);

        when(controller.addNewPhysician(invalidPhysician))
            .thenThrow(new InvalidValueException("Id must be greater than 0"));

        assertThrows(InvalidValueException.class, () -> controller.addNewPhysician(invalidPhysician));
    }
    @Test
    public void testAddNewPhysician_Success() {
        // Mocking service behavior
        when(physicianService.addNewPhysician(physician)).thenReturn("Record Created Successfully");

        ResponseEntity<String> result = controller.addNewPhysician(physician);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Record Created Successfully", result.getBody());
    }

    @Test
    public void testGetAllPhysician_Success() {
        String position = "somePosition";
        List<Physician> physicians = Arrays.asList(physician);

      
        when(physicianService.getAllPhysiciansByPosService(position)).thenReturn(physicians);

        ResponseEntity<List<Physician>> result = controller.getAllPhysician(position);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(physicians, result.getBody());
    }

    @Test
    public void testGetAllPhysician_InvalidPhysician() {
        String invalidPosition = "invalidPosition";

        when(controller.getAllPhysician(invalidPosition))
            .thenThrow(new NotFoundException("Invalid Position"));

        assertThrows(NotFoundException.class, () -> controller.getAllPhysician(invalidPosition));
    }
}


