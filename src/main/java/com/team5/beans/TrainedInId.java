package com.team5.beans;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class TrainedInId implements Serializable {

    
    
	private static final long serialVersionUID = 6480262401691085699L;

	@ManyToOne
    @JoinColumn(name = "Physician",referencedColumnName = "employeeId")
    private Physician physician;

    @ManyToOne
    @JoinColumn(name = "Treatment",referencedColumnName = "code")
    private Procedures treatment;

   

    public TrainedInId() {
       
    }

    public TrainedInId(Physician physicianId, Procedures treatment) {
        this.physician = physicianId;
        this.treatment = treatment;
    }

	@Override
	public int hashCode() {
		return Objects.hash(physician, treatment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainedInId other = (TrainedInId) obj;
		return physician == other.physician && treatment == other.treatment;
	}

	public Physician getPhysicianId() {
		return physician;
	}

	public void setPhysicianId(Physician physician) {
		this.physician = physician;
	}

	public Procedures getTreatmentId() {
		return treatment;
	}

	public void setTreatmentId(Procedures treatment) {
		this.treatment = treatment;
	}

   
}

