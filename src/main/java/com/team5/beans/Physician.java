package com.team5.beans;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="physician")
public class Physician {
	

	@Override
	public String toString() {
		return "Physician [employeeId=" + employeeId + ", name=" + name + ", position=" + position + ", ssn=" + ssn
				+ ", patients=" + patients + "]";
	}

	@Id
	@Column(name="EmployeeID")
	private int employeeId;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Position")
	private String position;
	
	@Column(name="SSN")
	private int ssn;
	@JsonManagedReference
	@OneToMany(mappedBy = "physician")
	private List<Patient> patients ;
	
	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName = "userId")
	private Users user;


	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public Physician() {};
	
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getSsn() {
		return ssn;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
	}
	

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, name, patients, position, ssn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Physician other = (Physician) obj;
		return employeeId == other.employeeId && Objects.equals(name, other.name)
				&& Objects.equals(patients, other.patients) && Objects.equals(position, other.position)
				&& ssn == other.ssn;
	}

	
	

}
