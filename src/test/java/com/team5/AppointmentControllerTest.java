package com.team5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.team5.beans.Appointment;
import com.team5.beans.Nurse;
import com.team5.beans.Patient;
import com.team5.beans.Physician;
import com.team5.controllers.AppointmentController;
import com.team5.services.AppointmentService;

@SpringBootTest
public class AppointmentControllerTest {
	
	
	@InjectMocks
	AppointmentController appointmentController;
	@Mock
	AppointmentService appointmentService;
	 Appointment appointment;
	    Patient patient ;
	    List<Patient> patients;
	    Physician physician ;
	    Nurse nurse;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	  @BeforeEach
	    public void setUp() {
		  appointment =  new Appointment();
	    	patient =  new Patient();
	    	physician = new Physician();
	    	nurse  = new Nurse();
	    
	    patient.setSsn(100000001);
	    patient.setInsuranceId(68476213);
	    patient.setAddress("42 Foobar Lane");
	    patient.setName("John Smith");
	    patient.setPhone("555-0256");



	    
	    physician.setEmployeeId(1);
	    physician.setName("John Dorian");
	    physician.setPosition("Staff Internist");
	    physician.setSsn(111111111);
	    physician.setPatients(Arrays.asList(patient));

	    nurse.setEmployeeId(101);
	    nurse.setRegistered(true);
	    nurse.setSsn(100000001);
	    nurse.setName("John Smith");
	    nurse.setPosition("Head Nurse");
	    
	    appointment.setAppointmentID(1);
	    appointment.setPatient(patient);
	    appointment.setPhysician(physician);
	    appointment.setPrepNurse(nurse);
	    appointment.setExaminationRoon("Room123");
	    
	    
	    try {
	    Date date = dateFormat.parse("04/07/2023 04:30:00");
	    appointment.setAppointmentDateTime(date);
	    }
	    catch(Exception e) {
	    	System.out.println(e.getMessage());
	    }
	    }
	  @Test
	  public void addAppointmentTest() {
		  
		  when(appointmentService.addNewAppointmentService(appointment)).thenReturn("Record Created Successfully");
		  ResponseEntity<String> res = appointmentController.addAppointmentResponse(appointment);
		  assertEquals(res.getStatusCode(), HttpStatus.OK);
		  assertEquals(res.getBody(), "Record Created Successfully");
		  
	  }
	  
	  @Test
	  public void getAllAppointmentTest() {
		  
		  when(appointmentService.getAllAppointmentService()).thenReturn(Arrays.asList(appointment));
		  ResponseEntity<List<Appointment>> res = appointmentController.getAllAppointmentsResponse();
		  assertEquals(res.getStatusCode(), HttpStatus.OK);
		  assertEquals(res.getBody(), Arrays.asList(appointment));
		  
	  }
	  @Test
	  public void getAllAppointmentsByDateTest() {
	      when(appointmentService.getAllAppointmentByDateService(any(LocalDateTime.class))).thenReturn(Arrays.asList(appointment));
	      ResponseEntity<List<Appointment>> res = appointmentController.getAllAppointmentsByDateResponse("2023-07-04 04:30:00.000000");
	      assertEquals(res.getStatusCode(), HttpStatus.OK);
	      assertEquals(res.getBody(), Arrays.asList(appointment));
	  }

	  @Test
	  public void getPatientInformationTest() {
	      when(appointmentService.getPatientInformation(anyInt())).thenReturn(patient);
	      ResponseEntity<Patient> res = appointmentController.getPatientInformationResponse(1);
	      assertEquals(res.getStatusCode(), HttpStatus.OK);
	      assertEquals(res.getBody(), patient);
	  }

	  @Test
	  public void getPhysicianTest() {
	      when(appointmentService.getPhysician(anyInt())).thenReturn(physician);
	      ResponseEntity<Physician> res = appointmentController.getPhysicianResponse(1);
	      assertEquals(res.getStatusCode(), HttpStatus.OK);
	      assertEquals(res.getBody(), physician);
	  }

	  @Test
	  public void getNurseTest() {
	      when(appointmentService.getNurse(anyInt())).thenReturn(nurse);
	      ResponseEntity<Nurse> res = appointmentController.getNurseResponse(1);
	      assertEquals(res.getStatusCode(), HttpStatus.OK);
	      assertEquals(res.getBody(), nurse);
	  }

	  @Test
	  public void getRoomTest() {
	      when(appointmentService.getExaminationRoomService(anyInt())).thenReturn("Room123");
	      ResponseEntity<String> res = appointmentController.getRoomResponse(1);
	      assertEquals(res.getStatusCode(), HttpStatus.OK);
	      assertEquals(res.getBody(), "Room123");
	  }

	  
	



	       
	    }

	
