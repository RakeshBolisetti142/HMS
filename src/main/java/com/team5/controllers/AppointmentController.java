package com.team5.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team5.beans.Appointment;
import com.team5.beans.Nurse;
import com.team5.beans.Patient;
import com.team5.beans.Physician;
import com.team5.exceptions.InvalidValueException;
import com.team5.services.AppointmentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/appointment")
@SecurityRequirement(name= "Bearer Authentication")
public class AppointmentController {

	@Autowired
	AppointmentService appointmentService;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@PostMapping
	public String addAppointment(@RequestBody Appointment appointment) {
		return appointmentService.addNewAppointmentService(appointment);
		
	}
	@GetMapping
	public List<Appointment> getAllAppointments(){
		
		return appointmentService.getAllAppointmentService();
		
	}
	
	@GetMapping("/{startdate}")
	public List<Appointment> getAllAppointmentsByDate(@PathVariable("startdate") String startDate){
		LocalDateTime date = LocalDateTime.parse(startDate, formatter);
		return appointmentService.getAllAppointmentByDateService(date);
		
	}
	@GetMapping("/patient/{appointmentId}")
	public Patient getPatientInformation(@PathVariable("appointmentId") int appointmentId) {
		if(appointmentId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.getPatientInformation(appointmentId);
		
	}
	@GetMapping("/physician/{appointmentId}")
	public Physician getPhysician(@PathVariable("appointmentId") int appointmentId) {
		if(appointmentId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.getPhysician(appointmentId);
		
	}
	@GetMapping("/nurse/{appointmentId}")
	public Nurse getNurse(@PathVariable("appointmentId") int appointmentId) {
		if(appointmentId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.getNurse(appointmentId);
		
	}
	@GetMapping("/examinationRoom/{appointmentId}")
	public String getRoom(@PathVariable("appointmentId") int appointmentId) {
		if(appointmentId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.getExaminationRoomService(appointmentId);
		
	}
	
	@GetMapping("/physician/patient/{patientid}")
	public List<Physician> getAllPhysicianByPatientId(@PathVariable("patientid") int patientId){
		if(patientId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.getAllPhysicianByPatientIdService(patientId);
		
	}
	@GetMapping("/physician/{patientid}/{date}")
	public Physician getPhysicianOnParticularDate(@PathVariable("patientid")int patientId,
			@PathVariable("date") String appointmentDate) {
		if(patientId<0) throw new InvalidValueException("id must greater than 0");
		Date dateConv=null;
		try {
			dateConv =  dateFormat.parse(appointmentDate);
		} catch (ParseException e) {
			
		}
		
		return appointmentService.getPhysicianOnParticularDate(dateConv, patientId);
		
	}
	@GetMapping("/nurse/patient/{patientid}")
	public List<Nurse> getAllNurseByPatientId(@PathVariable("patientid") int patientId){
		if(patientId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.getAllNurseByPatientIdService(patientId);
		
	}
	@GetMapping("/nurse/{patientid}/{date}")
	public Nurse getNurseOnParticularDate(@PathVariable("patientid")int patientId,
			@PathVariable("date") String appointmentDate) {
		if(patientId<0) throw new InvalidValueException("id must greater than 0");

		Date dateConv=null;
		try {
			dateConv = dateFormat.parse(appointmentDate);
		} catch (ParseException e) {
		
		
		}
		
		return appointmentService.getNurseOnParticularDate(dateConv, patientId);
		
	}
	@GetMapping("date/{patientid}")
	public List<Date> getAllDates(@PathVariable("patientid") int patientId){
		if(patientId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.getAllDates(patientId);
	}
	
	@GetMapping("patient/physician/{physicianId}")
	public List<Patient> getAllPatientsByPhysician(@PathVariable("physicianId") int physicianId){
		if(physicianId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.getAllPatientsByPhysician(physicianId);
	}
	@GetMapping("/patient/{physicianid}/{date}")
	public List<Patient> getAllPatientsByPhysicianOnDate(@PathVariable("physicianid") int physicianId,@PathVariable("date") String date){
		if(physicianId<0) throw new InvalidValueException("id must greater than 0");
		LocalDateTime dateConv = LocalDateTime.parse(date, formatter);
		return appointmentService.getAllPatientByPhysicianOnDateService(physicianId, dateConv);
	}
	
	@GetMapping("physician/patient/{physicianid}/{patientid}")
	public Patient getPatientByphysicianByPatientId(@PathVariable("physicianid") int physicianId,@PathVariable("patientid") int patientId) {
		if(physicianId<0 || patientId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.getPatientByPhysicianByPatientIdService(physicianId, patientId);
	}
	@GetMapping("patient/nurse/{nurseid}")
	public List<Patient> getAllPatientByNurse(@PathVariable("nurseid") int nurseId){
		if(nurseId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.getAllPatientByNurse(nurseId);
		
	}
	@GetMapping("/patient/nurse/{nurseid}/{patientid}")
	public Patient getPatientByNurse(@PathVariable("nurseid") int nurseId,@PathVariable("patientid")int patientId){
		if(nurseId<0 || patientId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.getPatientByPatientIdByNurse(nurseId,patientId);
		
	}
	@GetMapping("/patient/nurse/{nurseid}/date/{date}")
	public List<Patient> getAllPatientByNurseOnDate(@PathVariable("nurseid") int nurseId,@PathVariable("date")String date){
		if(nurseId<0) throw new InvalidValueException("id must greater than 0");
		Date dateConv=null;
		try {
			dateConv = (Date) dateFormat.parse(date);
		} catch (ParseException e) {
		
		}
		return appointmentService.getAllPatientByNurseOnDate(nurseId,dateConv);
		
	}
	
	@GetMapping("room/{patientid}/{date}")
	public String getRoomByPatientIdOnDate(@PathVariable("patientid")int patientId,@PathVariable("date") String date) {
		if(patientId<0) throw new InvalidValueException("id must greater than 0");
		Date dateConv=null;
		try {
			dateConv = (Date) dateFormat.parse(date);
		} catch (ParseException e) {
		}
		return appointmentService.getRoomByPatientIdOnDate(patientId, dateConv);
	}
	
	@GetMapping("room/physician/{physicianid}/{date}")
	public List<String> getAllRoomByPhysicianIdOnDate(@PathVariable("physicianid")int physicianId,@PathVariable("date") String date) {
		if(physicianId<0) throw new InvalidValueException("id must greater than 0");
		Date dateConv=null;
		try {
			dateConv = (Date) dateFormat.parse(date);
		} catch (ParseException e) {
		
		
		}
		return appointmentService.getAllRoomByPhysicianIdOnDate(physicianId, dateConv);
	}
	
	@GetMapping("room/nurse/{nurseid}/{date}")
	public List<String> getAllRoomByNurseIdOnDate(@PathVariable("nurseid")int nurseId,@PathVariable("date") String date) {
		if(nurseId<0) throw new InvalidValueException("id must greater than 0");
		Date dateConv=null;
		try {
			dateConv = (Date) dateFormat.parse(date);
		} catch (ParseException e) {
		
			
		}
		return appointmentService.getAllRoomByNurseIdOnDate(nurseId, dateConv);
	}
	
	@PutMapping("room/{room}/{appintmentid}")
	public String updateRoomByAppointmentId(@PathVariable("appintmentid") int appointmentId,@PathVariable("room")String room) {
		if(appointmentId<0) throw new InvalidValueException("id must greater than 0");
		return appointmentService.updateRoomById(appointmentId,room);
		
	}
	
	@PostMapping("/test/addAppointment")
	public ResponseEntity<String> addAppointmentResponse(@RequestBody Appointment appointment) {
		
		return new ResponseEntity<String>(addAppointment(appointment),HttpStatus.OK);
	}
	@GetMapping("/test/get")
	public ResponseEntity<List<Appointment>> getAllAppointmentsResponse(){
		
		return new ResponseEntity<>( appointmentService.getAllAppointmentService(),HttpStatus.OK);
		
	}
	public ResponseEntity<String> getRoomResponse(int i) {
		
		return new ResponseEntity<>( appointmentService.getExaminationRoomService(i),HttpStatus.OK);
	}
	public ResponseEntity<Nurse> getNurseResponse(int i) {
	
		return new ResponseEntity<>( appointmentService.getNurse(i),HttpStatus.OK);
	}
	public ResponseEntity<Physician> getPhysicianResponse(int i) {
		
		return new ResponseEntity<>( appointmentService.getPhysician(i),HttpStatus.OK);
	}
	public ResponseEntity<Patient> getPatientInformationResponse(int i) {
		
		return new ResponseEntity<>( appointmentService.getPatientInformation(i),HttpStatus.OK);
	}
	public ResponseEntity<List<Appointment>> getAllAppointmentsByDateResponse(String string) {
		LocalDateTime date = LocalDateTime.parse(string,formatter);
		
		return new ResponseEntity<>( appointmentService.getAllAppointmentByDateService(date),HttpStatus.OK);
	}

}
