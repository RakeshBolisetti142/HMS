package com.team5.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.team5.beans.Appointment;
import com.team5.beans.Nurse;
import com.team5.beans.Patient;
import com.team5.beans.Physician;

public interface AppointmentService  {
	
	String addNewAppointmentService(Appointment appointment);
	
	List<Appointment> getAllAppointmentService();
	List<Appointment> getAllAppointmentByDateService(LocalDateTime startDate);
	
	Patient getPatientInformation(int appointmentId);
	Physician getPhysician(int appointmentId);
	
	Nurse getNurse(int appointmentId);
	String getExaminationRoomService(int appointmentId);
	List<Physician> getAllPhysicianByPatientIdService(int patientId);
	Physician getPhysicianOnParticularDate(Date appointmentDate,int patientId);
	List<Nurse> getAllNurseByPatientIdService(int patientId);
	Nurse getNurseOnParticularDate(Date appointmentDate,int patientId);
	List<Date> getAllDates(int patientId);
	List<Patient> getAllPatientsByPhysician(int physicianId);
	List<Patient> getAllPatientByPhysicianOnDateService(int physicianId,LocalDateTime date);
	Patient getPatientByPhysicianByPatientIdService(int physicianId,int patientId);
	List<Patient> getAllPatientByNurse(int nurseId);
	Patient getPatientByPatientIdByNurse(int nurseId,int patiendId);
	List<Patient> getAllPatientByNurseOnDate(int nurseId,Date dateConv);
	String getRoomByPatientIdOnDate(int patientId,Date date);

	List<String> getAllRoomByPhysicianIdOnDate(int physicianId, Date dateConv);

	List<String> getAllRoomByNurseIdOnDate(int nurseId, Date dateConv);

	String updateRoomById(int appointmentId,String room);
}
