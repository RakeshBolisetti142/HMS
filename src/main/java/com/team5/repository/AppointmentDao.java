package com.team5.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team5.beans.Appointment;
import com.team5.beans.Patient;
import com.team5.beans.Physician;

@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Integer> {
	
	List<Appointment> findAllByAppointmentDateTime(LocalDateTime startDate);
	
	List<Appointment> findByPatient(Patient p);
	Appointment findByAppointmentDateTimeAndPatient(Date appointmentDate,Patient p);
	Appointment findByPhysicianAndAppointmentDateTime(Physician physicianId,Date date);
	List<Appointment> findByAppointmentDateTime(LocalDateTime date);
	List<Appointment> findByPhysicianEmployeeId(int employeeId);

	List<Appointment> findByPrepNurseEmployeeId(int employeeId);
	Appointment findByPatientSsnAndAppointmentDateTime(int sss,Date appointmentDateTime);
	List<Appointment> findByPhysicianEmployeeIdAndAppointmentDateTime(int employeeId,Date appoinmentDateTime);

	List<Appointment> findByPrepNurseEmployeeIdAndAppointmentDateTime(int nurseId, Date dateConv);

	Appointment findByAppointmentDateTimeAndPatientSsn(Date appointmentDate, int patientId);

	List<Appointment> findByPatientSsn(int patientId);
}
