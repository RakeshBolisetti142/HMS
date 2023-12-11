package com.team5.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment")
public class Appointment {
	@Id
	private int appointmentID;
	@ManyToOne
	@JoinColumn(name = "Patient", referencedColumnName = "ssn")
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "Physician", referencedColumnName = "employeeId")
	private Physician physician;

	@ManyToOne
	@JoinColumn(name = "PrepNurse", referencedColumnName = "employeeId")
	private Nurse prepNurse;

	@Column(name = "AppointmentDateTime")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date appointmentDateTime;

	@Column(name = "ExaminationRoom")
	private String examinationRoon;

	public Appointment() {
	}

	public int getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}

	public Nurse getPrepNurse() {
		return prepNurse;
	}

	public void setPrepNurse(Nurse prepNurse) {
		this.prepNurse = prepNurse;
	}

	public Date getAppointmentDateTime() {
		return appointmentDateTime;
	}

	public void setAppointmentDateTime(Date appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}

	public String getExaminationRoon() {
		return examinationRoon;
	}

	public void setExaminationRoon(String examinationRoon) {
		this.examinationRoon = examinationRoon;
	}

	@Override
	public String toString() {
		return "Appointment [AppointmentID=" + appointmentID + ", patient=" + patient + ", physician=" + physician
				+ ", prepNurse=" + prepNurse + ", appointmentDateTime=" + appointmentDateTime + ", examinationRoon="
				+ examinationRoon + "]";
	}

}
