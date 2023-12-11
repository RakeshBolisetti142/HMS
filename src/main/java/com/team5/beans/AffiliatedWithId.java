package com.team5.beans;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class AffiliatedWithId implements Serializable {

  
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "Department",referencedColumnName = "departmentId")
    @ManyToOne
    private Department department;

    @JoinColumn(name = "Physician",referencedColumnName = "employeeId")
    @ManyToOne
    private Physician physician;

   

    public AffiliatedWithId() {
        
    }



	@Override
	public int hashCode() {
		return Objects.hash(department, physician);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AffiliatedWithId other = (AffiliatedWithId) obj;
		return Objects.equals(department, other.department) && Objects.equals(physician, other.physician);
	}



	public Department getDepartment() {
		return department;
	}



	public void setDepartment(Department department) {
		this.department = department;
	}



	public Physician getPhysician() {
		return physician;
	}



	public void setPhysician(Physician physician) {
		this.physician = physician;
	}



	@Override
	public String toString() {
		return "AffiliatedWithId [department=" + department + ", physician=" + physician + "]";
	}


   
}

