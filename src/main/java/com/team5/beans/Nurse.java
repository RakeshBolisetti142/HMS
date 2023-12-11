package com.team5.beans;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="nurse")
public class Nurse {
		
	@Id
	@Column(name="EmployeeID")
	private int employeeId;
	
	@Column(name="Registered")
	private boolean registered;
	
	@Column(name="SSN")
	private int ssn;

	
	@Column(name="Name")
	private String name;
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Column(name="Position")
	private String position;
	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName = "userId")
	private Users user;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public int getSsn() {
		return ssn;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
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
	
	public Nurse() {}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, name, position, registered, ssn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nurse other = (Nurse) obj;
		return employeeId == other.employeeId && Objects.equals(name, other.name)
				&& Objects.equals(position, other.position) && registered == other.registered && ssn == other.ssn;
	}

	@Override
	public String toString() {
		return "Nurse [employeeId=" + employeeId + ", registered=" + registered + ", ssn=" + ssn + ", name=" + name
				+ ", position=" + position + "]";
	};
	
	
	

}
