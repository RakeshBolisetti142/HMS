package com.team5.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class UndergoesId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "Patient",referencedColumnName = "ssn")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "Procedures",referencedColumnName = "code")
    private Procedures procedures;



    @Column(name = "DateUndergoes", nullable = false)
    private Date dateUndergoes;

	@Override
	public int hashCode() {
		return Objects.hash(dateUndergoes, patient, procedures);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UndergoesId other = (UndergoesId) obj;
		return Objects.equals(dateUndergoes, other.dateUndergoes) && Objects.equals(patient, other.patient)
				&& Objects.equals(procedures, other.procedures) ;
	}

	@Override
	public String toString() {
		return "UndergoesId [patient=" + patient + ", procedures=" + procedures + ", dateUndergoes="
				+ dateUndergoes + "]";
	}

	public UndergoesId(Patient patient, Procedures procedures, Date dateUndergoes) {
		super();
		this.patient = patient;
		this.procedures = procedures;
		this.dateUndergoes = dateUndergoes;
	}
	public UndergoesId() {};
	private static final long serialVersionUID = 1L;
    
    
}
