package com.team5.beans;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {
	
	
	@Id
	@Column(name="SSN")
	private int ssn;
	
	@Column(name="InsuranceID")
	private int insuranceId;
	@Column(name="Address")
	private String address;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Phone")
	private String phone;
	
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="PCP",referencedColumnName = "employeeId")
	private Physician physician;
	
	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName = "userId")
	private Users user;
	
	
	
	public int getSsn() {
		return ssn;
	}
	public void setSsn(int ssn) {
		this.ssn = ssn;
	}
	public int getInsuranceId() {
		return insuranceId;
	}
	public void setInsuranceId(int insuranceId) {
		this.insuranceId = insuranceId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Physician getPhysician() {
		return physician;
	}
	public void setPhysician(Physician physician) {
		this.physician = physician;
	}
	
	
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Patient() {}
	
	@Override
	public String toString() {
		return "Patient [ssn=" + ssn + ", insuranceId=" + insuranceId + ", address=" + address + ", name=" + name
				+ ", phone=" + phone + ", physician=" + physician + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, insuranceId, name, phone, physician, ssn);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(address, other.address) && insuranceId == other.insuranceId
				&& Objects.equals(name, other.name) && Objects.equals(phone, other.phone)
				&& Objects.equals(physician, other.physician) && ssn == other.ssn;
	}


}
